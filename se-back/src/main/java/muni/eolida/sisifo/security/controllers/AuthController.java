package muni.eolida.sisifo.security.controllers;

import jakarta.validation.Valid;
import muni.eolida.sisifo.security.models.Role;
import muni.eolida.sisifo.security.models.User;
import muni.eolida.sisifo.security.models.enums.RoleEnum;
import muni.eolida.sisifo.security.payload.request.LoginRequest;
import muni.eolida.sisifo.security.payload.request.SignupRequest;
import muni.eolida.sisifo.security.payload.response.JwtResponse;
import muni.eolida.sisifo.security.payload.response.MessageResponse;
import muni.eolida.sisifo.security.repository.RoleRepository;
import muni.eolida.sisifo.security.repository.UserRepository;
import muni.eolida.sisifo.security.security.jwt.JwtUtils;
import muni.eolida.sisifo.security.security.services.UserDetailsImpl;
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
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

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
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Ya existe un Usuario con es email."));
    }

    // Create new user's account
    User user = new User(
            signUpRequest.getName(),
            signUpRequest.getIdNumber(),
            signUpRequest.getAddress(),
            signUpRequest.getTelephone(),
            signUpRequest.getUsername(),
            encoder.encode(signUpRequest.getPassword())
    );

    Set<Role> roles = new HashSet<>();
    Optional<Role> role = roleRepository.findByRole(RoleEnum.ROLE_USER);
    roles.add(role.get());
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente!"));
  }
}
