package muni.eolida.sisifo.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/prueba")
@RestController
@RequiredArgsConstructor
public class TestController {
    @GetMapping(value = "/usuario")
    @PreAuthorize("hasAuthority('ROL_USUARIO')")
    public String usuario() { return "Autoridad Usuario."; }

    @GetMapping(value = "/administrador")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String administrador() { return "Autoridad administrador."; }
}
