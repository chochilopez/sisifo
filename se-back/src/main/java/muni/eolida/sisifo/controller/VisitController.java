package muni.eolida.sisifo.controller;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.VisitMapper;
import muni.eolida.sisifo.mapper.creation.VisitCreation;
import muni.eolida.sisifo.mapper.dto.VisitDataTransferObject;
import muni.eolida.sisifo.model.VisitModel;
import muni.eolida.sisifo.service.implementation.VisitServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<VisitDataTransferObject>> findAllByIp(@PathVariable(name = "ip") @jakarta.validation.constraints.Size(min = 1, max = 18) String ip) {
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<VisitDataTransferObject>> findTopN(@PathVariable(name = "number") @jakarta.validation.constraints.Size(min = 1, max = 4) Integer number) {
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<VisitDataTransferObject> findById(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findById(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @GetMapping(value = "/count-all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> countAll() {
        Long quantity= visitServiceImplementation.countAll();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<VisitDataTransferObject> update(@Valid @RequestBody VisitCreation visitCreation) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.update(visitMapper.toEntity(visitCreation));
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destroy/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitModel> visitModelEntityMessenger = visitServiceImplementation.findById(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitModelEntityMessenger.getMessage(), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }
}
