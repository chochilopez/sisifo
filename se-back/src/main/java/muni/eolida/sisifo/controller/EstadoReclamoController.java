package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.service.implementation.EstadoReclamoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/estadoReclamo")
@RestController
@Slf4j
public class EstadoReclamoController {

    @Autowired
    private EstadoReclamoServiceImpl estadoReclamoService;
    @Autowired
    private EstadoReclamoMapper estadoReclamoMapper;

    @GetMapping(value = "/buscar-por-estado-reclamo/{estadoReclamo}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<EstadoReclamoDTO>> buscarPorEstadoReclamo(@PathVariable(name = "estadoReclamo") @javax.validation.constraints.Size(min = 3, max = 40) String estadoReclamo) {
        EntidadMensaje<EstadoReclamoModel> listado = estadoReclamoService.buscarPorEstadoReclamo(estadoReclamo);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<EstadoReclamoDTO> estadoReclamoDTOS = new ArrayList<>();
            for (EstadoReclamoModel estadoReclamoModel : listado.getListado()) {
                estadoReclamoDTOS.add(estadoReclamoMapper.toDto(estadoReclamoModel));
            }
            return new ResponseEntity<>(estadoReclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-estado-reclamo-con-eliminadas/{estadoReclamo}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<EstadoReclamoDTO>> buscarPorEstadoReclamoConEliminadas(@PathVariable(name = "estadoReclamo") @javax.validation.constraints.Size(min = 3, max = 40) String estadoReclamo) {
        EntidadMensaje<EstadoReclamoModel> listado = estadoReclamoService.buscarPorEstadoReclamoConEliminadas(estadoReclamo);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<EstadoReclamoDTO> estadoReclamoDTOS = new ArrayList<>();
            for (EstadoReclamoModel estadoReclamoModel : listado.getListado()) {
                estadoReclamoDTOS.add(estadoReclamoMapper.toDto(estadoReclamoModel));
            }
            return new ResponseEntity<>(estadoReclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<EstadoReclamoDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<EstadoReclamoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<EstadoReclamoDTO>> buscarTodas() {
        EntidadMensaje<EstadoReclamoModel> listado = estadoReclamoService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<EstadoReclamoDTO> EstadoReclamoDTOs = new ArrayList<>();
            for (EstadoReclamoModel EstadoReclamoModel:listado.getListado()) {
                EstadoReclamoDTOs.add(estadoReclamoMapper.toDto(EstadoReclamoModel));
            }
            return new ResponseEntity<>(EstadoReclamoDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<EstadoReclamoDTO>> buscarTodasConEliminadas() {
        EntidadMensaje<EstadoReclamoModel> listado = estadoReclamoService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<EstadoReclamoDTO> EstadoReclamoDTOs = new ArrayList<>();
            for (EstadoReclamoModel EstadoReclamoModel:listado.getListado()) {
                EstadoReclamoDTOs.add(estadoReclamoMapper.toDto(EstadoReclamoModel));
            }
            return new ResponseEntity<>(EstadoReclamoDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = estadoReclamoService.contarTodas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = estadoReclamoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<EstadoReclamoDTO> insertar(@Valid @RequestBody EstadoReclamoCreation estadoReclamoCreation) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.insertar(estadoReclamoCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<EstadoReclamoDTO> actualizar(@Valid @RequestBody EstadoReclamoModel estadoReclamoModel) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.actualizar(estadoReclamoModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<EstadoReclamoDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<EstadoReclamoDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(estadoReclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<EstadoReclamoModel> objeto = estadoReclamoService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }
}
