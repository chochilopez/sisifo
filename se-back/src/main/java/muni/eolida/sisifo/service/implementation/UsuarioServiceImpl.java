package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;
    private final RolServiceImpl rolService;

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario) {
        try {
            log.info("Buscando la entidad Usuario con nombre de usuario: {}.", nombreUsuario);
            Optional<UsuarioModel> objeto = usuarioDAO.findByUsernameContainingIgnoreCaseAndEliminadaIsNull(nombreUsuario);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ".";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Usuario.";
                log.info(mensaje);
                return new EntityMessenger<UsuarioModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario) {
        try {
            log.info("Buscando la entidad Usuario con nombre de usuario: {}, incluidas las eliminadas.", nombreUsuario);
            Optional<UsuarioModel> objeto = usuarioDAO.findByUsernameContainingIgnoreCase(nombreUsuario);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Usuario, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<UsuarioModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> darRol(UsuarioModel usuarioModel, String rolEnum) {
        try {
            log.info("Agregando el rol {} al usuario {}.", rolEnum, usuarioModel.getUsername());
            EntityMessenger<RolModel> objeto =  rolService.buscarPorRol(rolEnum);
            if (objeto.getEstado() != 200){
                String mensaje = "No existe el rol: " + rolEnum + ".";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            }
            if (usuarioModel.getRoles().isEmpty()) {
                HashSet<RolModel> roles = new HashSet<>();
                usuarioModel.setRoles(roles);
            }
            usuarioModel.getRoles().add(objeto.getObjeto());
            this.actualizar(usuarioModel);
            String mensaje = "El rol " + rolEnum + " fue añadido correctamente al usuario " + usuarioModel.getUsername() + ".";
            log.info(mensaje);
            return new EntityMessenger<UsuarioModel>(usuarioModel, null, mensaje, 200);
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> obtenerUsuario() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null)
                return this.buscarPorNombreDeUsuario(authentication.getName());
            else
                return this.buscarPorNombreDeUsuario("admin@municrespo.gob.ar");
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad Usuario con id: {}.", id);
            Optional<UsuarioModel> objeto = usuarioDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Usuario con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Usuario.";
                log.info(mensaje);
                return new EntityMessenger<UsuarioModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad Usuario con id: {}, incluidas las eliminadas.", id);
            Optional<UsuarioModel> objeto = usuarioDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Usuario con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Usuario, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<UsuarioModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades Usuario.");
            List<UsuarioModel> listado = usuarioDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Usuario.";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Usuario.";
                log.info(mensaje);
                return new EntityMessenger<UsuarioModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades Usuario, incluidas las eliminadas.");
            List<UsuarioModel> listado = usuarioDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades Usuario, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<UsuarioModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Usuario, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<UsuarioModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
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
    public EntityMessenger<UsuarioModel> insertar(UsuarioCreation model) {
        try {
            log.info("Insertando la entidad Usuario: {}.",  model);
            UsuarioModel objeto = usuarioDAO.save(usuarioMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(this.obtenerUsuario().getObjeto());
            usuarioDAO.save(objeto);
            String mensaje = "La entidad Usuario con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<UsuarioModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Usuario. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> actualizar(UsuarioModel model) {
        try {
            log.info("Actualizando la entidad Usuario: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<UsuarioModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(this.obtenerUsuario().getObjeto());
            UsuarioModel objeto = usuarioDAO.save(model);
            String mensaje = "La entidad Usuario con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<UsuarioModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Usuario. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad Usuario con id: {}.", id);
            EntityMessenger<UsuarioModel> objeto = this.buscarPorIdConEliminadas(id);
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
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad Usuario con id: {}.", id);
            EntityMessenger<UsuarioModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(this.obtenerUsuario().getObjeto());
            objeto.setObjeto(usuarioDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Usuario con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Usuario con id: {}.", id);
            EntityMessenger<UsuarioModel> objeto = this.buscarPorIdConEliminadas(id);
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
            return new EntityMessenger<UsuarioModel>(null, null, mensaje, 204);
        }
    }
}
