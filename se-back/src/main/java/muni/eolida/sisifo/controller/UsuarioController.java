package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/usuario")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.buscarPorId(id);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(usuarioMapper.toDto(UsuarioModelEntityMessenger.getObject()), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.buscarPorIdConBorrados(id);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(usuarioMapper.toDto(UsuarioModelEntityMessenger.getObject()), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> findAllByRemovedIsNull() {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.buscarTodos();
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 200){
            ArrayList<UsuarioDTO> UsuarioDTOs = new ArrayList<>();
            for (UsuarioModel UsuarioModel:UsuarioModelEntityMessenger.getList()) {
                UsuarioDTOs.add(usuarioMapper.toDto(UsuarioModel));
            }
            return new ResponseEntity<>(UsuarioDTOs, Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.buscarTodosConBorrados();
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 200){
            ArrayList<UsuarioDTO> UsuarioDTOs = new ArrayList<>();
            for (UsuarioModel UsuarioModel:UsuarioModelEntityMessenger.getList()) {
                UsuarioDTOs.add(usuarioMapper.toDto(UsuarioModel));
            }
            return new ResponseEntity<>(UsuarioDTOs, Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= usuarioService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= usuarioService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> insert(@Valid @RequestBody UsuarioCreation usuarioCreation) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.insertar(usuarioCreation);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(usuarioMapper.toDto(UsuarioModelEntityMessenger.getObject()), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> update(@Valid @RequestBody UsuarioModel usuarioModel) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.actualizar(usuarioModel);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(usuarioMapper.toDto(UsuarioModelEntityMessenger.getObject()), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.borrar(id);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(usuarioMapper.toDto(UsuarioModelEntityMessenger.getObject()), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.reciclar(id);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(usuarioMapper.toDto(UsuarioModelEntityMessenger.getObject()), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> UsuarioModelEntityMessenger = usuarioService.destruir(id);
        if (UsuarioModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
        else if (UsuarioModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(UsuarioModelEntityMessenger.getMessage(), Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(UsuarioModelEntityMessenger.getMessage())).build();
    }
}
