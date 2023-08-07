package muni.eolida.sisifo.controller;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.LocalFileMapper;
import muni.eolida.sisifo.mapper.creation.LocalFileCreation;
import muni.eolida.sisifo.mapper.dto.LocalFileDataTransferObject;
import muni.eolida.sisifo.model.LocalFileModel;
import muni.eolida.sisifo.service.implementation.LocalFileServiceImplementation;
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
@RequestMapping(value = "/api/localfile")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LocalFileController {

    private final LocalFileServiceImplementation localFileServiceImplementation;
    private final LocalFileMapper localFileMapper;

    @PutMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<LocalFileDataTransferObject> saveLocalFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("filetype") String filetype,
            @RequestParam("filesize") String filesize,
            @RequestParam("description") String description
    ) {
        try{
            EntityMessenger<LocalFileModel> localFileModelEntityMessenger = localFileServiceImplementation.saveLocalFile(multipartFile.getBytes(), multipartFile.getOriginalFilename(), filetype, description, filesize);
            if (localFileModelEntityMessenger.getStatusCode() == 202)
                return ResponseEntity.accepted().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
            else if (localFileModelEntityMessenger.getStatusCode() == 201)
                return new ResponseEntity<>(localFileMapper.toDto(localFileModelEntityMessenger.getObject()), Helper.httpHeaders(localFileModelEntityMessenger.getMessage()), HttpStatus.CREATED);
            else
                return ResponseEntity.noContent().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
        } catch (Exception e){
            return ResponseEntity.noContent().headers(Helper.httpHeaders(e.getMessage())).build();
        }
    }

    @GetMapping(value = "/find-all-by-filetype/{type}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<LocalFileDataTransferObject>> findAllByFiletype(@PathVariable(name = "type") @jakarta.validation.constraints.Size(min = 1, max = 50) String type) {
        EntityMessenger<LocalFileModel> localFileModelEntityMessenger = localFileServiceImplementation.findAllByFiletype(type);
        if (localFileModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
        else if (localFileModelEntityMessenger.getStatusCode() == 200){
            ArrayList<LocalFileDataTransferObject> localFileDataTransferObjects = new ArrayList<>();
            for (LocalFileModel localFileModel:localFileModelEntityMessenger.getList()) {
                localFileDataTransferObjects.add(localFileMapper.toDto(localFileModel));
            }
            return new ResponseEntity<>(localFileDataTransferObjects, Helper.httpHeaders(localFileModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<LocalFileDataTransferObject> findById(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<LocalFileModel> localFileModelEntityMessenger = localFileServiceImplementation.findById(id);
        if (localFileModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
        else if (localFileModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(localFileMapper.toDto(localFileModelEntityMessenger.getObject()), Helper.httpHeaders(localFileModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<LocalFileDataTransferObject>> findAll() {
        EntityMessenger<LocalFileModel> localFileModelEntityMessenger = localFileServiceImplementation.findAll();
        if (localFileModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
        else if (localFileModelEntityMessenger.getStatusCode() == 200){
            ArrayList<LocalFileDataTransferObject> localFileDataTransferObjects = new ArrayList<>();
            for (LocalFileModel localFileModel:localFileModelEntityMessenger.getList()) {
                localFileDataTransferObjects.add(localFileMapper.toDto(localFileModel));
            }
            return new ResponseEntity<>(localFileDataTransferObjects, Helper.httpHeaders(localFileModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/count-all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> countAll() {
        Long quantity= localFileServiceImplementation.countAll();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/count-all-by-filetype/{filetype}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> countAllByFiletype(@PathVariable(name = "filetype") @jakarta.validation.constraints.Size(min = 1, max = 50) String filetype) {
        Long quantity= localFileServiceImplementation.countAllByFiletype(filetype);
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<LocalFileDataTransferObject> update(@Valid @RequestBody LocalFileCreation localFileCreation) {
        EntityMessenger<LocalFileModel> localFileModelEntityMessenger = localFileServiceImplementation.update(localFileMapper.toEntity(localFileCreation));
        if (localFileModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
        else if (localFileModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(localFileMapper.toDto(localFileModelEntityMessenger.getObject()), Helper.httpHeaders(localFileModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destroy/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<LocalFileModel> localFileModelEntityMessenger = localFileServiceImplementation.destroy(id);
        if (localFileModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
        else if (localFileModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(localFileModelEntityMessenger.getMessage(), Helper.httpHeaders(localFileModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(localFileModelEntityMessenger.getMessage())).build();
    }
}
