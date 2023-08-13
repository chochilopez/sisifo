package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.SeguimientoMapper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.repository.SeguimientoDAO;
import muni.eolida.sisifo.service.SeguimientoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class SeguimientoServiceImpl implements SeguimientoService {

    private final SeguimientoDAO seguimientoDAO;
    private final SeguimientoMapper seguimientoMapper;

    @Override
    public EntityMessenger<SeguimientoModel> buscarPorId(Long id) {
        log.info("Searching for entity SeguimientoModel with id: {}.", id);
        Optional<SeguimientoModel> object = seguimientoDAO.findByIdAndBajaIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity SeguimientoModel with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<SeguimientoModel>(null, null, message, 202);
        } else {
            String message = "One entity SeguimientoModel was found.";
            log.info(message);
            return new EntityMessenger<SeguimientoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity SeguimientoModel with id: {}, included deleted ones.", id);
        Optional<SeguimientoModel> object = seguimientoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity SeguimientoModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<SeguimientoModel>(null, null, message, 202);
        } else {
            String message = "One entity SeguimientoModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<SeguimientoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodos() {
        log.info("Searching for all entities SeguimientoModel.");
        List<SeguimientoModel> list = seguimientoDAO.findAllByBajaIsNull();
        if (list.isEmpty()) {
            String message = "No entities SeguimientoModel were found.";
            log.warn(message);
            return new EntityMessenger<SeguimientoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities SeguimientoModel were found.";
            log.info(message);
            return new EntityMessenger<SeguimientoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodosConBorrados() {
        log.info("Searching for all entities SeguimientoModel, included deleted ones.");
        List<SeguimientoModel> list = seguimientoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities SeguimientoModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<SeguimientoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities SeguimientoModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<SeguimientoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = seguimientoDAO.countAllByBajaIsNull();
        log.info("Table SeguimientoModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = seguimientoDAO.count();
        log.info("Table SeguimientoModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<SeguimientoModel> insertar(SeguimientoCreation model) {
        try {
            log.info("Inserting entity SeguimientoModel: {}.",  model);
            SeguimientoModel seguimientoModel = seguimientoDAO.save(seguimientoMapper.toEntity(model));
            seguimientoModel.setAlta(Helper.getNow(""));
            seguimientoDAO.save(seguimientoModel);
            String message = "The entity SeguimientoModel with id: " + seguimientoModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<SeguimientoModel>(seguimientoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<SeguimientoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> actualizar(SeguimientoModel model) {
        try {
            log.info("Updating entity SeguimientoModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<SeguimientoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            SeguimientoModel seguimientoModel = seguimientoDAO.save(model);
            String message = "The entity SeguimientoModel with id: " + seguimientoModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<SeguimientoModel>(seguimientoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<SeguimientoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> reciclar(Long id) {
        log.info("Recycling entity SeguimientoModel with: {}.", id);
        EntityMessenger<SeguimientoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity SeguimientoModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(null);
        entityMessenger.setObject(seguimientoDAO.save(entityMessenger.getObject()));
        String message = "The entity SeguimientoModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<SeguimientoModel> borrar(Long id) {
        log.info("Deleting entity SeguimientoModel with: {}.", id);
        EntityMessenger<SeguimientoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(Helper.getNow(""));
        entityMessenger.setObject(seguimientoDAO.save(entityMessenger.getObject()));
        String message = "The entity SeguimientoModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<SeguimientoModel> destruir(Long id) {
        log.info("Destroying entity SeguimientoModel with: {}.", id);
        EntityMessenger<SeguimientoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity SeguimientoModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        seguimientoDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
