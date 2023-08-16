package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDTO;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.service.implementation.ArchivoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/archivo")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ArchivoController {

    private final ArchivoServiceImpl archivoService;
    private final ArchivoMapper archivoMapper;

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ArchivoDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.buscarPorId(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(archivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ArchivoDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.buscarPorIdConBorrados(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(archivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<ArchivoDTO>> findAllByRemovedIsNull() {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.buscarTodos();
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<ArchivoDTO> ArchivoDTOs = new ArrayList<>();
            for (ArchivoModel ArchivoModel:ArchivoModelEntityMessenger.getList()) {
                ArchivoDTOs.add(archivoMapper.toDto(ArchivoModel));
            }
            return new ResponseEntity<>(ArchivoDTOs, Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<ArchivoDTO>> findAll() {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.buscarTodosConBorrados();
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<ArchivoDTO> ArchivoDTOs = new ArrayList<>();
            for (ArchivoModel ArchivoModel:ArchivoModelEntityMessenger.getList()) {
                ArchivoDTOs.add(archivoMapper.toDto(ArchivoModel));
            }
            return new ResponseEntity<>(ArchivoDTOs, Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= archivoService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= archivoService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ArchivoDTO> insert(@Valid @RequestBody ArchivoCreation archivoCreation) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.insertar(archivoCreation);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(archivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ArchivoDTO> update(@Valid @RequestBody ArchivoModel archivoModel) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.actualizar(archivoModel);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(archivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ArchivoDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.borrar(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(archivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ArchivoDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.reciclar(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(archivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = archivoService.destruir(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(ArchivoModelEntityMessenger.getMessage(), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }
}
