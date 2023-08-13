package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.RolMapper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.RolDAO;
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

    @Override
    public EntityMessenger<RolModel> buscarTodosPorRol(RolEnum rol) {
        log.info("Searching for entity RolModel with rol: {}.", rol);
        Optional<RolModel> object = rolDAO.findByRol(rol);
        if (object.isEmpty()) {
            String message = "No entity RolModel with rol: " + rol + " was found.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "One entity RolModel was found.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodosPorRolConBorrados(RolEnum rol) {
        log.info("Searching for entity RolModel with rol: {}.", rol);
        Optional<RolModel> object = rolDAO.findByRolAndBajaIsNull(rol);
        if (object.isEmpty()) {
            String message = "No entity RolModel with rol: " + rol + " was found.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "One entity RolModel was found.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorId(Long id) {
        log.info("Searching for entity RolModel with id: {}.", id);
        Optional<RolModel> object = rolDAO.findByIdAndBajaIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity RolModel with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "One entity RolModel was found.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity RolModel with id: {}, included deleted ones.", id);
        Optional<RolModel> object = rolDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity RolModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "One entity RolModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodos() {
        log.info("Searching for all entities RolModel.");
        List<RolModel> list = rolDAO.findAllByBajaIsNull();
        if (list.isEmpty()) {
            String message = "No entities RolModel were found.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities RolModel were found.";
            log.info(message);
            return new EntityMessenger<RolModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodosConBorrados() {
        log.info("Searching for all entities RolModel, included deleted ones.");
        List<RolModel> list = rolDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities RolModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities RolModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<RolModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = rolDAO.countAllByBajaIsNull();
        log.info("Table RolModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = rolDAO.count();
        log.info("Table RolModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<RolModel> insertar(RolCreation model) {
        try {
            log.info("Inserting entity RolModel: {}.",  model);
            RolModel rolModel = rolDAO.save(rolMapper.toEntity(model));
            rolModel.setAlta(Helper.getNow(""));
            rolDAO.save(rolModel);
            String message = "The entity RolModel with id: " + rolModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<RolModel>(rolModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> actualizar(RolModel model) {
        try {
            log.info("Updating entity RolModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<RolModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            RolModel rolModel = rolDAO.save(model);
            String message = "The entity RolModel with id: " + rolModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<RolModel>(rolModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> reciclar(Long id) {
        log.info("Recycling entity RolModel with: {}.", id);
        EntityMessenger<RolModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity RolModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(null);
        entityMessenger.setObject(rolDAO.save(entityMessenger.getObject()));
        String message = "The entity RolModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<RolModel> borrar(Long id) {
        log.info("Deleting entity RolModel with: {}.", id);
        EntityMessenger<RolModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(Helper.getNow(""));
        entityMessenger.setObject(rolDAO.save(entityMessenger.getObject()));
        String message = "The entity RolModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<RolModel> destruir(Long id) {
        log.info("Destroying entity RolModel with: {}.", id);
        EntityMessenger<RolModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity RolModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        rolDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}

