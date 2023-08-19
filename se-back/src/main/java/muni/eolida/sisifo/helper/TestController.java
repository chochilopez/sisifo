package muni.eolida.sisifo.helper;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/test")
@RestController
public class TestController {
    @GetMapping(value = "/usuacontribuyenterio")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public String contribuyente() { return "Autoridad contribuyente."; }

    @GetMapping(value = "/empleado")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public String empleado() { return "Autoridad empleado."; }

    @GetMapping(value = "/capataz")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public String capataz() { return "Autoridad capataz."; }

    @GetMapping(value = "/jefe")
    @PreAuthorize("hasAuthority('JEFE')")
    public String jefe() { return "Autoridad jefe."; }
}
