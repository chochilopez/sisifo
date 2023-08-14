package com.gloit.epione.controller;

import com.gloit.epione.helper.EntityMessenger;
import com.gloit.epione.helper.Helper;
import com.gloit.epione.mapper.VisitMapper;
import com.gloit.epione.mapper.creation.VisitCreation;
import com.gloit.epione.mapper.dto.VisitDataTransferObject;
import com.gloit.epione.model.VisitModel;
import com.gloit.epione.service.implementation.VisitServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/visit")
@RequiredArgsConstructor
@RestController
@Slf4j
public class VisitController {

    private final VisitServiceImplementation visitServiceImplementation;
    private final VisitMapper visitMapper;

    @GetMapping(value = "/find-all-by-ip/{ip}")
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<List<VisitDataTransferObject>> findAllByIpAndRemovedIsNull(@PathVariable(name = "ip") @javax.validation.constraints.Size(min = 1, max = 18) String ip) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findAllByIpAndRemovedIsNull(ip);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitDataTransferObject> visitDataTransferObjects = new ArrayList<>();
            for (VisitModel visitModel:visitModelEntityMessenger.getList()) {
                visitDataTransferObjects.add(visitMapper.toDto(visitModel));
            }
            return new ResponseEntity<>(visitDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all-by-ip-with-deleted/{ip}")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<List<VisitDataTransferObject>> findAllByIp(@PathVariable(name = "ip") @javax.validation.constraints.Size(min = 1, max = 18) String ip) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findAllByIp(ip);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitDataTransferObject> visitDataTransferObjects = new ArrayList<>();
            for (VisitModel visitModel:visitModelEntityMessenger.getList()) {
                visitDataTransferObjects.add(visitMapper.toDto(visitModel));
            }
            return new ResponseEntity<>(visitDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-top-n/{number}")
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<List<VisitDataTransferObject>> findTopNAndRemovedIsNull(@PathVariable(name = "number") @javax.validation.constraints.Size(min = 1, max = 4) Integer number) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findTopNAndRemovedIsNull(number);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitDataTransferObject> visitDataTransferObjects = new ArrayList<>();
            for (VisitModel visitModel:visitModelEntityMessenger.getList()) {
                visitDataTransferObjects.add(visitMapper.toDto(visitModel));
            }
            return new ResponseEntity<>(visitDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-top-n-with-deleted/{number}")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<List<VisitDataTransferObject>> findTopN(@PathVariable(name = "number") @javax.validation.constraints.Size(min = 1, max = 4) Integer number) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findTopN(number);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitDataTransferObject> visitDataTransferObjects = new ArrayList<>();
            for (VisitModel visitModel:visitModelEntityMessenger.getList()) {
                visitDataTransferObjects.add(visitMapper.toDto(visitModel));
            }
            return new ResponseEntity<>(visitDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<VisitDataTransferObject> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findByIdAndRemovedIsNull(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-by-id-with-deleted/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<VisitDataTransferObject> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findById(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<List<VisitDataTransferObject>> findAllByRemovedIsNull() {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findAllByRemovedIsNull();
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitDataTransferObject> visitDataTransferObjects = new ArrayList<>();
            for (VisitModel visitModel:visitModelEntityMessenger.getList()) {
                visitDataTransferObjects.add(visitMapper.toDto(visitModel));
            }
            return new ResponseEntity<>(visitDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all-with-deleted")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<List<VisitDataTransferObject>> findAll() {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findAll();
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitDataTransferObject> visitDataTransferObjects = new ArrayList<>();
            for (VisitModel visitModel:visitModelEntityMessenger.getList()) {
                visitDataTransferObjects.add(visitMapper.toDto(visitModel));
            }
            return new ResponseEntity<>(visitDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/count-all-with-deleted")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<Long> countAll() {
        Long quantity= visitServiceImplementation.countAll();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/count-all")
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= visitServiceImplementation.countAllByRemovedIsNull();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('AUTHORITY_GUEST')")
    public ResponseEntity<VisitDataTransferObject> insert(@Valid @RequestBody VisitCreation visitCreation) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.insert(visitMapper.toEntity(visitCreation));
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<VisitDataTransferObject> update(@Valid @RequestBody VisitCreation visitCreation) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.update(visitMapper.toEntity(visitCreation));
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_EDITOR')")
    public ResponseEntity<VisitDataTransferObject> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.delete(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/recycle/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<VisitDataTransferObject> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.recycle(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destroy/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_ADMIN')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findById(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitModelEntityMessenger.getMessage(), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }
}
