package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.VisitaMapper;
import muni.eolida.sisifo.mapper.creation.VisitaCreation;
import muni.eolida.sisifo.model.VisitaModel;
import muni.eolida.sisifo.repository.VisitaDAO;
import muni.eolida.sisifo.service.VisitaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class VisitaServiceImpl implements VisitaService {

    private final VisitaDAO visitaDAO;
    private final VisitaMapper visitaMapper;

    @Override
    public EntityMessenger<VisitaModel> buscarTodosPorIp(String ip) {
        log.info("Searching for entities Visit with ip: {}.", ip);
        List<VisitaModel> VisitaModels = visitaDAO.findAllByIpContainingAndBajaIsNull(ip);
        if (VisitaModels.isEmpty()) {
            String message = "No entities Visit was found with ip adrress " + ip + ".";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = VisitaModels.size() + " entities Visit was found with ip adrress " + ip + ".";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, VisitaModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarTodosPorIpConBorrados(String ip) {
        log.info("Searching for entities Visit with ip: {}, included deleted ones.", ip);
        List<VisitaModel> VisitaModels = visitaDAO.findAllByIpContaining(ip);
        if (VisitaModels.isEmpty()) {
            String message = "No entities Visit was found with ip adrress " + ip + ", included deleted ones.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = VisitaModels.size() + " entities Visit was found with ip adrress " + ip + ", included deleted ones.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, VisitaModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarLosPrimerosN(int number) {
        log.info("Searching for top " + number + " entities in Visit.");
        List<VisitaModel> VisitaModels = visitaDAO.findTopNAndBajaIsNull(number);
        if (VisitaModels.isEmpty()) {
            String message = "No entities Visit was found.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = VisitaModels.size() + " entities Visit was found.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, VisitaModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarLosPrimerosNConBorrados(int number) {
        log.info("Searching for top " + number + " entities in Visit, included deleted ones.");
        List<VisitaModel> VisitaModels = visitaDAO.findTopN(number);
        if (VisitaModels.isEmpty()) {
            String message = "No entities Visit was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = VisitaModels.size() + " entities Visit was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, VisitaModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarPorId(Long id) {
        log.info("Searching for entity Visit with id: {}.", id);
        Optional<VisitaModel> object = visitaDAO.findByIdAndBajaIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity Visit with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = "One entity Visit was found.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity Visit with id: {}, included deleted ones.", id);
        Optional<VisitaModel> object = visitaDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity Visit with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = "One entity Visit was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarTodos() {
        log.info("Searching for all entities Visit.");
        List<VisitaModel> list = visitaDAO.findAllByBajaIsNull();
        if (list.isEmpty()) {
            String message = "No entities Visit were found.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit were found.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarTodosConBorrados() {
        log.info("Searching for all entities Visit, included deleted ones.");
        List<VisitaModel> list = visitaDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities Visit were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = visitaDAO.countAllByBajaIsNull();
        log.info("Table Visit possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = visitaDAO.count();
        log.info("Table Visit possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<VisitaModel> insertar(VisitaCreation model) {
        try {
            log.info("Inserting entity Visit: {}.",  model);
            VisitaModel VisitaModel = visitaDAO.save(visitaMapper.toEntity(model));
            VisitaModel.setAlta(Helper.getNow(""));
            visitaDAO.save(VisitaModel);
            String message = "The entity Visit with id: " + VisitaModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(VisitaModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> actualizar(VisitaModel model) {
        try {
            log.info("Updating entity Visit: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<VisitaModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            VisitaModel VisitaModel = visitaDAO.save(model);
            String message = "The entity Visit with id: " + VisitaModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(VisitaModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> reciclar(Long id) {
        log.info("Recycling entity Visit with: {}.", id);
        EntityMessenger<VisitaModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity Visit with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(null);
        entityMessenger.setObject(visitaDAO.save(entityMessenger.getObject()));
        String message = "The entity Visit with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<VisitaModel> borrar(Long id) {
        log.info("Deleting entity Visit with: {}.", id);
        EntityMessenger<VisitaModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(Helper.getNow(""));
        entityMessenger.setObject(visitaDAO.save(entityMessenger.getObject()));
        String message = "The entity Visit with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<VisitaModel> destruir(Long id) {
        log.info("Destroying entity Visit with: {}.", id);
        EntityMessenger<VisitaModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity Visit with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        visitaDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
