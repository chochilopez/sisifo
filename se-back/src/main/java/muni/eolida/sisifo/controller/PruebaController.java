package muni.eolida.sisifo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class PruebaController {
  @GetMapping("/user")
  @PreAuthorize("hasAuthority('AUTHORITY_USER')")
  public String userAccess() {
    return "Credenciales Usuario.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
  public String adminAccess() {
    return "Credenciales Admin.";
  }
}
