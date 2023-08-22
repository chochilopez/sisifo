package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.SeguimientoMapper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.service.implementation.SeguimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/seguimiento")
@RestController
@Slf4j
public class SeguimientoController {

    @Autowired
    private SeguimientoServiceImpl seguimientoService;
    @Autowired
    private SeguimientoMapper seguimientoMapper;

    @GetMapping(value = "/buscar-todas-por-descripcion/{descripcion}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasPorDescripcion(@PathVariable(name = "descripcion")  String descripcion) {
        EntityMessenger<SeguimientoModel> listado = seguimientoService.buscarTodasPorDescripcion(descripcion);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<SeguimientoDTO> seguimientoDTOS = new ArrayList<>();
            for (SeguimientoModel seguimientoModel : listado.getListado()) {
                seguimientoDTOS.add(seguimientoMapper.toDto(seguimientoModel));
            }
            return new ResponseEntity<>(seguimientoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-descripcion-con-eliminadas/{descripcion}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasPorDescripcionConEliminadas(@PathVariable(name = "descripcion")  String descripcion) {
        EntityMessenger<SeguimientoModel> listado = seguimientoService.buscarTodasPorDescripcionConEliminadas(descripcion);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<SeguimientoDTO> seguimientoDTOS = new ArrayList<>();
            for (SeguimientoModel seguimientoModel : listado.getListado()) {
                seguimientoDTOS.add(seguimientoMapper.toDto(seguimientoModel));
            }
            return new ResponseEntity<>(seguimientoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasPorCreadaEntreFechas(
            @PathVariable(name = "inicio")  String inicio,
            @PathVariable(name = "fin")  String fin){
        EntityMessenger<SeguimientoModel> listado = seguimientoService.buscarTodasPorCreadaEntreFechas(inicio, fin);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<SeguimientoDTO> reclamoDTOS = new ArrayList<>();
            for (SeguimientoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(seguimientoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas-con-eliminadas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasPorCreadaEntreFechasConEliminadas(
            @PathVariable(name = "inicio")  String inicio,
            @PathVariable(name = "fin")  String fin){
        EntityMessenger<SeguimientoModel> listado = seguimientoService.buscarTodasPorCreadaEntreFechasConEliminadas(inicio, fin);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<SeguimientoDTO> reclamoDTOS = new ArrayList<>();
            for (SeguimientoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(seguimientoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<SeguimientoDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(seguimientoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") Long id) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(seguimientoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodas() {
        EntityMessenger<SeguimientoModel> listado = seguimientoService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<SeguimientoDTO> SeguimientoDTOs = new ArrayList<>();
            for (SeguimientoModel SeguimientoModel:listado.getListado()) {
                SeguimientoDTOs.add(seguimientoMapper.toDto(SeguimientoModel));
            }
            return new ResponseEntity<>(SeguimientoDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<SeguimientoDTO>> buscarTodasConEliminadas() {
        EntityMessenger<SeguimientoModel> listado = seguimientoService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<SeguimientoDTO> SeguimientoDTOs = new ArrayList<>();
            for (SeguimientoModel SeguimientoModel:listado.getListado()) {
                SeguimientoDTOs.add(seguimientoMapper.toDto(SeguimientoModel));
            }
            return new ResponseEntity<>(SeguimientoDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = seguimientoService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = seguimientoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> insertar(@Valid @RequestBody SeguimientoCreation seguimientoCreation) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.insertar(seguimientoCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(seguimientoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> actualizar(@Valid @RequestBody SeguimientoModel seguimientoModel) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.actualizar(seguimientoModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(seguimientoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<SeguimientoDTO> borrar(@PathVariable(name = "id") Long id) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(seguimientoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<SeguimientoDTO> reciclar(@PathVariable(name = "id") Long id) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(seguimientoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") Long id) {
        EntityMessenger<SeguimientoModel> objeto = seguimientoService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
