package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.ReclamoDTO;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.service.implementation.ReclamoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/reclamo")
@RestController
@Slf4j
public class ReclamoController {

    @Autowired
    private ReclamoServiceImpl reclamoService;
    @Autowired
    private ReclamoMapper reclamoMapper;

    @GetMapping(value = "/buscar-mis-reclamos")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<ReclamoDTO>> buscarMisReclamos() {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarMisReclamos();
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-creador-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorCreadorId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-creador-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorCreadorIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-tipo-reclamo-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorTipoReclamoId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorTipoReclamoId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-tipo-reclamo-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorTipoReclamoIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorTipoReclamoIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-barrio-id/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorBarrioId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorBarrioId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-barrio-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorBarrioIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorBarrioIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-calle-id/{id}")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCalleId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorBarrioId(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-calle-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCalleIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 40) Long id) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorBarrioIdConEliminadas(id);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-descripcion/{descripcion}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorDescripcion(@PathVariable(name = "reclamo") @javax.validation.constraints.Size(min = 3, max = 100) String descripcion) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorDescripcion(descripcion);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-descripcion-con-eliminadas/{descripcion}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorDescripcionConEliminadas(@PathVariable(name = "descripcion") @javax.validation.constraints.Size(min = 3, max = 100) String descripcion) {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorDescripcionConEliminadas(descripcion);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadaEntreFechas(
            @PathVariable(name = "inicio") @javax.validation.constraints.Size(min = 3, max = 40) String inicio,
            @PathVariable(name = "fin") @javax.validation.constraints.Size(min = 3, max = 40) String fin){
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorCreadaEntreFechas(inicio, fin);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-todas-por-creada-entre-fechas-con-eliminadas/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasPorCreadaEntreFechasConEliminadas(
            @PathVariable(name = "inicio") @javax.validation.constraints.Size(min = 3, max = 40) String inicio,
            @PathVariable(name = "fin") @javax.validation.constraints.Size(min = 3, max = 40) String fin){
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasPorCreadaEntreFechasConEliminadas(inicio, fin);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : listado.getListado()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<ReclamoDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodas() {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<ReclamoDTO> ReclamoDTOs = new ArrayList<>();
            for (ReclamoModel ReclamoModel:listado.getListado()) {
                ReclamoDTOs.add(reclamoMapper.toDto(ReclamoModel));
            }
            return new ResponseEntity<>(ReclamoDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodasConEliminadas() {
        EntidadMensaje<ReclamoModel> listado = reclamoService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<ReclamoDTO> ReclamoDTOs = new ArrayList<>();
            for (ReclamoModel ReclamoModel:listado.getListado()) {
                ReclamoDTOs.add(reclamoMapper.toDto(ReclamoModel));
            }
            return new ResponseEntity<>(ReclamoDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = reclamoService.contarTodas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = reclamoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> insertar(@Valid @RequestBody ReclamoCreation reclamoCreation) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.insertar(reclamoCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> actualizar(@Valid @RequestBody ReclamoModel reclamoModel) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.actualizar(reclamoModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ReclamoDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<ReclamoDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ReclamoModel> objeto = reclamoService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }
}
