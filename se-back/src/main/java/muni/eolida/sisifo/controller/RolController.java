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
import muni.eolida.sisifo.mapper.dto.RolDTO;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.RolMapper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.service.implementation.RolServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/rol")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Endpoints ROL", description = "Recursos referidos a la consulta y persistencia de Roles o autoridades.")
public class RolController extends BaseController {
    private final RolServiceImpl rolService;
    private final RolMapper rolMapper;

    @Operation(
            summary = "Buscar entidades por rol.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
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
    @GetMapping(value = "/buscar-por-rol/{rol}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> buscarTodasPorRol(@PathVariable(name = "rol")  String rol) {
        RolModel objeto = rolService.buscarPorRol(rol);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con rol :" + rol + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidades por rol, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
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
    @GetMapping(value = "/buscar-por-rol-con-eliminadas/{rol}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> buscarPorRolConEliminadas(@PathVariable(name = "rol")  String rol) {
        RolModel objeto = rolService.buscarPorRolConEliminadas(rol);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con rol :" + rol + ", incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se devuelve la entidad correspondiente al id.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
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
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        RolModel objeto = rolService.buscarPorId(id);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar entidad por ID, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
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
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        RolModel objeto = rolService.buscarPorIdConEliminadas(id);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se encontro una entidad con id :" + id + ", incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RolDTO.class)))},
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
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<List<RolDTO>> buscarTodas() {
        List<RolModel> listado = rolService.buscarTodas();
        ArrayList<RolDTO> roles = new ArrayList<>();
        for (RolModel rol:listado) {
            roles.add(rolMapper.toDto(rol));
        }
        return new ResponseEntity<>(roles, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades."), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar todas las entidades, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RolDTO.class)))},
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
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<List<RolDTO>> buscarTodasConEliminadas() {
        List<RolModel> listado = rolService.buscarTodasConEliminadas();
        ArrayList<RolDTO> roles = new ArrayList<>();
        for (RolModel rol:listado) {
            roles.add(rolMapper.toDto(rol));
        }
        return new ResponseEntity<>(roles, Helper.httpHeaders("Se encontraron " + listado.size() + " entidades, incluidas las eliminadas."), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = rolService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = rolService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(
            summary = "Persisitir una entidad.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se persiste la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
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
                    description = "Objeto JSON conteniendo el nombre del Rol.",
                    example = "<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"rol\": \"SUPER_ADMIN\"<br>}"
            )
    })
    @PutMapping
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> guardar(@Valid @RequestBody RolCreation rolCreation) {
        RolModel objeto = rolService.guardar(rolCreation);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se persistio correctamente la entidad."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar una entidad.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se marca la entidad como eliminada.</strong>"
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
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> borrar(@PathVariable(name = "id") Long id) {
        RolModel objeto = rolService.eliminar(id);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se elimino correctamente la entidad con id: " + id + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "Restaura una entidad marcada como eliminada..",
            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser reciclada primero debe estar eliminada. De consumirse correctamente se devuelve la entidad reciclada.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
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
    public ResponseEntity<RolDTO> reciclar(@PathVariable(name = "id") Long id) {
        RolModel objeto = rolService.reciclar(id);
        return new ResponseEntity<>(rolMapper.toDto(objeto), Helper.httpHeaders("Se reciclo correctamente la entidad con id: " + id + "."), HttpStatus.OK);
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
        rolService.destruir(id);
        String mensaje = "Se destruyo correctamente la entidad con id: " + id + ".";
        return new ResponseEntity<>(mensaje, Helper.httpHeaders(mensaje), HttpStatus.OK);
    }
}
