package muni.eolida.sisifo.controller;

import jakarta.validation.Valid;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.helper.payload.request.LoginRequest;
import muni.eolida.sisifo.helper.payload.request.SignupRequest;
import muni.eolida.sisifo.helper.payload.response.JwtResponse;
import muni.eolida.sisifo.helper.payload.response.MessageResponse;
import muni.eolida.sisifo.repository.RolRepository;
import muni.eolida.sisifo.repository.UsuarioRepository;
import muni.eolida.sisifo.security.jwt.JwtUtils;
import muni.eolida.sisifo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AutoridadController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  RolRepository rolRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getUsername(),
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (usuarioRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Ya existe un Usuario con es email."));
    }

    // Create new user's account
    UsuarioModel user = new UsuarioModel(
            signUpRequest.getName(),
            signUpRequest.getIdNumber(),
            signUpRequest.getAddress(),
            signUpRequest.getTelephone(),
            signUpRequest.getUsername(),
            encoder.encode(signUpRequest.getPassword())
    );

    Set<RolModel> rolModels = new HashSet<>();
    Optional<RolModel> role = rolRepository.findByRol(RolEnum.ROL_USUARIO);
    rolModels.add(role.get());
    usuarioRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente!"));
  }
}
