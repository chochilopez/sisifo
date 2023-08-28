package muni.eolida.sisifo.service.implementation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.model.TokenModel;
import muni.eolida.sisifo.model.enums.TipoToken;
import muni.eolida.sisifo.repository.TokenDAO;
import muni.eolida.sisifo.security.JwtService;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.email.EmailModel;
import muni.eolida.sisifo.util.email.mapper.EmailCreation;
import muni.eolida.sisifo.util.email.service.EmailServiceImpl;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import org.apache.tomcat.util.codec.binary.Base64;
import muni.eolida.sisifo.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioServiceImpl usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenDAO tokenDAO;
    private final JwtService jwtService;
    private final EmailServiceImpl emailService;

    @Value("${sisifo.app.mail.username}")
    private String sender;
    @Value("${sisifo.app.mail.path}")
    private String path;
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);

    @Override
    public EntityMessenger<AutenticacionResponseDTO> ingresar(AutenticacionRequestDTO autenticacionRequestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(autenticacionRequestDTO.getUsername(), autenticacionRequestDTO.getPassword()));
            EntityMessenger<UsuarioModel> usuarioModelEntityMessenger = usuarioService.buscarPorNombreDeUsuario(autenticacionRequestDTO.getUsername());
            if (usuarioModelEntityMessenger.getEstado() == 200) {
                if (usuarioModelEntityMessenger.getObjeto().getHabilitada()) {
                    String token = jwtService.generarToken(usuarioModelEntityMessenger.getObjeto());
                    String refresh = jwtService.generarRefreshToken(usuarioModelEntityMessenger.getObjeto());
                    this.revocarTokensUsuario(usuarioModelEntityMessenger.getObjeto());
                    this.guardarTokenUsuario(usuarioModelEntityMessenger.getObjeto(), token);
                    String mensaje = "El usuario " + autenticacionRequestDTO.getUsername() + " se logueo correctamente.";
                    log.info(mensaje);
                    return new EntityMessenger<AutenticacionResponseDTO>(
                            AutenticacionResponseDTO.builder()
                                    .tokenAcceso(token)
                                    .tokenRefresco(refresh)
                                    .build(),
                            null,
                            mensaje,
                            200
                    );
                } else {
                    String mensaje = "El usuario no se encuentra habilitado, debe confirmar el email.";
                    log.warn(mensaje);
                    return new EntityMessenger<AutenticacionResponseDTO>(null, null, mensaje, 202);
                }
            } else {
                return new EntityMessenger<>(null, null, usuarioModelEntityMessenger.getMensaje(), usuarioModelEntityMessenger.getEstado());
            }
        } catch (DisabledException e) {
            String mensaje = "El usuario se encuentra deshabilitado.";
            log.warn(mensaje);
            return new EntityMessenger<AutenticacionResponseDTO>(null, null, mensaje, 202);
        } catch (BadCredentialsException e) {
            String mensaje = "El usuario no posee credenciales válidas.";
            log.warn(mensaje);
            return new EntityMessenger<AutenticacionResponseDTO>(null, null, mensaje, 202);
        } catch (UsernameNotFoundException e) {
            String mensaje = "El usuario no existe o la cuenta no se encuentra habilitada.";
            log.warn(mensaje);
            return new EntityMessenger<AutenticacionResponseDTO>(null, null, mensaje, 202);
        } catch (Exception e) {
            String mensaje = "Hubo un problema al otorgale permisos al usuario.";
            log.warn(mensaje);
            return new EntityMessenger<AutenticacionResponseDTO>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> validarToken(Long id, String token) {
        try {
            EntityMessenger<UsuarioModel> usuario = usuarioService.buscarPorId(id);
            if (usuario.getEstado() == 200) {
                if (usuario.getObjeto().getToken().equals(token)) {
                    usuario.getObjeto().setHabilitada(true);
                    usuario = usuarioService.actualizar(usuario.getObjeto());
                    String mensaje = "La cuenta de usaurio: " + usuario.getObjeto().getUsername() + ", se habilitó correctamente.";
                    log.info(mensaje);
                    usuario.setMensaje(mensaje);
                    return usuario;
                } else {
                    String mensaje = "Ocurrió un error al intentar habilitar la cuenta.";
                    log.warn(mensaje);
                    return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
                }
            }
            return usuario;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> registrar(UsuarioCreation usuarioCreation) {
        try {
            if (!usuarioService.existeUsuarioPorNombreDeUsuario(usuarioCreation.getUsername())) {
                EntityMessenger<UsuarioModel> usuario = usuarioService.insertar(usuarioCreation);
                if (usuario.getEstado() == 201) {
                    usuario.getObjeto().setHabilitada(false);
                    String token = Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey());
                    usuario.getObjeto().setToken(token);
                    String body = path + usuario.getObjeto().getId() + "/" + token;
                    EntityMessenger<EmailModel> emailModelEntidadMensaje = emailService.enviarEmailSimple(new EmailCreation(
                            "Confirmá tu dirección de email para continuar con tu reclamo.",
                            this.sender,
                            usuarioCreation.getUsername(),
                            body
                    ));
                    usuario = usuarioService.darRol(usuario.getObjeto(), "CONTRIBUYENTE");
                    if (emailModelEntidadMensaje.getEstado() == 201) {
                        String mensaje = "El usuario " + usuarioCreation.getUsername() + " fue creado correctamente. En envió el email de confirmación.";
                        log.info(mensaje);
                        return new EntityMessenger<UsuarioModel>(usuario.getObjeto(), null, mensaje, 201);
                    }
                }
                return usuario;
            } else {
                String mensaje = "El nombre de usuario ya esta en uso.";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    private void guardarTokenUsuario(UsuarioModel usuario, String jwt) {
        TokenModel token = TokenModel.builder()
                .usuario(usuario)
                .token(jwt)
                .tipoToken(TipoToken.BEARER)
                .expirado(false)
                .revocado(false)
                .build();
        tokenDAO.save(token);
    }

    private void revocarTokensUsuario(UsuarioModel user) {
        List<TokenModel> validUserTokens = tokenDAO.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpirado(true);
            token.setRevocado(true);
        });
        tokenDAO.saveAll(validUserTokens);
    }

    @Override
    public EntityMessenger<AutenticacionResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response){
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String userEmail;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                refreshToken = authHeader.substring(7);
                userEmail = jwtService.extraerUsuario(refreshToken);
                if (userEmail != null) {
                    EntityMessenger<UsuarioModel> usuarioModelEntityMessenger = usuarioService.buscarPorNombreDeUsuario(userEmail);
                    if (usuarioModelEntityMessenger.getEstado() == 200) {
                        if (jwtService.esValidoToken(refreshToken, usuarioModelEntityMessenger.getObjeto())) {
                            var accessToken = jwtService.generarToken(usuarioModelEntityMessenger.getObjeto());
                            this.revocarTokensUsuario(usuarioModelEntityMessenger.getObjeto());
                            this.guardarTokenUsuario(usuarioModelEntityMessenger.getObjeto(), accessToken);
                            return new EntityMessenger<AutenticacionResponseDTO>(new AutenticacionResponseDTO(accessToken, refreshToken), null, "Tokens emitidos correctamente", 200);
                        }
                    }
                }
            }
            return new EntityMessenger<AutenticacionResponseDTO>(null, null, "Ocurrio un error al validar el token de refresco", 202);
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al validar el token de refresco.";
            log.error(mensaje);
            return new EntityMessenger<AutenticacionResponseDTO>(null, null, mensaje, 204);
        }
    }

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if (authHeader != null || authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                Optional<TokenModel> storedToken = tokenDAO.findByToken(jwt);
                if (storedToken.isPresent()) {
                    storedToken.get().setExpirado(true);
                    storedToken.get().setRevocado(true);
                    tokenDAO.save(storedToken.get());
                    SecurityContextHolder.clearContext();
                    String mensaje = "El usuario ha salido correctamente del sistema.";
                    log.info(mensaje);
                    return new ResponseEntity<>(mensaje, HttpStatus.OK);
                }
            }
            String mensaje = "El token se encuentra mal formado.";
            log.info(mensaje);
            return new ResponseEntity<>(mensaje, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar salir del sistema.";
            log.error(mensaje);
            return new ResponseEntity<>(mensaje, HttpStatus.NO_CONTENT);
        }
    }
}