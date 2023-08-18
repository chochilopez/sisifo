package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDTO;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.service.implementation.ArchivoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/archivo")
@RestController
@Slf4j
public class ArchivoController {

    @Autowired
    private ArchivoServiceImpl archivoService;
    @Autowired
    private ArchivoMapper archivoMapper;

    @PutMapping(value = "/guardar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<ArchivoDTO> saveLocalFile(@RequestParam("file") MultipartFile multipartFile) {
        try{
            EntidadMensaje<ArchivoModel> objeto = archivoService.guardarArchivo(multipartFile.getBytes());
            if (objeto.getEstado() == 202)
                return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
            else if (objeto.getEstado() == 201)
                return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
            else
                return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        } catch (Exception e){
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(e.getMessage())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<ArchivoDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ArchivoDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<List<ArchivoDTO>> buscarTodas() {
        EntidadMensaje<ArchivoModel> listado = archivoService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<ArchivoDTO> ArchivoDTOs = new ArrayList<>();
            for (ArchivoModel ArchivoModel:listado.getListado()) {
                ArchivoDTOs.add(archivoMapper.toDto(ArchivoModel));
            }
            return new ResponseEntity<>(ArchivoDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<ArchivoDTO>> buscarTodasConEliminadas() {
        EntidadMensaje<ArchivoModel> listado = archivoService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<ArchivoDTO> ArchivoDTOs = new ArrayList<>();
            for (ArchivoModel ArchivoModel:listado.getListado()) {
                ArchivoDTOs.add(archivoMapper.toDto(ArchivoModel));
            }
            return new ResponseEntity<>(ArchivoDTOs, Ayudador.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CONTRIBUYENTE')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad= archivoService.contarTodas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad= archivoService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Ayudador.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ArchivoDTO> insertar(@Valid @RequestBody ArchivoCreation archivoCreation) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.insertar(archivoCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ArchivoDTO> actualizar(@Valid @RequestBody ArchivoModel archivoModel) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.actualizar(archivoModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<ArchivoDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<ArchivoDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(archivoMapper.toDto(objeto.getObjeto()), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntidadMensaje<ArchivoModel> objeto = archivoService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Ayudador.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Ayudador.httpHeaders(objeto.getMensaje())).build();
    }
}
