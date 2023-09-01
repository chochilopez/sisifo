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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.service.implementation.EstadoReclamoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/estadoReclamo")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Endpoints ESTADO RECLAMO", description = "Recursos referidos a la consulta y persistencia de los estados posibles de un reclamo.")
public class EstadoReclamoController extends BaseController {
    private final EstadoReclamoServiceImpl estadoReclamoService;
    private final EstadoReclamoMapper estadoReclamoMapper;

    @Operation(
            summary = "Buscar entidades por estado de reclamo.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoReclamoDTO.class)))},
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
                    description = "String."
            )
    })
    @GetMapping(value = "/buscar-por-estado-reclamo/{estadoReclamo}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<EstadoReclamoDTO> buscarPorEstadoReclamo(@PathVariable(name = "estadoReclamo")  String estadoReclamo) {
        EstadoReclamoModel objeto = estadoReclamoService.buscarPorEstadoReclamo(estadoReclamo);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con estado de reclamo :" + estadoReclamo + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidades por estado de reclamo, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoReclamoDTO.class))},
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
                    description = "String."
            )
    })
    @GetMapping(value = "/buscar-por-estado-reclamo-con-eliminadas/{estadoReclamo}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<EstadoReclamoDTO> buscarPorEstadoReclamoConEliminadas(@PathVariable(name = "estadoReclamo")  String estadoReclamo) {
        EstadoReclamoModel objeto = estadoReclamoService.buscarPorEstadoReclamo(estadoReclamo);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con estado de reclamo :" + estadoReclamo + ", incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve la entidad correspondiente al id.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoReclamoDTO.class))},
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
    public ResponseEntity<EstadoReclamoDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        EstadoReclamoModel objeto = estadoReclamoService.buscarPorId(id);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoReclamoDTO.class))},
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
    public ResponseEntity<EstadoReclamoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        EstadoReclamoModel objeto = estadoReclamoService.buscarPorIdConEliminadas(id);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + ", incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoReclamoDTO.class)))},
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
    public ResponseEntity<List<EstadoReclamoDTO>> buscarTodas() {
        List<EstadoReclamoModel> listado = estadoReclamoService.buscarTodas();
        ArrayList<EstadoReclamoDTO> estados = new ArrayList<>();
        for (EstadoReclamoModel estadoReclamo:listado) {
            estados.add(estadoReclamoMapper.toDto(estadoReclamo));
        }
        return new ResponseEntity<>(estados, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoReclamoDTO.class)))},
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
    public ResponseEntity<List<EstadoReclamoDTO>> buscarTodasConEliminadas() {
        List<EstadoReclamoModel> listado = estadoReclamoService.buscarTodasConEliminadas();
        ArrayList<EstadoReclamoDTO> estados = new ArrayList<>();
        for (EstadoReclamoModel estado:listado) {
            estados.add(estadoReclamoMapper.toDto(estado));
        }
        return new ResponseEntity<>(estados, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades, incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = estadoReclamoService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = estadoReclamoService.contarTodasConEliminadas();
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoReclamoDTO.class))},
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
                    description = "Objeto JSON conteniendo el nombre del Estado del Reclamo.",
                    example = "<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"estado\": \"EN_CURSO\"<br>}"
            )
    })
    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<EstadoReclamoDTO> insertar(@Valid @RequestBody EstadoReclamoCreation estadoReclamoCreation) {
        EstadoReclamoModel objeto = estadoReclamoService.guardar(estadoReclamoCreation);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se persistio correctamente la entidad."), HttpStatus.CREATED);
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
    public ResponseEntity<EstadoReclamoDTO> borrar(@PathVariable(name = "id") Long id) {
        EstadoReclamoModel objeto = estadoReclamoService.eliminar(id);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se elimino correctamente la entidad con id: " + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Restaura una entidad marcada como eliminada..",
            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser reciclada primero debe estar eliminada. De consumirse correctamente se devuelve la entidad reciclada.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoReclamoDTO.class))},
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
    public ResponseEntity<EstadoReclamoDTO> reciclar(@PathVariable(name = "id") Long id) {
        EstadoReclamoModel objeto = estadoReclamoService.reciclar(id);
        return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto), Helper.httpHeaders("Se reciclo correctamente la entidad con id: " + id + "."), HttpStatus.OK);
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
        estadoReclamoService.destruir(id);
        String mensaje = "Se destruyo correctamente la entidad con id: " + id + ".";
        return new ResponseEntity<>(mensaje, Helper.httpHeaders(mensaje), HttpStatus.OK);
    }
}
