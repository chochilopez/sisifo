package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.ReclamoDTO;
import muni.eolida.sisifo.mapper.dto.ReclamoDTO;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.service.implementation.ReclamoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/reclamo")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ReclamoController {

    private final ReclamoServiceImpl reclamoService;
    private final ReclamoMapper reclamoMapper;
    
    @GetMapping(value = "/buscar-mis-reclamos")
    @PreAuthorize("hasAuthority('ROL_USUARIO')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodosPorReclamo() {
        EntityMessenger<ReclamoModel> reclamoModelEntityMessenger = reclamoService.buscarMisReclamos();
        if (reclamoModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(reclamoModelEntityMessenger.getMessage())).build();
        } else if (reclamoModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : reclamoModelEntityMessenger.getList()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(reclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(reclamoModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-reclamos-por-usuario/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodosPorReclamo(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 5) Long id) {
        EntityMessenger<ReclamoModel> reclamoModelEntityMessenger = reclamoService.buscarPorCreador(id);
        if (reclamoModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(reclamoModelEntityMessenger.getMessage())).build();
        } else if (reclamoModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : reclamoModelEntityMessenger.getList()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(reclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(reclamoModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-reclamos-por-usuario-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<ReclamoDTO>> buscarTodosPorReclamoConBorrados(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 5) Long id) {
        EntityMessenger<ReclamoModel> reclamoModelEntityMessenger = reclamoService.buscarPorCreadorConBorrados(id);
        if (reclamoModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(reclamoModelEntityMessenger.getMessage())).build();
        } else if (reclamoModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<ReclamoDTO> reclamoDTOS = new ArrayList<>();
            for (ReclamoModel reclamoModel : reclamoModelEntityMessenger.getList()) {
                reclamoDTOS.add(reclamoMapper.toDto(reclamoModel));
            }
            return new ResponseEntity<>(reclamoDTOS, Helper.httpHeaders(reclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(reclamoModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ReclamoDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.buscarPorId(id);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(ReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ReclamoDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.buscarPorIdConBorrados(id);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(ReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<ReclamoDTO>> findAllByRemovedIsNull() {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.buscarTodos();
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<ReclamoDTO> ReclamoDTOs = new ArrayList<>();
            for (ReclamoModel ReclamoModel:ReclamoModelEntityMessenger.getList()) {
                ReclamoDTOs.add(reclamoMapper.toDto(ReclamoModel));
            }
            return new ResponseEntity<>(ReclamoDTOs, Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<ReclamoDTO>> findAll() {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.buscarTodosConBorrados();
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<ReclamoDTO> ReclamoDTOs = new ArrayList<>();
            for (ReclamoModel ReclamoModel:ReclamoModelEntityMessenger.getList()) {
                ReclamoDTOs.add(reclamoMapper.toDto(ReclamoModel));
            }
            return new ResponseEntity<>(ReclamoDTOs, Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= reclamoService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= reclamoService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROL_USUARIO')")
    public ResponseEntity<ReclamoDTO> insert(@Valid @RequestBody ReclamoCreation reclamoCreation) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.insertar(reclamoCreation);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(ReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ReclamoDTO> update(@Valid @RequestBody ReclamoModel reclamoModel) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.actualizar(reclamoModel);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(ReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ReclamoDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.borrar(id);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(reclamoMapper.toDto(ReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ReclamoDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.reciclar(id);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(reclamoMapper.toDto(ReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ReclamoModel> ReclamoModelEntityMessenger = reclamoService.destruir(id);
        if (ReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
        else if (ReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(ReclamoModelEntityMessenger.getMessage(), Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ReclamoModelEntityMessenger.getMessage())).build();
    }
}
