package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
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
        EntityMessenger<CalleModel> listado = calleService.buscarTodasPorCalle(calle);
        if (listado.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMessage())).build();
        } else if (listado.getStatusCode() == 200) {
            ArrayList<CalleDTO> calleDTOS = new ArrayList<>();
            for (CalleModel calleModel : listado.getList()) {
                calleDTOS.add(calleMapper.toDto(calleModel));
            }
            return new ResponseEntity<>(calleDTOS, Helper.httpHeaders(listado.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-nombre-con-eliminadas/{calle}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<CalleDTO>> buscarTodasPorCalleConEliminadas(@PathVariable(name = "calle") @javax.validation.constraints.Size(min = 3, max = 40) String calle) {
        EntityMessenger<CalleModel> listado = calleService.buscarTodasPorCalleConEliminadas(calle);
        if (listado.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMessage())).build();
        } else if (listado.getStatusCode() == 200) {
            ArrayList<CalleDTO> calleDTOS = new ArrayList<>();
            for (CalleModel calleModel : listado.getList()) {
                calleDTOS.add(calleMapper.toDto(calleModel));
            }
            return new ResponseEntity<>(calleDTOS, Helper.httpHeaders(listado.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<CalleDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> objeto = calleService.buscarPorId(id);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObject()), Helper.httpHeaders(objeto.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> objeto = calleService.buscarPorIdConEliminadas(id);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObject()), Helper.httpHeaders(objeto.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<CalleDTO>> buscarTodas() {
        EntityMessenger<CalleModel> listado = calleService.buscarTodas();
        if (listado.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMessage())).build();
        else if (listado.getStatusCode() == 200){
            ArrayList<CalleDTO> CalleDTOs = new ArrayList<>();
            for (CalleModel CalleModel:listado.getList()) {
                CalleDTOs.add(calleMapper.toDto(CalleModel));
            }
            return new ResponseEntity<>(CalleDTOs, Helper.httpHeaders(listado.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<CalleDTO>> buscarTodasConEliminadas() {
        EntityMessenger<CalleModel> listado = calleService.buscarTodasConEliminadas();
        if (listado.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMessage())).build();
        else if (listado.getStatusCode() == 200){
            ArrayList<CalleDTO> CalleDTOs = new ArrayList<>();
            for (CalleModel CalleModel:listado.getList()) {
                CalleDTOs.add(calleMapper.toDto(CalleModel));
            }
            return new ResponseEntity<>(CalleDTOs, Helper.httpHeaders(listado.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMessage())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = calleService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = calleService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> insertar(@Valid @RequestBody CalleCreation calleCreation) {
        EntityMessenger<CalleModel> objeto = calleService.insertar(calleCreation);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObject()), Helper.httpHeaders(objeto.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> actualizar(@Valid @RequestBody CalleModel calleModel) {
        EntityMessenger<CalleModel> objeto = calleService.actualizar(calleModel);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObject()), Helper.httpHeaders(objeto.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> objeto = calleService.eliminar(id);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObject()), Helper.httpHeaders(objeto.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<CalleDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> objeto = calleService.reciclar(id);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(objeto.getObject()), Helper.httpHeaders(objeto.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> objeto = calleService.destruir(id);
        if (objeto.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMessage())).build();
        else if (objeto.getStatusCode() == 200)
            return new ResponseEntity<>(objeto.getMessage(), Helper.httpHeaders(objeto.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMessage())).build();
    }
}
