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
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.ReclamoDTO;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.service.implementation.ReclamoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/reclamo")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Endpoints RECLAMO", description = "Recursos referidos a la consulta y persistencia de Reclamos.")
public class ReclamoController {
    private final ReclamoServiceImpl reclamoService;
    private final ReclamoMapper reclamoMapper;

    @Operation(
            summary = "Buscar todos los reclamos iniciados por la persona.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-mis-reclamos")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<ReclamoDTO>> buscarMisReclamos() {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarMisReclamos();
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de creador.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-creador-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadorId(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorCreadorId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de creador, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    @GetMapping(value = "/buscar-todas-por-creador-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadorIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorCreadorIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de tipo de reclamo.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-tipo-reclamo-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorTipoReclamoId(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorTipoReclamoId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de tipo de reclamo, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    @GetMapping(value = "/buscar-todas-por-tipo-reclamo-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorTipoReclamoIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorTipoReclamoIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de barrio.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-barrio-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorBarrioId(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorBarrioId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de barrio, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    @GetMapping(value = "/buscar-todas-por-barrio-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorBarrioIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorBarrioIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de calle.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-calle-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCalleId(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorCalleId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por id de calle, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    @GetMapping(value = "/buscar-todas-por-calle-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCalleIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorCalleIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por descripcion.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-descripcion/{descripcion}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorDescripcion(@PathVariable(name = "descripcion")  String descripcion) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorDescripcion(descripcion);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades por descripcion, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @GetMapping(value = "/buscar-todas-por-descripcion-con-eliminadas/{descripcion}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorDescripcionConEliminadas(@PathVariable(name = "descripcion")  String descripcion) {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorDescripcionConEliminadas(descripcion);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades entre periodo de fechas.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
            @Parameter(name = "inicio", in = ParameterIn.PATH, description = "LocalDateTime.", example = "2017-01-13T17:09:42.411"),
            @Parameter(name = "fin", in = ParameterIn.PATH, description = "LocalDateTime.", example = "2023-12-22T17:09:42.411"),
    })
    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadaEntreFechas(
            @PathVariable(name = "inicio")  String inicio,
            @PathVariable(name = "fin")  String fin){
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorCreadaEntreFechas(inicio, fin);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidades entre periodo de fechas, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
            @Parameter(name = "inicio", in = ParameterIn.PATH, description = "LocalDateTime.", example = "2017-01-13T17:09:42.411"),
            @Parameter(name = "fin", in = ParameterIn.PATH, description = "LocalDateTime.", example = "2023-12-22T17:09:42.411"),
    })
    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas-con-eliminadas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadaEntreFechasConEliminadas(
            @PathVariable(name = "inicio")  String inicio,
            @PathVariable(name = "fin")  String fin){
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasPorCreadaEntreFechasConEliminadas(inicio, fin);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @Operation(
            summary = "Buscar entidad por ID.",
            description = "Rol/Autoridad requerida: EMPLEADO<br><strong>De consumirse correctamente se devuelve la entidad correspondiente al id.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<ReclamoDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> objeto = reclamoService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @Operation(
            summary = "Buscar entidad por ID, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> objeto = reclamoService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @Operation(
            summary = "Buscar todas las entidades.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodas() {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<ReclamoDTO> ReclamoDTOs = new ArrayList<>();
            for (ReclamoModel ReclamoModel:listado.getListado()) {
                ReclamoDTOs.add(reclamoMapper.toDto(ReclamoModel));
            }
            return new ResponseEntity<>(ReclamoDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @Operation(
            summary = "Buscar todas las entidades, incluidas las eliminadas.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se devuelve un Array con todos las entidades en formato JSON.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso consumido correctamente, se devuelve Array de objetos JSON.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReclamoDTO.class)))},
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
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasConEliminadas() {
        EntityMessenger<ReclamoModel> listado = reclamoService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<ReclamoDTO> ReclamoDTOs = new ArrayList<>();
            for (ReclamoModel ReclamoModel:listado.getListado()) {
                ReclamoDTOs.add(reclamoMapper.toDto(ReclamoModel));
            }
            return new ResponseEntity<>(ReclamoDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = reclamoService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = reclamoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @Operation(
            summary = "Persisitir una nueva entidad.",
            description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se persiste la nueva entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el nuevo objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
                    description = "Objeto JSON conteniendo: altura, barrio_id, calle_id, interseccion_id, coordinadaX, coordinadaY, descripcion," +
                            " entreCalle1_id, entreCalle2_id, image_id, tipoReclamo_id y persona_id del Reclamo.",
                    example = "<br>{<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"altura\": \"1234\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"barrio_id\": \"2\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"calle_id\": \"3\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"interseccion_id\": \"4\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"coordinadaX\": \"-32.031247495487506\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"coordinadaY\": \"-60.30571350279057\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"descripcion\": \"La luminaria no funciona\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"entreCalle1_id\": \"4\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"entreCalle2_id\": \"5\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"imagen_id\": \"1\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"tipoReclamo_id\": \"2\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"persona_id\": \"1\",<br>" +
                            "<br>}"
            )
    })
    @PutMapping
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<ReclamoDTO> insertar(@Valid @RequestBody ReclamoCreation reclamoCreation) {
        EntityMessenger<ReclamoModel> objeto = reclamoService.insertar(reclamoCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @Operation(
            summary = "Modificar una entidad.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se persiste los cambios en la entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el objeto JSON modificado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
                    description = "Objeto JSON conteniendo: id, altura, barrio, calle, interseccion, coordinadaX, coordinadaY, descripcion," +
                            " entreCalle1, entreCalle2, imagen, tipoReclamo y persona del Reclamo.",
                    example = "<br>{<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"id\": \"2\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"altura\": \"1234\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"barrio\": \"{}\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"calle\": \"{}\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"interseccion\": \"4\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"coordinadaX\": \"-32.031247495487506\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"coordinadaY\": \"-60.30571350279057\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"descripcion\": \"La luminaria no funciona\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"entreCalle1\": \"{}\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"entreCalle2\": \"{}\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"image\": \"{}\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"tipoReclamo\": \"{}\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"persona\": \"{}\",<br>" +
                            "<br>}"
            )
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> actualizar(@Valid @RequestBody ReclamoModel reclamoModel) {
        EntityMessenger<ReclamoModel> objeto = reclamoService.actualizar(reclamoModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
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
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> borrar(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> objeto = reclamoService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReclamoDTO.class))},
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
    public ResponseEntity<ReclamoDTO> reciclar(@PathVariable(name = "id") Long id) {
        EntityMessenger<ReclamoModel> objeto = reclamoService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
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
        EntityMessenger<ReclamoModel> objeto = reclamoService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
