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
        EntityMessenger<CalleModel> calleModelEntityMessenger = calleService.buscarTodasPorCalle(calle);
        if (calleModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(calleModelEntityMessenger.getMessage())).build();
        } else if (calleModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<CalleDTO> calleDTOS = new ArrayList<>();
            for (CalleModel calleModel : calleModelEntityMessenger.getList()) {
                calleDTOS.add(calleMapper.toDto(calleModel));
            }
            return new ResponseEntity<>(calleDTOS, Helper.httpHeaders(calleModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(calleModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-nombre-con-eliminadas/{calle}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<CalleDTO>> buscarTodasPorCalleConEliminadas(@PathVariable(name = "calle") @javax.validation.constraints.Size(min = 3, max = 40) String calle) {
        EntityMessenger<CalleModel> calleModelEntityMessenger = calleService.buscarTodasPorCalleConEliminadas(calle);
        if (calleModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(calleModelEntityMessenger.getMessage())).build();
        } else if (calleModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<CalleDTO> calleDTOS = new ArrayList<>();
            for (CalleModel calleModel : calleModelEntityMessenger.getList()) {
                calleDTOS.add(calleMapper.toDto(calleModel));
            }
            return new ResponseEntity<>(calleDTOS, Helper.httpHeaders(calleModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(calleModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<CalleDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarPorId(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarPorIdConEliminadas(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<CalleDTO>> buscarTodas() {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarTodas();
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200){
            ArrayList<CalleDTO> CalleDTOs = new ArrayList<>();
            for (CalleModel CalleModel:CalleModelEntityMessenger.getList()) {
                CalleDTOs.add(calleMapper.toDto(CalleModel));
            }
            return new ResponseEntity<>(CalleDTOs, Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<CalleDTO>> buscarTodasConEliminadas() {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarTodasConEliminadas();
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200){
            ArrayList<CalleDTO> CalleDTOs = new ArrayList<>();
            for (CalleModel CalleModel:CalleModelEntityMessenger.getList()) {
                CalleDTOs.add(calleMapper.toDto(CalleModel));
            }
            return new ResponseEntity<>(CalleDTOs, Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long quantity= calleService.contarTodas();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long quantity= calleService.contarTodasConEliminadas();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> insertar(@Valid @RequestBody CalleCreation calleCreation) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.insertar(calleCreation);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> actualizar(@Valid @RequestBody CalleModel calleModel) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.actualizar(calleModel);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<CalleDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.eliminar(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<CalleDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.reciclar(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.destruir(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(CalleModelEntityMessenger.getMessage(), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }
}
