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
import muni.eolida.sisifo.util.EntityMessenger;
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
public class BarrioController {
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "String."
            )
    })
    @GetMapping(value = "/buscar-por-nombre/{barrio}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasPorBarrio(@PathVariable(name = "barrio")  String barrio) {
        EntityMessenger<BarrioModel> listado = barrioService.buscarTodasPorBarrio(barrio);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<BarrioDTO> barrioDTOS = new ArrayList<>();
            for (BarrioModel barrioModel : listado.getListado()) {
                barrioDTOS.add(barrioMapper.toDto(barrioModel));
            }
            return new ResponseEntity<>(barrioDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "String."
            )
    })
    @GetMapping(value = "/buscar-por-nombre-con-eliminadas/{barrio}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasPorBarrioConEliminadas(@PathVariable(name = "barrio")  String barrio) {
        EntityMessenger<BarrioModel> listado = barrioService.buscarTodasPorBarrioConEliminadas(barrio);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<BarrioDTO> barrioDTOS = new ArrayList<>();
            for (BarrioModel barrioModel : listado.getListado()) {
                barrioDTOS.add(barrioMapper.toDto(barrioModel));
            }
            return new ResponseEntity<>(barrioDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
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
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
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
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<BarrioDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
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
    public ResponseEntity<BarrioDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
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
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BarrioDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<BarrioDTO>> buscarTodas() {
        EntityMessenger<BarrioModel> listado = barrioService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<BarrioDTO> BarrioDTOs = new ArrayList<>();
            for (BarrioModel BarrioModel:listado.getListado()) {
                BarrioDTOs.add(barrioMapper.toDto(BarrioModel));
            }
            return new ResponseEntity<>(BarrioDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
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
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BarrioDTO.class)))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasConEliminadas() {
        EntityMessenger<BarrioModel> listado = barrioService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<BarrioDTO> BarrioDTOs = new ArrayList<>();
            for (BarrioModel BarrioModel:listado.getListado()) {
                BarrioDTOs.add(barrioMapper.toDto(BarrioModel));
            }
            return new ResponseEntity<>(BarrioDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
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
            summary = "Persisitir una nueva entidad.",
            description = "Rol/Autoridad requerida: CAPATAZ<br><strong>De consumirse correctamente se persiste la nueva entidad.</strong>"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso consumido correctamente, se devuelve el nuevo objeto JSON.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
    public ResponseEntity<BarrioDTO> insertar(@Valid @RequestBody BarrioCreation barrioCreation) {
        EntityMessenger<BarrioModel> objeto = barrioService.insertar(barrioCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
            )
    })
    @Parameters({
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Objeto JSON conteniendo el nombre del Barrio.",
                    example = "<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"id\": \"2\",<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;\"barrio\": \"Virgen del Guadalupe\"<br>}"
            )
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<BarrioDTO> actualizar(@Valid @RequestBody BarrioModel barrioModel) {
        EntityMessenger<BarrioModel> objeto = barrioService.actualizar(barrioModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
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
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
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
    public ResponseEntity<BarrioDTO> borrar(@PathVariable(name = "id") Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BarrioDTO.class))},
                    headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
            ),
            @ApiResponse(
                    responseCode = "202",
                    description = "Recurso consumido correctamente, sin embargo ocurrio un error.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
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
    public ResponseEntity<BarrioDTO> reciclar(@PathVariable(name = "id") Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
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
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Ocurrio una excepcion al consumir el recurso.",
                    headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre la excepcion.")}
            ),
            @ApiResponse(
                    responseCode = "401",
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
        EntityMessenger<BarrioModel> objeto = barrioService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
