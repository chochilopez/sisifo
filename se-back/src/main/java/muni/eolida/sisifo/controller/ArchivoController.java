package muni.eolida.sisifo.controller;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDataTransferObject;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.service.implementation.ArchivoServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/archivo")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ArchivoController {

    private final ArchivoServiceImplementation archivoServiceImplementation;
    private final ArchivoMapper archivoMapper;

    @PutMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ArchivoDataTransferObject> guardarArchivo(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("filetype") String filetype,
            @RequestParam("filesize") String filesize,
            @RequestParam("description") String description
    ) {
        try{
            EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = ArchivoServiceImplementation.guardarArchivo(multipartFile.getBytes(), multipartFile.getOriginalFilename(), filetype, description, filesize);
            if (ArchivoModelEntityMessenger.getStatusCode() == 202)
                return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
            else if (ArchivoModelEntityMessenger.getStatusCode() == 201)
                return new ResponseEntity<>(ArchivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.CREATED);
            else
                return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        } catch (Exception e){
            return ResponseEntity.noContent().headers(Helper.httpHeaders(e.getMessage())).build();
        }
    }

    @GetMapping(value = "/find-all-by-filetype/{type}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<ArchivoDataTransferObject>> buscarTodosPorTipoArchivo(@PathVariable(name = "type") @jakarta.validation.constraints.Size(min = 1, max = 50) String type) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = ArchivoServiceImplementation.buscarTodosPorTipoArchivo(type);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<ArchivoDataTransferObject> ArchivoDataTransferObjects = new ArrayList<>();
            for (ArchivoModel archivoModel :ArchivoModelEntityMessenger.getList()) {
                ArchivoDataTransferObjects.add(ArchivoMapper.toDto(archivoModel));
            }
            return new ResponseEntity<>(ArchivoDataTransferObjects, Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ArchivoDataTransferObject> findById(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = ArchivoServiceImplementation.findById(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(ArchivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<ArchivoDataTransferObject>> findAll() {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = ArchivoServiceImplementation.findAll();
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200){
            ArrayList<ArchivoDataTransferObject> ArchivoDataTransferObjects = new ArrayList<>();
            for (ArchivoModel archivoModel :ArchivoModelEntityMessenger.getList()) {
                ArchivoDataTransferObjects.add(ArchivoMapper.toDto(archivoModel));
            }
            return new ResponseEntity<>(ArchivoDataTransferObjects, Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/count-all")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= ArchivoServiceImplementation.countAll();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/count-all-by-filetype/{filetype}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> contarTodosPorTipoArchivo(@PathVariable(name = "filetype") @jakarta.validation.constraints.Size(min = 1, max = 50) String filetype) {
        Long quantity= ArchivoServiceImplementation.contarTodosPorTipoArchivo(filetype);
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<ArchivoDataTransferObject> actualizar(@Valid @RequestBody ArchivoCreation ArchivoCreation) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = ArchivoServiceImplementation.actualizar(ArchivoMapper.toEntity(ArchivoCreation));
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(ArchivoMapper.toDto(ArchivoModelEntityMessenger.getObject()), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destroy/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = ArchivoServiceImplementation.destroy(id);
        if (ArchivoModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
        else if (ArchivoModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(ArchivoModelEntityMessenger.getMessage(), Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(ArchivoModelEntityMessenger.getMessage())).build();
    }
}
