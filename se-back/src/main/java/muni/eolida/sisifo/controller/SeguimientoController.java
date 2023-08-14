package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.SeguimientoMapper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.service.implementation.SeguimientoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/seguimiento")
@RequiredArgsConstructor
@RestController
@Slf4j
public class SeguimientoController {

    private final SeguimientoServiceImpl seguimientoService;
    private final SeguimientoMapper seguimientoMapper;

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<SeguimientoDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.buscarPorId(id);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(seguimientoMapper.toDto(SeguimientoModelEntityMessenger.getObject()), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<SeguimientoDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.buscarPorIdConBorrados(id);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(seguimientoMapper.toDto(SeguimientoModelEntityMessenger.getObject()), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<SeguimientoDTO>> findAllByRemovedIsNull() {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.buscarTodos();
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<SeguimientoDTO> SeguimientoDTOs = new ArrayList<>();
            for (SeguimientoModel SeguimientoModel:SeguimientoModelEntityMessenger.getList()) {
                SeguimientoDTOs.add(seguimientoMapper.toDto(SeguimientoModel));
            }
            return new ResponseEntity<>(SeguimientoDTOs, Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<SeguimientoDTO>> findAll() {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.buscarTodosConBorrados();
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<SeguimientoDTO> SeguimientoDTOs = new ArrayList<>();
            for (SeguimientoModel SeguimientoModel:SeguimientoModelEntityMessenger.getList()) {
                SeguimientoDTOs.add(seguimientoMapper.toDto(SeguimientoModel));
            }
            return new ResponseEntity<>(SeguimientoDTOs, Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= seguimientoService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= seguimientoService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<SeguimientoDTO> insert(@Valid @RequestBody SeguimientoCreation seguimientoCreation) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.insertar(seguimientoCreation);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(seguimientoMapper.toDto(SeguimientoModelEntityMessenger.getObject()), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<SeguimientoDTO> update(@Valid @RequestBody SeguimientoModel seguimientoModel) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.actualizar(seguimientoModel);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(seguimientoMapper.toDto(SeguimientoModelEntityMessenger.getObject()), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<SeguimientoDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.borrar(id);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(seguimientoMapper.toDto(SeguimientoModelEntityMessenger.getObject()), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<SeguimientoDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.reciclar(id);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(seguimientoMapper.toDto(SeguimientoModelEntityMessenger.getObject()), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<SeguimientoModel> SeguimientoModelEntityMessenger = seguimientoService.destruir(id);
        if (SeguimientoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
        else if (SeguimientoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(SeguimientoModelEntityMessenger.getMessage(), Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(SeguimientoModelEntityMessenger.getMessage())).build();
    }
}
