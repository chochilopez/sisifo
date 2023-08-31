package muni.eolida.sisifo.service.implementation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.model.TokenModel;
import muni.eolida.sisifo.repository.TokenDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.security.JwtService;
import muni.eolida.sisifo.util.email.EmailModel;
import muni.eolida.sisifo.util.email.mapper.EmailCreation;
import muni.eolida.sisifo.util.email.service.EmailServiceImpl;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomTokenMismatchException;
import org.apache.tomcat.util.codec.binary.Base64;
import muni.eolida.sisifo.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

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
    private final UsuarioDAO usuarioDAO;
    private final TokenServiceImpl tokenService;

    @Value("${sisifo.app.mail.username}")
    private String sender;
    @Value("${sisifo.app.mail.path}")
    private String path;
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);

    @Override
    public AutenticacionResponseDTO ingresar(AutenticacionRequestDTO autenticacionRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(autenticacionRequestDTO.getUsername(), autenticacionRequestDTO.getPassword()));
        UsuarioModel usuario = usuarioService.buscarPorNombreDeUsuario(autenticacionRequestDTO.getUsername());
        if (!usuario.getHabilitada())
            throw new CustomDataNotFoundException("El usuario: " + autenticacionRequestDTO.getUsername() + " no se encuentra habilitado, debe confirmar su email");
        String token = jwtService.generarToken(usuario);
        String refresh = jwtService.generarRefreshToken(usuario);
        tokenService.revocarTokensUsuario(usuario);
        tokenService.guardarTokenUsuario(usuario, token);
        log.info("El usuario {} se logueo correctamente.", autenticacionRequestDTO.getUsername());
        return new AutenticacionResponseDTO(token, refresh);
    }

    @Override
    public UsuarioModel validarToken(Long id, String token) {
        UsuarioModel usuario = usuarioService.buscarPorId(id);
        if (usuario.getToken().equals(token)) {
            usuario.setHabilitada(true);
            log.info("La cuenta de usuario: {}, se habilitó correctamente.", usuario.getUsername());
            return usuarioDAO.save(usuario);
        } else {
            log.warn("Ocurrió un error al intentar habilitar la cuenta.");
            throw new CustomTokenMismatchException("No se pudo habilitar el usuario " + usuario.getUsername() + " ya que los tokens no coinciden.");
        }
    }

    @Override
    public UsuarioModel registrar(UsuarioCreation usuarioCreation) {
        usuarioService.existeUsuarioPorNombreDeUsuario(usuarioCreation.getUsername());
        UsuarioModel usuario = usuarioService.guardar(usuarioCreation);
        usuario.setHabilitada(false);
        String token = Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey());
        usuario.setToken(token);
        String body = path + usuario.getId() + "/" + token;
        EmailModel emailModel = emailService.enviarEmailSimple(new EmailCreation(
                "Confirmá tu dirección de email para continuar con tu reclamo.",
                this.sender,
                usuarioCreation.getUsername(),
                body
        ));
        return usuarioService.darRol(usuario, "CONTRIBUYENTE");
    }

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if (authHeader != null) {
                if (authHeader.startsWith("Bearer ")) {
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