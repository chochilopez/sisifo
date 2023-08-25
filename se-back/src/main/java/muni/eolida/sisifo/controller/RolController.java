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
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.RolMapper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.service.implementation.RolServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/rol")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Endpoints ROL", description = "Recursos referidos a la consulta y persistencia de Roles o autoridades.")
public class RolController {
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
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RolDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
    public ResponseEntity<List<RolDTO>> buscarTodasPorRol(@PathVariable(name = "rol")  String rol) {
        EntityMessenger<RolModel> listado = rolService.buscarPorRol(rol);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<RolDTO> rolDTOS = new ArrayList<>();
            for (RolModel rolModel : listado.getListado()) {
                rolDTOS.add(rolMapper.toDto(rolModel));
            }
            return new ResponseEntity<>(rolDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por rol, incluidas las eliminadas.",
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
    public ResponseEntity<List<RolDTO>> buscarTodasPorRolConEliminadas(@PathVariable(name = "rol")  String rol) {
        EntityMessenger<RolModel> listado = rolService.buscarPorRolConEliminadas(rol);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<RolDTO> rolDTOS = new ArrayList<>();
            for (RolModel rolModel : listado.getListado()) {
                rolDTOS.add(rolMapper.toDto(rolModel));
            }
            return new ResponseEntity<>(rolDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
        EntityMessenger<RolModel> objeto = rolService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(rolMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
        EntityMessenger<RolModel> objeto = rolService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(rolMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<List<RolDTO>> buscarTodas() {
        EntityMessenger<RolModel> listado = rolService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<RolDTO> RolDTOs = new ArrayList<>();
            for (RolModel RolModel:listado.getListado()) {
                RolDTOs.add(rolMapper.toDto(RolModel));
            }
            return new ResponseEntity<>(RolDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<List<RolDTO>> buscarTodasConEliminadas() {
        EntityMessenger<RolModel> listado = rolService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<RolDTO> RolDTOs = new ArrayList<>();
            for (RolModel RolModel:listado.getListado()) {
                RolDTOs.add(rolMapper.toDto(RolModel));
            }
            return new ResponseEntity<>(RolDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
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
            summary = "Persisitir una nueva entidad.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se persiste la nueva entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el nuevo objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
    public ResponseEntity<RolDTO> insertar(@Valid @RequestBody RolCreation rolCreation) {
        EntityMessenger<RolModel> objeto = rolService.insertar(rolCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(rolMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @Operation(
            summary = "Modificar una entidad.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>De consumirse correctamente se persiste los cambios en la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el objeto JSON modificado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Objeto JSON conteniendo el id y nombre del Rol.",
                    example = "<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"id\": \"12\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"barrio\": \"SUPER_ADMIN\"<br>}"
            )
    })
    @PostMapping
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<RolDTO> actualizar(@Valid @RequestBody RolModel rolModel) {
        EntityMessenger<RolModel> objeto = rolService.actualizar(rolModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(rolMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
        EntityMessenger<RolModel> objeto = rolService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(rolMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
        EntityMessenger<RolModel> objeto = rolService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(rolMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @Operation(
            summary = "Destruye una entidad marcada como eliminada.",
            description = "Rol/Autoridad requerida: JEFE<br><strong>La entidad en orden de ser destruida primero debe estar eliminada. De consumirse correctamente destruye totalmente el recurso.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, Objeto destruido.",
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = { @Content(mediaType = "", schema = @Schema())},
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Numerico."
            )
    })
    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") Long id) {
        EntityMessenger<RolModel> objeto = rolService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
