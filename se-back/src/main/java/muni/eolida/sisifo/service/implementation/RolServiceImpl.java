package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.RolMapper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.RolDAO;
import muni.eolida.sisifo.service.RolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RolServiceImpl implements RolService {

    private final RolDAO rolDAO;
    private final RolMapper rolMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<RolModel> buscarPorRol(RolEnum rol) {
        log.info("Buscando la entidad Rol con rol: {}.", rol);
        Optional<RolModel> object = rolDAO.findByRolAndEliminadaIsNull(rol);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Rol con rol: " + rol + ".";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Rol.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorRolConEliminadas(RolEnum rol) {
        log.info("Buscando la entidad Rol con rol: {}, incluidas las eliminadas.", rol);
        Optional<RolModel> object = rolDAO.findByRol(rol);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Rol con rol: " + rol + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Rol, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Rol con id: {}.", id);
        Optional<RolModel> object = rolDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Rol con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Rol.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Rol con id: {}, incluidas las eliminadas.", id);
        Optional<RolModel> object = rolDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Rol con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Rol, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodas() {
        log.info("Buscando todas las entidades Rol.");
        List<RolModel> list = rolDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Rol.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Rol.";
            log.info(message);
            return new EntityMessenger<RolModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Rol, incluidas las eliminadas.");
        List<RolModel> list = rolDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Rol, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Rol, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<RolModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = rolDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Rol.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = rolDAO.count();
        log.info("Existen {} entidades Rol, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<RolModel> insertar(RolCreation model) {
        try {
            log.info("Insertando la entidad Rol: {}.",  model);
            RolModel object = rolDAO.save(rolMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(usuarioService.obtenerUsuario().getObject());
            rolDAO.save(object);
            String message = "La entidad Rol con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<RolModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Rol. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> actualizar(RolModel model) {
        try {
            log.info("Actualizando la entidad Rol: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<RolModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            RolModel object = rolDAO.save(model);
            String message = "La entidad Rol con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<RolModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Rol. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> reciclar(Long id) {
        log.info("Reciclando la entidad Rol con id: {}.", id);
        EntityMessenger<RolModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Rol con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(rolDAO.save(entityMessenger.getObject()));
        String message = "La entidad Rol con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<RolModel> eliminar(Long id) {
        log.info("Borrando la entidad Rol con id: {}.", id);
        EntityMessenger<RolModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(rolDAO.save(entityMessenger.getObject()));
        String message = "La entidad Rol con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<RolModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Rol con id: {}.", id);
            EntityMessenger<RolModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Rol con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            rolDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Rol. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }
}
