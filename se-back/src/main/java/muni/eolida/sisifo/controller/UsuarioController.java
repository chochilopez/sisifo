package muni.eolida.sisifo.controller;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/usuario")
@RestController
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping(value = "/buscar-por-nombre-usuario/{nombreUsuario}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombreDeUsuario(@PathVariable(name = "nombreUsuario") @javax.validation.constraints.Size(min = 3, max = 50) String nombreUsuario) {
        EntityMessenger<UsuarioModel> listado = usuarioService.buscarPorNombreDeUsuario(nombreUsuario);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<UsuarioDTO> usuarioDTOS = new ArrayList<>();
            for (UsuarioModel usuarioModel : listado.getListado()) {
                usuarioDTOS.add(usuarioMapper.toDto(usuarioModel));
            }
            return new ResponseEntity<>(usuarioDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-nombre-usuario-con-eliminadas/{nombreUsuario}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombreDeUsuarioConEliminadas(@PathVariable(name = "nombreUsuario") @javax.validation.constraints.Size(min = 3, max = 50) String nombreUsuario) {
        EntityMessenger<UsuarioModel> listado = usuarioService.buscarPorNombreDeUsuarioConEliminadas(nombreUsuario);
        if (listado.getEstado() == 202) {
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        } else if (listado.getEstado() == 200) {
            ArrayList<UsuarioDTO> usuarioDTOS = new ArrayList<>();
            for (UsuarioModel usuarioModel : listado.getListado()) {
                usuarioDTOS.add(usuarioMapper.toDto(usuarioModel));
            }
            return new ResponseEntity<>(usuarioDTOS, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
        }
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.buscarPorId(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-por-id-con-eliminadas/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<UsuarioDTO> buscarPorIdConEliminadas(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<List<UsuarioDTO>> buscarTodas() {
        EntityMessenger<UsuarioModel> listado = usuarioService.buscarTodas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<UsuarioDTO> UsuarioDTOs = new ArrayList<>();
            for (UsuarioModel UsuarioModel:listado.getListado()) {
                UsuarioDTOs.add(usuarioMapper.toDto(UsuarioModel));
            }
            return new ResponseEntity<>(UsuarioDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/buscar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<List<UsuarioDTO>> buscarTodasConEliminadas() {
        EntityMessenger<UsuarioModel> listado = usuarioService.buscarTodasConEliminadas();
        if (listado.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(listado.getMensaje())).build();
        else if (listado.getEstado() == 200){
            ArrayList<UsuarioDTO> UsuarioDTOs = new ArrayList<>();
            for (UsuarioModel UsuarioModel:listado.getListado()) {
                UsuarioDTOs.add(usuarioMapper.toDto(UsuarioModel));
            }
            return new ResponseEntity<>(UsuarioDTOs, Helper.httpHeaders(listado.getMensaje()), HttpStatus.OK);
        }
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(listado.getMensaje())).build();
    }

    @GetMapping(value = "/contar-todas")
    @PreAuthorize("hasAuthority('CAPATAZ')")
    public ResponseEntity<Long> contarTodas() {
        Long cantidad = usuarioService.contarTodas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @GetMapping(value = "/contar-todas-con-eliminadas")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<Long> contarTodasConEliminadas() {
        Long cantidad = usuarioService.contarTodasConEliminadas();
        return new ResponseEntity<>(cantidad, Helper.httpHeaders(String.valueOf(cantidad)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<UsuarioDTO> insertar(@Valid @RequestBody UsuarioCreation usuarioCreation) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.insertar(usuarioCreation);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<UsuarioDTO> actualizar(@Valid @RequestBody UsuarioModel usuarioModel) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.actualizar(usuarioModel);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.CREATED);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<UsuarioDTO> borrar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.eliminar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 201)
            return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @PostMapping(value = "/reciclar/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<UsuarioDTO> reciclar(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.reciclar(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }

    @DeleteMapping(value = "/destruir/{id}")
    @PreAuthorize("hasAuthority('JEFE')")
    public ResponseEntity<String> destruir(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        EntityMessenger<UsuarioModel> objeto = usuarioService.destruir(id);
        if (objeto.getEstado() == 202)
            return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
        else if (objeto.getEstado() == 200)
            return new ResponseEntity<>(objeto.getMensaje(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
        else
            return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
    }
}
