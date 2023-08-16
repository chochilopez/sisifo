package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.CalleMapper;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.mapper.dto.CalleDTO;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.service.implementation.CalleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/calle")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CalleController {

    private final CalleServiceImpl calleService;
    private final CalleMapper calleMapper;

    @GetMapping(value = "/buscar-por-nombre/{calle}")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<CalleDTO>> buscarTodosPorCalle(@PathVariable(name = "calle") @javax.validation.constraints.Size(min = 3, max = 40) String calle) {
        EntityMessenger<CalleModel> calleModelEntityMessenger = calleService.buscarTodosPorCalle(calle);
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

    @GetMapping(value = "/buscar-por-nombre-con-borrados/{calle}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<CalleDTO>> buscarTodosPorCalleConBorrados(@PathVariable(name = "calle") @javax.validation.constraints.Size(min = 3, max = 40) String calle) {
        EntityMessenger<CalleModel> calleModelEntityMessenger = calleService.buscarTodosPorCalleConBorrados(calle);
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
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<CalleDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarPorId(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CalleDTO> findByIdConBorrados(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarPorIdConBorrados(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<CalleDTO>> findAllByRemovedIsNull() {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarTodos();
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

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<CalleDTO>> findAll() {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.buscarTodosConBorrados();
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

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= calleService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= calleService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CalleDTO> insert(@Valid @RequestBody CalleCreation calleCreation) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.insertar(calleCreation);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CalleDTO> update(@Valid @RequestBody CalleModel calleModel) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.actualizar(calleModel);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CalleDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.borrar(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CalleDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.reciclar(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(calleMapper.toDto(CalleModelEntityMessenger.getObject()), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<CalleModel> CalleModelEntityMessenger = calleService.destruir(id);
        if (CalleModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
        else if (CalleModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(CalleModelEntityMessenger.getMessage(), Helper.httpHeaders(CalleModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(CalleModelEntityMessenger.getMessage())).build();
    }
}
