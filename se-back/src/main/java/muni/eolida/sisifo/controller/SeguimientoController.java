package muni.eolida.sisifo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.enums.EstadoReclamoEnum;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.SeguimientoMapper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.service.implementation.SeguimientoServiceImpl;
import muni.eolida.sisifo.util.exception.CustomAlreadyExistantAreaException;
import muni.eolida.sisifo.util.exception.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/seguimiento")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Endpoints SEGUIMIENTO", description = "Recursos referidos a la consulta y persistencia de Seguimientos de reclamos.")
public class SeguimientoController extends BaseController {
    private final SeguimientoServiceImpl seguimientoService;
    private final SeguimientoMapper seguimientoMapper;

    @ExceptionHandler(CustomAlreadyExistantAreaException.class)
    public ResponseEntity<ErrorDTO> handleCustomAlreadyExistantAreaException(CustomAlreadyExistantAreaException e) {
        HttpStatus status = HttpStatus.CONFLICT; // 409
        String mensaje = "El estado de reclamo ya esta conectado al seguimiento. " + e.getMessage();

        return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
    }

    @Operation(
            summary = "Agregar EstadoReclamo a Seguimiento.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se agrega el EstadoReclamo al Seguimiento.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON modificado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SeguimientoDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(name = "idSeguimiento", in = ParameterIn.PATH, description = "Numerico."),
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Objeto JSON conteniendo el EstadoReclamo.",
                    example = "<br>{<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"estado\": \"RESUELTO.\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"descripcion\": \"Luminaria reemplazada.\",<br>" +
                            "<br>}"
            )
    })
    @PostMapping(value = "/agregar-estado-reclamo/{idSeguimiento}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> agregarEstadoReclamo(
            @PathVariable("idSeguimiento") @Positive Long idSeguimiento,
            @Valid @RequestBody EstadoReclamoCreation estadoReclamoCreation
    ) {
        SeguimientoModel objeto = seguimientoService.agregarEstadoReclamo(idSeguimiento, estadoReclamoCreation);
        return new ResponseEntity<>(seguimientoMapper.toDto(objeto), Helper.httpHeaders(
                "Se agrego correctamente el estadoReclamo con: " + estadoReclamoCreation.getEstado() + ", y la descripcion: " + estadoReclamoCreation.getDescripcion() + ", al seguimiento " + idSeguimiento + "."
        ), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidades entre periodo de fechas.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SeguimientoDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(name = "inicio", in = ParameterIn.PATH, description = "LocalDateTime.", example = "00:00:00 01-01-2000"),
            @Parameter(name = "fin", in = ParameterIn.PATH, description = "LocalDateTime.", example = "23:12:00 01-08-2024"),
    })
    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasPorCreadaEntreFechas(
            @PathVariable(name = "inicio")  String inicio,
            @PathVariable(name = "fin")  String fin){
        List<SeguimientoModel> listado = seguimientoService.buscarTodasPorCreadaEntreFechas(inicio, fin);
        ArrayList<SeguimientoDTO> reclamoDTOS = new ArrayList<>();
        for (SeguimientoModel reclamoModel : listado) {
            reclamoDTOS.add(seguimientoMapper.toDto(reclamoModel));
        }
        return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidades entre periodo de fechas, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SeguimientoDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(name = "inicio", in = ParameterIn.PATH, description = "LocalDateTime.", example = "00:00:00 01-01-2000"),
            @Parameter(name = "fin", in = ParameterIn.PATH, description = "LocalDateTime.", example = "23:12:00 01-08-2024"),
    })
    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas-con-eliminadas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasPorCreadaEntreFechasConEliminadas(
            @PathVariable(name = "inicio")  String inicio,
            @PathVariable(name = "fin")  String fin){
        List<SeguimientoModel> listado = seguimientoService.buscarTodasPorCreadaEntreFechasConEliminadas(inicio, fin);
        ArrayList<SeguimientoDTO> reclamoDTOS = new ArrayList<>();
        for (SeguimientoModel reclamoModel : listado) {
            reclamoDTOS.add(seguimientoMapper.toDto(reclamoModel));
        }
        return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades, incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve la entidad correspondiente al id.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SeguimientoDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Numerico."
            )
    })
    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<SeguimientoDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        SeguimientoModel objeto = seguimientoService.buscarPorId(id);
        return new ResponseEntity<>(seguimientoMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SeguimientoDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Numerico."
            )
    })
    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        SeguimientoModel objeto = seguimientoService.buscarPorIdConEliminadas(id);
        return new ResponseEntity<>(seguimientoMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + ", incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SeguimientoDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodas() {
        List<SeguimientoModel> listado = seguimientoService.buscarTodas();
        ArrayList<SeguimientoDTO> seguimientos = new ArrayList<>();
        for (SeguimientoModel seguimiento:listado) {
            seguimientos.add(seguimientoMapper.toDto(seguimiento));
        }
        return new ResponseEntity<>(seguimientos, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SeguimientoDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasConEliminadas() {
        List<SeguimientoModel> listado = seguimientoService.buscarTodasConEliminadas();
        ArrayList<SeguimientoDTO> seguimientos = new ArrayList<>();
        for (SeguimientoModel seguimiento:listado) {
            seguimientos.add(seguimientoMapper.toDto(seguimiento));
        }
        return new ResponseEntity<>(seguimientos, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades, incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = seguimientoService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = seguimientoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(
            summary = "Persisitir una entidad.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se persiste la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SeguimientoDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Objeto JSON conteniendo la descripcion del Seguimiento.",
                    example = "<br>{<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"descripcion\": \"Luminaria reparada.\",<br>" +
                            "<br>}"
            )
    })
    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> guardar(@Valid @RequestBody SeguimientoCreation seguimientoCreation) {
        SeguimientoModel objeto = seguimientoService.guardar(seguimientoCreation);
        return new ResponseEntity<>(seguimientoMapper.toDto(objeto), Helper.httpHeaders("Se persistio correctamente la entidad."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar una entidad.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se marca la entidad como eliminada.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se marca la entidad como eliminada.",
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Numerico."
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> borrar(@PathVariable(name = "id") Long id) {
        SeguimientoModel objeto = seguimientoService.eliminar(id);
        return new ResponseEntity<>(seguimientoMapper.toDto(objeto), Helper.httpHeaders("Se elimino correctamente la entidad con id: " + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Restaura una entidad marcada como eliminada..",
            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser reciclada primero debe estar eliminada. De consumirse correctamente se devuelve la entidad reciclada.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SeguimientoDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos ingresados no poseen el formato correcto.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el recurso buscado.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Error en la conversion de parametros ingresados.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "Debe autenticarse para acceder al recurso."
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee las autoridades necesarias para acceder al recurso."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Numerico."
            )
    })
    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<SeguimientoDTO> reciclar(@PathVariable(name = "id") Long id) {
        SeguimientoModel objeto = seguimientoService.reciclar(id);
        return new ResponseEntity<>(seguimientoMapper.toDto(objeto), Helper.httpHeaders("Se reciclo correctamente la entidad con id: " + id + "."), HttpStatus.OK);
    }

//    @Operation(
//            summary = "Destruye una entidad marcada como eliminada.",
//            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser destruida primero debe estar eliminada. De consumirse correctamente devuelve TRUE o FALSE.</strong>"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Recurso consumido correctamente, Objeto destruido.",
//                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Los datos ingresados no poseen el formato correcto.",
//                    content = { @Content(mediaType = "", schema = @Schema())},
//                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "No se encontro el recurso buscado.",
//                    content = { @Content(mediaType = "", schema = @Schema())},
//                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
//            ),
//            @ApiResponse(
//                    responseCode = "409",
//                    description = "Error en la conversion de parametros ingresados.",
//                    content = { @Content(mediaType = "", schema = @Schema())},
//                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
//            ),
//            @ApiResponse(
//                    responseCode = "401",
//                    content = { @Content(mediaType = "", schema = @Schema())},
//                    description = "Debe autenticarse para acceder al recurso."
//            ),
//            @ApiResponse(
//                    responseCode = "403",
//                    content = { @Content(mediaType = "", schema = @Schema())},
//                    description = "No se posee las autoridades necesarias para acceder al recurso."
//            )
//    })
//    @Parameters({
//            @Parameter(
//                    in = ParameterIn.PATH,
//                    description = "Numerico."
//            )
//    })
    @Operation(hidden = true)
    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") Long id) throws IOException {
        seguimientoService.destruir(id);
        String mensaje = "Se destruyo correctamente la entidad con id: " + id + ".";
        return new ResponseEntity<>(mensaje, Helper.httpHeaders(mensaje), HttpStatus.OK);
    }
}
