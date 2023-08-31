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
import muni.eolida.sisifo.mapper.BarrioMapper;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.mapper.dto.BarrioDTO;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.service.implementation.BarrioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/barrio")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Endpoints BARRIO", description = "Recursos referidos a la consulta y persistencia de Barrios de la ciudad.")
public class BarrioController extends BaseController {
    private final BarrioServiceImpl barrioService;
    private final BarrioMapper barrioMapper;

    @Operation(
            summary = "Buscar entidades por nombre.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BarrioDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-nombre/{barrio}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasPorBarrio(@PathVariable(name = "barrio")  String barrio) {
        List<BarrioModel> listado = barrioService.buscarTodasPorBarrio(barrio);
        ArrayList<BarrioDTO> barrioDTOS = new ArrayList<>();
        for (BarrioModel barrioModel : listado) {
            barrioDTOS.add(barrioMapper.toDto(barrioModel));
        }
        return new ResponseEntity<>(barrioDTOS, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidades por nombre, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BarrioDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-nombre-con-eliminadas/{barrio}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasPorBarrioConEliminadas(@PathVariable(name = "barrio")  String barrio) {
        List<BarrioModel> listado = barrioService.buscarTodasPorBarrioConEliminadas(barrio);
        ArrayList<BarrioDTO> barrioDTOS = new ArrayList<>();
        for (BarrioModel barrioModel : listado) {
            barrioDTOS.add(barrioMapper.toDto(barrioModel));
        }
        return new ResponseEntity<>(barrioDTOS, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades, incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se devuelve la entidad correspondiente al id.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
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
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<BarrioDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        BarrioModel objeto = barrioService.buscarPorId(id);
        return new ResponseEntity<>(barrioMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
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
    public ResponseEntity<BarrioDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        BarrioModel objeto = barrioService.buscarPorIdConEliminadas(id);
        return new ResponseEntity<>(barrioMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + ", incluidas las borradas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BarrioDTO.class)))},
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
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<BarrioDTO>> buscarTodas() {
        List<BarrioModel> listado = barrioService.buscarTodas();
        ArrayList<BarrioDTO> barrios = new ArrayList<>();
        for (BarrioModel BarrioModel:listado) {
            barrios.add(barrioMapper.toDto(BarrioModel));
        }
        return new ResponseEntity<>(barrios, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BarrioDTO.class)))},
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
    public ResponseEntity<List<BarrioDTO>> buscarTodasConEliminadas() {
        List<BarrioModel> listado = barrioService.buscarTodasConEliminadas();
        ArrayList<BarrioDTO> BarrioDTOs = new ArrayList<>();
        for (BarrioModel BarrioModel:listado) {
            BarrioDTOs.add(barrioMapper.toDto(BarrioModel));
        }
        return new ResponseEntity<>(BarrioDTOs, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades, incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = barrioService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = barrioService.contarTodasConEliminadas();
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
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
                    description = "Objeto JSON conteniendo el nombre del Barrio.",
                    example = "<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"barrio\": \"Virgen del Guadalupe\"<br>}"
            )
    })
    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<BarrioDTO> guardar(@Valid @RequestBody BarrioCreation barrioCreation) {
        BarrioModel objeto = barrioService.guardar(barrioCreation);
        return new ResponseEntity<>(barrioMapper.toDto(objeto), Helper.httpHeaders("Se persistio correctamente la entidad."), HttpStatus.CREATED);
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
    public ResponseEntity<BarrioDTO> borrar(@PathVariable(name = "id") Long id) {
        BarrioModel objeto = barrioService.eliminar(id);
        return new ResponseEntity<>(barrioMapper.toDto(objeto), Helper.httpHeaders("Se elimino correctamente la entidad con id: " + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Restaura una entidad marcada como eliminada..",
            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser reciclada primero debe estar eliminada. De consumirse correctamente se devuelve la entidad reciclada.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
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
    public ResponseEntity<BarrioDTO> reciclar(@PathVariable(name = "id") Long id) {
        BarrioModel objeto = barrioService.reciclar(id);
        return new ResponseEntity<>(barrioMapper.toDto(objeto), Helper.httpHeaders("Se reciclo correctamente la entidad con id: " + id + "."), HttpStatus.OK);
    }

//    @Operation(
//            summary = "Destruye una entidad marcada como eliminada.",
//            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser destruida primero debe estar eliminada. De consumirse correctamente destruye totalmente el recurso.</strong>"
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
    public ResponseEntity<Boolean> destruir(@PathVariable(name = "id") Long id) {
        Boolean eliminada = barrioService.destruir(id);
        return new ResponseEntity<>(eliminada, Helper.httpHeaders(
                eliminada ? "Se destruyo correctamente la entidad con id: " + id + "." : "No se pudo destruir la entidad con id: " + id + "."
        ), HttpStatus.OK);
    }
}
