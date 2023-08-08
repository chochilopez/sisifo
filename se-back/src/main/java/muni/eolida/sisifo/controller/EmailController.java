package muni.eolida.sisifo.controller;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.EmailMapper;
import muni.eolida.sisifo.mapper.creation.EmailCreation;
import muni.eolida.sisifo.mapper.dto.EmailDataTransferObject;
import muni.eolida.sisifo.model.EmailModel;
import muni.eolida.sisifo.service.implementation.EmailServiceImplementation;
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
@RequestMapping(value = "/api/email")
@RequiredArgsConstructor
@RestController
@Slf4j
public class EmailController {

    private final EmailServiceImplementation emailServiceImplementation;
    private final EmailMapper emailMapper;

    @PostMapping(value = "/simple")
    @PreAuthorize("hasAuthority('ROL_USUARIO')")
    public ResponseEntity<EmailDataTransferObject> simple(@Valid @RequestBody EmailCreation emailCreation) {
        EntityMessenger<EmailModel> emailModelEntityMessenger = emailServiceImplementation.enviarEmailSimple(emailMapper.toEntity(emailCreation));
        if (emailModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
        else if (emailModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(emailMapper.toDto(emailModelEntityMessenger.getObject()), Helper.httpHeaders(emailModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<EmailDataTransferObject> findById(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<EmailModel> emailModelEntityMessenger = emailServiceImplementation.findById(id);
        if (emailModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
        else if (emailModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(emailMapper.toDto(emailModelEntityMessenger.getObject()), Helper.httpHeaders(emailModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<EmailDataTransferObject>> findAll() {
        EntityMessenger<EmailModel> emailModelEntityMessenger = emailServiceImplementation.findAll();
        if (emailModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
        else if (emailModelEntityMessenger.getStatusCode() == 200){
            ArrayList<EmailDataTransferObject> emailDataTransferObjects = new ArrayList<>();
            for (EmailModel emailModel:emailModelEntityMessenger.getList()) {
                emailDataTransferObjects.add(emailMapper.toDto(emailModel));
            }
            return new ResponseEntity<>(emailDataTransferObjects, Helper.httpHeaders(emailModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<EmailDataTransferObject> insertar(@Valid @RequestBody EmailCreation emailCreation) {
        EntityMessenger<EmailModel> emailModelEntityMessenger = emailServiceImplementation.insertar(emailMapper.toEntity(emailCreation));
        if (emailModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
        else if (emailModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(emailMapper.toDto(emailModelEntityMessenger.getObject()), Helper.httpHeaders(emailModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<EmailDataTransferObject> actualizar(@Valid @RequestBody EmailCreation emailCreation) {
        EntityMessenger<EmailModel> emailModelEntityMessenger = emailServiceImplementation.actualizar(emailMapper.toEntity(emailCreation));
        if (emailModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
        else if (emailModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(emailMapper.toDto(emailModelEntityMessenger.getObject()), Helper.httpHeaders(emailModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destroy/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<EmailModel> emailModelEntityMessenger = emailServiceImplementation.destroy(id);
        if (emailModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
        else if (emailModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(emailModelEntityMessenger.getMessage(), Helper.httpHeaders(emailModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(emailModelEntityMessenger.getMessage())).build();
    }
}
