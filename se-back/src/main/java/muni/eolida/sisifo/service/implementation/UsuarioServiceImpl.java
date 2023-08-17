package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;
    private final RolServiceImpl rolService;

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}.", nombreUsuario);
        Optional<UsuarioModel> object = usuarioDAO.findByUsernameContainingIgnoreCaseAndEliminadaIsNull(nombreUsuario);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ".";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Usuario.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}, incluidas las eliminadas.", nombreUsuario);
        Optional<UsuarioModel> object = usuarioDAO.findByUsernameContainingIgnoreCase(nombreUsuario);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Usuario, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> darRol(UsuarioModel usuarioModel, RolEnum rolEnum) {
        log.info("Agregando el rol {} al usuario {}.", rolEnum, usuarioModel.getUsername());
        EntityMessenger<RolModel> rolModelEntityMessenger =  rolService.buscarTodosPorRol(rolEnum);
        if (rolModelEntityMessenger.getStatusCode() != 200){
            String message = "No existe el rol: " + rolEnum + ".";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        }
        if (usuarioModel.getRoles().isEmpty()) {
            HashSet<RolModel> roles = new HashSet<>();
            usuarioModel.setRoles(roles);
        }
        usuarioModel.getRoles().add(rolModelEntityMessenger.getObject());
        this.actualizar(usuarioModel);
        String message = "El rol " + rolEnum + " fue añadido correctamente al usuario " + usuarioModel.getUsername() + ".";
        log.info(message);
        return new EntityMessenger<UsuarioModel>(usuarioModel, null, message, 200);
    }

    @Override
    public EntityMessenger<UsuarioModel> obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return this.buscarPorNombreDeUsuario(authentication.getName());
        else
            return this.buscarPorNombreDeUsuario("admin@municrespo.gob.ar");
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Usuario con id: {}.", id);
        Optional<UsuarioModel> object = usuarioDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Usuario con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Usuario.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Usuario con id: {}, incluidas las eliminadas.", id);
        Optional<UsuarioModel> object = usuarioDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Usuario con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Usuario, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodas() {
        log.info("Buscando todas las entidades Usuario.");
        List<UsuarioModel> list = usuarioDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Usuario.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Usuario.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Usuario, incluidas las eliminadas.");
        List<UsuarioModel> list = usuarioDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Usuario, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Usuario, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = usuarioDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Usuario.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = usuarioDAO.count();
        log.info("Existen {} entidades Usuario, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<UsuarioModel> insertar(UsuarioCreation model) {
        try {
            log.info("Insertando la entidad Usuario: {}.",  model);
            UsuarioModel object = usuarioDAO.save(usuarioMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(this.obtenerUsuario().getObject());
            usuarioDAO.save(object);
            String message = "La entidad Usuario con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Usuario. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> actualizar(UsuarioModel model) {
        try {
            log.info("Actualizando la entidad Usuario: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<UsuarioModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(this.obtenerUsuario().getObject());
            UsuarioModel object = usuarioDAO.save(model);
            String message = "La entidad Usuario con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Usuario. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> reciclar(Long id) {
        log.info("Reciclando la entidad Usuario con id: {}.", id);
        EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Usuario con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(usuarioDAO.save(entityMessenger.getObject()));
        String message = "La entidad Usuario con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UsuarioModel> eliminar(Long id) {
        log.info("Borrando la entidad Usuario con id: {}.", id);
        EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(this.obtenerUsuario().getObject());
        entityMessenger.setObject(usuarioDAO.save(entityMessenger.getObject()));
        String message = "La entidad Usuario con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UsuarioModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Usuario con id: {}.", id);
            EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Usuario con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            usuarioDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Usuario. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }
}
