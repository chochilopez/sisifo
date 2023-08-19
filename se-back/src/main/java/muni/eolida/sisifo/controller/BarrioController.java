package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.BarrioMapper;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.mapper.dto.BarrioDTO;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.service.implementation.BarrioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/barrio")
@RestController
@Slf4j
public class BarrioController {

    @Autowired
    private BarrioServiceImpl barrioService;
    @Autowired
    private BarrioMapper barrioMapper;

    @GetMapping(value = "/buscar-por-nombre/{barrio}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasPorBarrio(@PathVariable(name = "barrio") @javax.validation.constraints.Size(min = 3, max = 40) String barrio) {
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

    @GetMapping(value = "/buscar-por-nombre-con-eliminadas/{barrio}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<BarrioDTO>> buscarTodasPorBarrioConEliminadas(@PathVariable(name = "barrio") @javax.validation.constraints.Size(min = 3, max = 40) String barrio) {
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

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<BarrioDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<BarrioDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

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

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = barrioService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = barrioService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<BarrioDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<BarrioDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(barrioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<BarrioModel> objeto = barrioService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
