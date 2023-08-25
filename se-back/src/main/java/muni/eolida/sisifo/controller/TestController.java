package muni.eolida.sisifo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/prueba")
@RestController
@Tag(name = "Endpoints TEST", description = "Recursos referidos al testeo de autoridades o roles.")
public class TestController {

    @Operation(
            summary = "Chequear si el Usuario tiene Rol CONTRIBUYENTE.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente."
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/contribuyente")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public String contribuyente() { return "Autoridad contribuyente."; }

    @Operation(
            summary = "Chequear si el Usuario tiene Rol EMPLEADO.",
            description = "Rol/Autoridad requerida: EMPLEADO<br>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente."
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/empleado")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public String empleado() { return "Autoridad empleado."; }

    @Operation(
            summary = "Chequear si el Usuario tiene Rol CAPATAZ.",
            description = "Rol/Autoridad requerida: CAPATAZ<br>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente."
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/capataz")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public String capataz() { return "Autoridad capataz."; }

    @Operation(
            summary = "Chequear si el Usuario tiene Rol JEFE.",
            description = "Rol/Autoridad requerida: JEFE<br>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente."
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/jefe")
    @PreAuthorize("hasAuthority('JEFE')")
    public String jefe() { return "Autoridad jefe."; }
}
