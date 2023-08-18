package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private RolServiceImpl rolService;

    @Override
    public EntidadMensaje<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}.", nombreUsuario);
        Optional<UsuarioModel> objeto = usuarioDAO.findByUsernameContainingIgnoreCaseAndEliminadaIsNull(nombreUsuario);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ".";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Usuario.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}, incluidas las eliminadas.", nombreUsuario);
        Optional<UsuarioModel> objeto = usuarioDAO.findByUsernameContainingIgnoreCase(nombreUsuario);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Usuario, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> darRol(UsuarioModel usuarioModel, RolEnum rolEnum) {
        log.info("Agregando el rol {} al usuario {}.", rolEnum, usuarioModel.getUsername());
        EntidadMensaje<RolModel> objeto =  rolService.buscarPorRol(rolEnum);
        if (objeto.getEstado() != 200){
            String mensaje = "No existe el rol: " + rolEnum + ".";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        }
        if (usuarioModel.getRoles().isEmpty()) {
            HashSet<RolModel> roles = new HashSet<>();
            usuarioModel.setRoles(roles);
        }
        usuarioModel.getRoles().add(objeto.getObjeto());
        this.actualizar(usuarioModel);
        String mensaje = "El rol " + rolEnum + " fue añadido correctamente al usuario " + usuarioModel.getUsername() + ".";
        log.info(mensaje);
        return new EntidadMensaje<UsuarioModel>(usuarioModel, null, mensaje, 200);
    }

    @Override
    public EntidadMensaje<UsuarioModel> obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return this.buscarPorNombreDeUsuario(authentication.getName());
        else
            return this.buscarPorNombreDeUsuario("admin@municrespo.gob.ar");
    }

    @Override
    public EntidadMensaje<UsuarioModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Usuario con id: {}.", id);
        Optional<UsuarioModel> objeto = usuarioDAO.findByIdAndEliminadaIsNull(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Usuario con id: " + id + ".";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Usuario.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Usuario con id: {}, incluidas las eliminadas.", id);
        Optional<UsuarioModel> objeto = usuarioDAO.findById(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Usuario con id: " + id + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Usuario, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> buscarTodas() {
        log.info("Buscando todas las entidades Usuario.");
        List<UsuarioModel> listado = usuarioDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Usuario.";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Usuario.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Usuario, incluidas las eliminadas.");
        List<UsuarioModel> listado = usuarioDAO.findAll();
        if (listado.isEmpty()) {
            String mensaje = "No se encontrarón entidades Usuario, incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Usuario, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = usuarioDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Usuario.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = usuarioDAO.count();
        log.info("Existen {} entidades Usuario, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntidadMensaje<UsuarioModel> insertar(UsuarioCreation model) {
        try {
            log.info("Insertando la entidad Usuario: {}.",  model);
            UsuarioModel objeto = usuarioDAO.save(usuarioMapper.toEntity(model));
            objeto.setCreada(Ayudador.getAhora(""));
            objeto.setCreador(this.obtenerUsuario().getObjeto());
            usuarioDAO.save(objeto);
            String mensaje = "La entidad Usuario con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Usuario. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> actualizar(UsuarioModel model) {
        try {
            log.info("Actualizando la entidad Usuario: {}.",  model);
            if (model.getId() != null) {
                EntidadMensaje<UsuarioModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Ayudador.getAhora(""));
            model.setModificador(this.obtenerUsuario().getObjeto());
            UsuarioModel objeto = usuarioDAO.save(model);
            String mensaje = "La entidad Usuario con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<UsuarioModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Usuario. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<UsuarioModel> reciclar(Long id) {
        log.info("Reciclando la entidad Usuario con id: {}.", id);
        EntidadMensaje<UsuarioModel> objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        if (objeto.getObjeto().getEliminada() == null) {
            String mensaje = "La entidad Usuario con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(mensaje);
            objeto.setMensaje(mensaje);
            return objeto;
        }
        objeto.getObjeto().setEliminada(null);
        objeto.getObjeto().setEliminador(null);
        objeto.setObjeto(usuarioDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Usuario con id: " + id + ", fue reciclada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<UsuarioModel> eliminar(Long id) {
        log.info("Borrando la entidad Usuario con id: {}.", id);
        EntidadMensaje<UsuarioModel> objeto = this.buscarPorId(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        objeto.getObjeto().setEliminada(Ayudador.getAhora(""));
        objeto.getObjeto().setEliminador(this.obtenerUsuario().getObjeto());
        objeto.setObjeto(usuarioDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Usuario con id: " + id + ", fue borrada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<UsuarioModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Usuario con id: {}.", id);
            EntidadMensaje<UsuarioModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Usuario con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            usuarioDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Usuario. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<UsuarioModel>(null, null, mensaje, 204);
        }
    }
}
