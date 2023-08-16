package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.TipoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.service.implementation.TipoReclamoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/tipo-reclamo")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TipoReclamoController {

    private final TipoReclamoServiceImpl tipoReclamoService;
    private final TipoReclamoMapper tipoReclamoMapper;

    @GetMapping(value = "/buscar-por-nombre/{nombre}")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodosPorNombre(@PathVariable(name = "nombre") @javax.validation.constraints.Size(min = 3, max = 40) String nombre) {
        EntityMessenger<TipoReclamoModel> tipoReclamoModelEntityMessenger = tipoReclamoService.buscarTodosPorNombre(nombre);
        if (tipoReclamoModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(tipoReclamoModelEntityMessenger.getMessage())).build();
        } else if (tipoReclamoModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<TipoReclamoDTO> list = new ArrayList<>();
            for (TipoReclamoModel tipoReclamoModel : tipoReclamoModelEntityMessenger.getList()) {
                list.add(tipoReclamoMapper.toDto(tipoReclamoModel));
            }
            return new ResponseEntity<>(list, Helper.httpHeaders(tipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(tipoReclamoModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-nombre-con-borrados/{nombre}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<TipoReclamoDTO>> buscarTodosPorNombreConBorrados(@PathVariable(name = "nombre") @javax.validation.constraints.Size(min = 3, max = 40) String nombre) {
        EntityMessenger<TipoReclamoModel> tipoReclamoModelEntityMessenger = tipoReclamoService.buscarTodosPorNombreConBorrados(nombre);
        if (tipoReclamoModelEntityMessenger.getStatusCode() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(tipoReclamoModelEntityMessenger.getMessage())).build();
        } else if (tipoReclamoModelEntityMessenger.getStatusCode() == 200) {
            ArrayList<TipoReclamoDTO> list = new ArrayList<>();
            for (TipoReclamoModel tipoReclamoModel : tipoReclamoModelEntityMessenger.getList()) {
                list.add(tipoReclamoMapper.toDto(tipoReclamoModel));
            }
            return new ResponseEntity<>(list, Helper.httpHeaders(tipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(tipoReclamoModelEntityMessenger.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<TipoReclamoDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.buscarPorId(id);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(TipoReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<TipoReclamoDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.buscarPorIdConBorrados(id);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(TipoReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<TipoReclamoDTO>> findAllByRemovedIsNull() {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.buscarTodos();
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<TipoReclamoDTO> TipoReclamoDTOs = new ArrayList<>();
            for (TipoReclamoModel TipoReclamoModel:TipoReclamoModelEntityMessenger.getList()) {
                TipoReclamoDTOs.add(tipoReclamoMapper.toDto(TipoReclamoModel));
            }
            return new ResponseEntity<>(TipoReclamoDTOs, Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<TipoReclamoDTO>> findAll() {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.buscarTodosConBorrados();
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<TipoReclamoDTO> TipoReclamoDTOs = new ArrayList<>();
            for (TipoReclamoModel TipoReclamoModel:TipoReclamoModelEntityMessenger.getList()) {
                TipoReclamoDTOs.add(tipoReclamoMapper.toDto(TipoReclamoModel));
            }
            return new ResponseEntity<>(TipoReclamoDTOs, Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= tipoReclamoService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= tipoReclamoService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<TipoReclamoDTO> insert(@Valid @RequestBody TipoReclamoCreation tipoReclamoCreation) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.insertar(tipoReclamoCreation);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(TipoReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<TipoReclamoDTO> update(@Valid @RequestBody TipoReclamoModel tipoReclamoModel) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.actualizar(tipoReclamoModel);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(TipoReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<TipoReclamoDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.borrar(id);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(TipoReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<TipoReclamoDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.reciclar(id);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(tipoReclamoMapper.toDto(TipoReclamoModelEntityMessenger.getObject()), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<TipoReclamoModel> TipoReclamoModelEntityMessenger = tipoReclamoService.destruir(id);
        if (TipoReclamoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
        else if (TipoReclamoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(TipoReclamoModelEntityMessenger.getMessage(), Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(TipoReclamoModelEntityMessenger.getMessage())).build();
    }
}
