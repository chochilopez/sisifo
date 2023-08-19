package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.mapper.TipoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.service.implementation.TipoReclamoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/tipoReclamo")
@RestController
@Slf4j
public class TipoReclamoController {

    @Autowired
    private TipoReclamoServiceImpl tipoReclamoService;
    @Autowired
    private TipoReclamoMapper tipoReclamoMapper;

    @GetMapping(value = "/buscar-todas-por-area-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodasPorAreaId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 5) Long id) {
        EntityMessenger<TipoReclamoModel> listado = tipoReclamoService.buscarTodasPorAreaId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<TipoReclamoDTO> tipoReclamoDTOS = new ArrayList<>();
            for (TipoReclamoModel tipoReclamoModel : listado.getListado()) {
                tipoReclamoDTOS.add(tipoReclamoMapper.toDto(tipoReclamoModel));
            }
            return new ResponseEntity<>(tipoReclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-area-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodasPorAreaIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 5) Long id) {
        EntityMessenger<TipoReclamoModel> listado = tipoReclamoService.buscarTodasPorAreaIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<TipoReclamoDTO> tipoReclamoDTOS = new ArrayList<>();
            for (TipoReclamoModel tipoReclamoModel : listado.getListado()) {
                tipoReclamoDTOS.add(tipoReclamoMapper.toDto(tipoReclamoModel));
            }
            return new ResponseEntity<>(tipoReclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-tipo/{tipo}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodasPorTipo(@PathVariable(name = "tipo") @javax.validation.constraints.Size(min = 3, max = 40) String tipo) {
        EntityMessenger<TipoReclamoModel> listado = tipoReclamoService.buscarTodasPorTipo(tipo);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<TipoReclamoDTO> tipoReclamoDTOS = new ArrayList<>();
            for (TipoReclamoModel tipoReclamoModel : listado.getListado()) {
                tipoReclamoDTOS.add(tipoReclamoMapper.toDto(tipoReclamoModel));
            }
            return new ResponseEntity<>(tipoReclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-tipo-con-eliminadas/{tipo}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodasPorTipoConEliminadas(@PathVariable(name = "tipo") @javax.validation.constraints.Size(min = 3, max = 40) String tipo) {
        EntityMessenger<TipoReclamoModel> listado = tipoReclamoService.buscarTodasPorTipoConEliminadas(tipo);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<TipoReclamoDTO> tipoReclamoDTOS = new ArrayList<>();
            for (TipoReclamoModel tipoReclamoModel : listado.getListado()) {
                tipoReclamoDTOS.add(tipoReclamoMapper.toDto(tipoReclamoModel));
            }
            return new ResponseEntity<>(tipoReclamoDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<TipoReclamoDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<TipoReclamoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodas() {
        EntityMessenger<TipoReclamoModel> listado = tipoReclamoService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<TipoReclamoDTO> TipoReclamoDTOs = new ArrayList<>();
            for (TipoReclamoModel TipoReclamoModel:listado.getListado()) {
                TipoReclamoDTOs.add(tipoReclamoMapper.toDto(TipoReclamoModel));
            }
            return new ResponseEntity<>(TipoReclamoDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodasConEliminadas() {
        EntityMessenger<TipoReclamoModel> listado = tipoReclamoService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<TipoReclamoDTO> TipoReclamoDTOs = new ArrayList<>();
            for (TipoReclamoModel TipoReclamoModel:listado.getListado()) {
                TipoReclamoDTOs.add(tipoReclamoMapper.toDto(TipoReclamoModel));
            }
            return new ResponseEntity<>(TipoReclamoDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = tipoReclamoService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = tipoReclamoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<TipoReclamoDTO> insertar(@Valid @RequestBody TipoReclamoCreation tipoReclamoCreation) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.insertar(tipoReclamoCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<TipoReclamoDTO> actualizar(@Valid @RequestBody TipoReclamoModel tipoReclamoModel) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.actualizar(tipoReclamoModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<TipoReclamoDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<TipoReclamoDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> objeto = tipoReclamoService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
