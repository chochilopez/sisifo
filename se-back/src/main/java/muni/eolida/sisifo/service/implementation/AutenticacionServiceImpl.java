package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.email.EmailModel;
import muni.eolida.sisifo.helper.email.mapper.EmailCreation;
import muni.eolida.sisifo.helper.email.service.EmailServiceImpl;
import muni.eolida.sisifo.helper.payload.request.LoginRequest;
import muni.eolida.sisifo.helper.payload.response.JwtResponse;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.security.jwt.JwtUtils;
import muni.eolida.sisifo.security.services.UserDetailsServiceImpl;
import org.apache.tomcat.util.codec.binary.Base64;
import muni.eolida.sisifo.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AutenticacionServiceImpl implements AutenticacionService {
    @Autowired
    UsuarioServiceImpl usuarioService;
    @Autowired
    UsuarioDAO usuarioDAO;
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmailServiceImpl emailService;

    @Value("${sisifo.app.mail.username}")
    private String sender;

    @Value("${sisifo.app.mail.path}")
    private String path;

    @Override
    public EntityMessenger<JwtResponse> ingresarUsuario(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (DisabledException e) {
            String message = "El usuario se encuentra deshabilitado.";
            log.info(message);
            return new EntityMessenger<JwtResponse>(null, null, message, 204);
        } catch (BadCredentialsException e) {
            String message = "El usuario posee credenciales invalidas.";
            log.info(message);
            return new EntityMessenger<JwtResponse>(null, null, message, 204);
        } catch (UsernameNotFoundException e) {
            String message = "El usuario no existe o se cuenta no se encuentra habilitada.";
            log.info(message);
            return new EntityMessenger<JwtResponse>(null, null, message, 202);
        }
        UserDetails userdetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtils.generateToken(userdetails);
        Collection<? extends GrantedAuthority> authorities = userdetails.getAuthorities();
        Set<String> auths=new HashSet<>();
        if (authorities.contains(new SimpleGrantedAuthority(RolEnum.ROL_USUARIO.name())))
            auths.add("ROL_USUARIO");
        if (authorities.contains(new SimpleGrantedAuthority(RolEnum.ROL_ADMINISTRADOR.name())))
            auths.add("ROL_ADMINISTRADOR");

        String message = "El usuario " + loginRequest.getUsername() + " se logueo correctamente.";
        log.info(message);
        return new EntityMessenger<JwtResponse>(new JwtResponse(token, userdetails.getUsername(), auths), null, message, 200);
    }

    @Override
    public EntityMessenger<UsuarioModel>  validarToken(Long id, String token) {
        EntityMessenger<UsuarioModel> usuario = usuarioService.buscarPorId(id);
        if (usuario.getStatusCode() == 200) {
            if (token != null && usuario.getObject().getToken().equals(token)) {
                usuario.getObject().setHabilitada(true);
                usuario = usuarioService.actualizar(usuario.getObject());
                String message = "Account " + usuario.getObject().getUsername() + " is enabled.";
                log.info(message);
                usuario.setMessage(message);
                return usuario;
            } else {
                String message = "An error ocurred while trying to enable the account.";
                log.warn(message);
                return new EntityMessenger<UsuarioModel>(null, null, message, 204);
            }
        }
        return usuario;
    }

    @Override
    public EntityMessenger<UsuarioModel> registrarUsuario(UsuarioCreation usuarioCreation) {
        EntityMessenger<UsuarioModel> usuario = usuarioService.insertar(usuarioCreation);
        if (usuario.getStatusCode() == 201) {
            usuario.getObject().setHabilitada(false);
            String token = new String(Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey()));
            usuario.getObject().setToken(token);
            String body = path + usuario.getObject().getId() + "/" +  token;
            EntityMessenger<EmailModel> emailModelEntityMessenger = emailService.sendSimpleEmail(new EmailCreation(
                    "Confirmá tu dirección de email para continuar con tu reclamo.",
                    this.sender,
                    usuarioCreation.getUsername(),
                    body
            ));
            usuario = usuarioService.darRol(usuario.getObject(), RolEnum.ROL_USUARIO);
            if (emailModelEntityMessenger.getStatusCode() == 201) {
                String message = "The user " + usuarioCreation.getUsername() + " was created. Confirmation email sended.";
                log.info(message);
                return new EntityMessenger<UsuarioModel>(usuario.getObject(), null, message, 201);
            }
        }
        return usuario;
    }

}
