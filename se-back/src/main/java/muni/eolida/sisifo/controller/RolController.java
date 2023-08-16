package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.RolMapper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.dto.RolDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.service.implementation.RolServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/rol")
@RequiredArgsConstructor
@RestController
@Slf4j
public class RolController {

    private final RolServiceImpl rolService;
    private final RolMapper rolMapper;

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RolDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.buscarPorId(id);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(rolMapper.toDto(RolModelEntityMessenger.getObject()), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RolDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.buscarPorIdConBorrados(id);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(rolMapper.toDto(RolModelEntityMessenger.getObject()), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<RolDTO>> findAllByRemovedIsNull() {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.buscarTodos();
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 200){
            ArrayList<RolDTO> RolDTOs = new ArrayList<>();
            for (RolModel RolModel:RolModelEntityMessenger.getList()) {
                RolDTOs.add(rolMapper.toDto(RolModel));
            }
            return new ResponseEntity<>(RolDTOs, Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<RolDTO>> findAll() {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.buscarTodosConBorrados();
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 200){
            ArrayList<RolDTO> RolDTOs = new ArrayList<>();
            for (RolModel RolModel:RolModelEntityMessenger.getList()) {
                RolDTOs.add(rolMapper.toDto(RolModel));
            }
            return new ResponseEntity<>(RolDTOs, Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= rolService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= rolService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RolDTO> insert(@Valid @RequestBody RolCreation rolCreation) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.insertar(rolCreation);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(rolMapper.toDto(RolModelEntityMessenger.getObject()), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RolDTO> update(@Valid @RequestBody RolModel rolModel) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.actualizar(rolModel);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(rolMapper.toDto(RolModelEntityMessenger.getObject()), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RolDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.borrar(id);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(rolMapper.toDto(RolModelEntityMessenger.getObject()), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RolDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.reciclar(id);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(rolMapper.toDto(RolModelEntityMessenger.getObject()), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<RolModel> RolModelEntityMessenger = rolService.destruir(id);
        if (RolModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
        else if (RolModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(RolModelEntityMessenger.getMessage(), Helper.httpHeaders(RolModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(RolModelEntityMessenger.getMessage())).build();
    }
}
