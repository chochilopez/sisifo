package muni.eolida.sisifo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.VisitaMapper;
import muni.eolida.sisifo.mapper.creation.VisitaCreation;
import muni.eolida.sisifo.mapper.dto.VisitaDataTransferObject;
import muni.eolida.sisifo.model.VisitaModel;
import muni.eolida.sisifo.service.implementation.VisitaServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/visita")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UsuarioController {

    private final VisitaServiceImplementation visitaServiceImplementation;
    private final VisitaMapper visitaMapper;

    @GetMapping(value = "/find-all-by-ip/{ip}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDataTransferObject>> buscarTodosPorIp(@PathVariable(name = "ip") @jakarta.validation.constraints.Size(min = 1, max = 18) String ip) {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.buscarTodosPorIp(ip);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDataTransferObject> visitaDataTransferObjects = new ArrayList<>();
            for (VisitaModel visitaModel :visitModelEntityMessenger.getList()) {
                visitaDataTransferObjects.add(visitaMapper.toDto(visitaModel));
            }
            return new ResponseEntity<>(visitaDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-top-n/{number}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDataTransferObject>> findTopN(@PathVariable(name = "number") @jakarta.validation.constraints.Size(min = 1, max = 4) Integer number) {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.findTopN(number);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDataTransferObject> visitaDataTransferObjects = new ArrayList<>();
            for (VisitaModel visitaModel :visitModelEntityMessenger.getList()) {
                visitaDataTransferObjects.add(visitaMapper.toDto(visitaModel));
            }
            return new ResponseEntity<>(visitaDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDataTransferObject> findById(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.findById(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitaMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDataTransferObject>> findAll() {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.findAll();
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDataTransferObject> visitaDataTransferObjects = new ArrayList<>();
            for (VisitaModel visitaModel :visitModelEntityMessenger.getList()) {
                visitaDataTransferObjects.add(visitaMapper.toDto(visitaModel));
            }
            return new ResponseEntity<>(visitaDataTransferObjects, Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/count-all")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= visitaServiceImplementation.countAll();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROL_USUARIO')")
    public ResponseEntity<VisitaDataTransferObject> insertar(@Valid @RequestBody VisitaCreation visitaCreation) {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.insertar(visitaMapper.toEntity(visitaCreation));
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitaMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDataTransferObject> actualizar(@Valid @RequestBody VisitaCreation visitaCreation) {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.actualizar(visitaMapper.toEntity(visitaCreation));
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitaMapper.toDto(visitModelEntityMessenger.getObject()), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destroy/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @jakarta.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> visitModelEntityMessenger = visitaServiceImplementation.findById(id);
        if (visitModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
        else if (visitModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitModelEntityMessenger.getMessage(), Helper.httpHeaders(visitModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(visitModelEntityMessenger.getMessage())).build();
    }
}
