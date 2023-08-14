package muni.eolida.sisifo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.VisitaMapper;
import muni.eolida.sisifo.mapper.creation.VisitaCreation;
import muni.eolida.sisifo.mapper.dto.VisitaDTO;
import muni.eolida.sisifo.model.VisitaModel;
import muni.eolida.sisifo.service.implementation.VisitaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/visita")
@RequiredArgsConstructor
@RestController
@Slf4j
public class RolController {

    private final VisitaServiceImpl visitaService;
    private final VisitaMapper visitaMapper;

    @GetMapping(value = "/buscar-por-ip/{ip}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDTO>> findAllByIpAndRemovedIsNull(@PathVariable(name = "ip") @javax.validation.constraints.Size(min = 1, max = 18) String ip) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarTodosPorIp(ip);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDTO> VisitaDTOs = new ArrayList<>();
            for (VisitaModel VisitaModel:VisitaModelEntityMessenger.getList()) {
                VisitaDTOs.add(visitaMapper.toDto(VisitaModel));
            }
            return new ResponseEntity<>(VisitaDTOs, Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-ip-con-borrados/{ip}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDTO>> findAllByIp(@PathVariable(name = "ip") @javax.validation.constraints.Size(min = 1, max = 18) String ip) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarTodosPorIpConBorrados(ip);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDTO> VisitaDTOs = new ArrayList<>();
            for (VisitaModel VisitaModel:VisitaModelEntityMessenger.getList()) {
                VisitaDTOs.add(visitaMapper.toDto(VisitaModel));
            }
            return new ResponseEntity<>(VisitaDTOs, Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-los-primeros-n/{cantidad}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDTO>> findTopNAndRemovedIsNull(@PathVariable(name = "cantidad") @javax.validation.constraints.Size(min = 1, max = 10) Integer cantidad) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarLosPrimerosN(cantidad);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDTO> VisitaDTOs = new ArrayList<>();
            for (VisitaModel VisitaModel:VisitaModelEntityMessenger.getList()) {
                VisitaDTOs.add(visitaMapper.toDto(VisitaModel));
            }
            return new ResponseEntity<>(VisitaDTOs, Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-los-primeros-n-con-borrados/{cantidad}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDTO>> findTopN(@PathVariable(name = "cantidad") @javax.validation.constraints.Size(min = 1, max = 10) Integer cantidad) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarLosPrimerosNConBorrados(cantidad);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDTO> VisitaDTOs = new ArrayList<>();
            for (VisitaModel VisitaModel:VisitaModelEntityMessenger.getList()) {
                VisitaDTOs.add(visitaMapper.toDto(VisitaModel));
            }
            return new ResponseEntity<>(VisitaDTOs, Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDTO> findByIdAndRemovedIsNull(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarPorId(id);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitaMapper.toDto(VisitaModelEntityMessenger.getObject()), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-borrados/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDTO> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarPorIdConBorrados(id);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitaMapper.toDto(VisitaModelEntityMessenger.getObject()), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDTO>> findAllByRemovedIsNull() {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarTodos();
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDTO> VisitaDTOs = new ArrayList<>();
            for (VisitaModel VisitaModel:VisitaModelEntityMessenger.getList()) {
                VisitaDTOs.add(visitaMapper.toDto(VisitaModel));
            }
            return new ResponseEntity<>(VisitaDTOs, Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/buscar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<List<VisitaDTO>> findAll() {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.buscarTodosConBorrados();
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200){
            ArrayList<VisitaDTO> VisitaDTOs = new ArrayList<>();
            for (VisitaModel VisitaModel:VisitaModelEntityMessenger.getList()) {
                VisitaDTOs.add(visitaMapper.toDto(VisitaModel));
            }
            return new ResponseEntity<>(VisitaDTOs, Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @GetMapping(value = "/contar-todos")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAll() {
        Long quantity= visitaService.contarTodos();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todos-con-borrados")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<Long> countAllByRemovedIsNull() {
        Long quantity= visitaService.contarTodosConBorrados();
        return new ResponseEntity<>(quantity, Helper.httpHeaders(String.valueOf(quantity)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDTO> insert(@Valid @RequestBody VisitaCreation visitaCreation) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.insertar(visitaCreation);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitaMapper.toDto(VisitaModelEntityMessenger.getObject()), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDTO> update(@Valid @RequestBody VisitaModel visitaModel) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.actualizar(visitaModel);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitaMapper.toDto(VisitaModelEntityMessenger.getObject()), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDTO> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.borrar(id);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 201)
            return new ResponseEntity<>(visitaMapper.toDto(VisitaModelEntityMessenger.getObject()), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<VisitaDTO> recycle(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.reciclar(id);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(visitaMapper.toDto(VisitaModelEntityMessenger.getObject()), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public ResponseEntity<String> destroy(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<VisitaModel> VisitaModelEntityMessenger = visitaService.destruir(id);
        if (VisitaModelEntityMessenger.getStatusCode() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
        else if (VisitaModelEntityMessenger.getStatusCode() == 200)
            return new ResponseEntity<>(VisitaModelEntityMessenger.getMessage(), Helper.httpHeaders(VisitaModelEntityMessenger.getMessage()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(VisitaModelEntityMessenger.getMessage())).build();
    }
}
