package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.CalleMapper;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.mapper.dto.CalleDTO;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.service.implementation.CalleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/calle")
@RestController
@Slf4j
public class CalleController {

    @Autowired
    private CalleServiceImpl calleService;
    @Autowired
    private CalleMapper calleMapper;

    @GetMapping(value = "/buscar-por-nombre/{calle}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<CalleDTO>> buscarTodasPorCalle(@PathVariable(name = "calle") @javax.validation.constraints.Size(min = 3, max = 40) String calle) {
        EntidadMensaje<CalleModel> listado = calleService.buscarTodasPorCalle(calle);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<CalleDTO> calleDTOS = new ArrayList<>();
            for (CalleModel calleModel : listado.getListado()) {
                calleDTOS.add(calleMapper.toDto(calleModel));
            }
            return new ResponseEntity<>(calleDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-nombre-con-eliminadas/{calle}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<CalleDTO>> buscarTodasPorCalleConEliminadas(@PathVariable(name = "calle") @javax.validation.constraints.Size(min = 3, max = 40) String calle) {
        EntidadMensaje<CalleModel> listado = calleService.buscarTodasPorCalleConEliminadas(calle);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<CalleDTO> calleDTOS = new ArrayList<>();
            for (CalleModel calleModel : listado.getListado()) {
                calleDTOS.add(calleMapper.toDto(calleModel));
            }
            return new ResponseEntity<>(calleDTOS, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<CalleDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<CalleModel> objeto = calleService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<CalleModel> objeto = calleService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<CalleDTO>> buscarTodas() {
        EntidadMensaje<CalleModel> listado = calleService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<CalleDTO> CalleDTOs = new ArrayList<>();
            for (CalleModel CalleModel:listado.getListado()) {
                CalleDTOs.add(calleMapper.toDto(CalleModel));
            }
            return new ResponseEntity<>(CalleDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<CalleDTO>> buscarTodasConEliminadas() {
        EntidadMensaje<CalleModel> listado = calleService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<CalleDTO> CalleDTOs = new ArrayList<>();
            for (CalleModel CalleModel:listado.getListado()) {
                CalleDTOs.add(calleMapper.toDto(CalleModel));
            }
            return new ResponseEntity<>(CalleDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = calleService.contarTodas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = calleService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> insertar(@Valid @RequestBody CalleCreation calleCreation) {
        EntidadMensaje<CalleModel> objeto = calleService.insertar(calleCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> actualizar(@Valid @RequestBody CalleModel calleModel) {
        EntidadMensaje<CalleModel> objeto = calleService.actualizar(calleModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<CalleModel> objeto = calleService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<CalleDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<CalleModel> objeto = calleService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<CalleModel> objeto = calleService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }
}
