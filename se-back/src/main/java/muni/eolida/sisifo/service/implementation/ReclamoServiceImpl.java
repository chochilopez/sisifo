package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.repository.ReclamoDAO;
import muni.eolida.sisifo.service.ReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReclamoServiceImpl implements ReclamoService {

    private final ReclamoDAO reclamoDAO;
    private final ReclamoMapper reclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<ReclamoModel> buscarPorId(Long id) {
        log.info("Searching for entity ReclamoModel with id: {}.", id);
        Optional<ReclamoModel> object = reclamoDAO.findByIdAndBorradoIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity ReclamoModel with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "One entity ReclamoModel was found.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity ReclamoModel with id: {}, included deleted ones.", id);
        Optional<ReclamoModel> object = reclamoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity ReclamoModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "One entity ReclamoModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodos() {
        log.info("Searching for all entities ReclamoModel.");
        List<ReclamoModel> list = reclamoDAO.findAllByBorradoIsNull();
        if (list.isEmpty()) {
            String message = "No entities ReclamoModel were found.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities ReclamoModel were found.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosConBorrados() {
        log.info("Searching for all entities ReclamoModel, included deleted ones.");
        List<ReclamoModel> list = reclamoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities ReclamoModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities ReclamoModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = reclamoDAO.countAllByBorradoIsNull();
        log.info("Table ReclamoModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = reclamoDAO.count();
        log.info("Table ReclamoModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<ReclamoModel> insertar(ReclamoCreation model) {
        try {
            log.info("Inserting entity ReclamoModel: {}.",  model);
            ReclamoModel reclamoModel = reclamoDAO.save(reclamoMapper.toEntity(model));
            reclamoModel.setCreado(Helper.getNow(""));
            reclamoModel.setCreador(usuarioService.obtenerUsuario().getObject());
            reclamoDAO.save(reclamoModel);
            String message = "The entity ReclamoModel with id: " + reclamoModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(reclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> actualizar(ReclamoModel model) {
        try {
            log.info("Updating entity ReclamoModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<ReclamoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            ReclamoModel reclamoModel = reclamoDAO.save(model);
            String message = "The entity ReclamoModel with id: " + reclamoModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(reclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> reciclar(Long id) {
        log.info("Recycling entity ReclamoModel with: {}.", id);
        EntityMessenger<ReclamoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity ReclamoModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
        entityMessenger.setObject(reclamoDAO.save(entityMessenger.getObject()));
        String message = "The entity ReclamoModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ReclamoModel> borrar(Long id) {
        log.info("Deleting entity ReclamoModel with: {}.", id);
        EntityMessenger<ReclamoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(reclamoDAO.save(entityMessenger.getObject()));
        String message = "The entity ReclamoModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ReclamoModel> destruir(Long id) {
        log.info("Destroying entity ReclamoModel with: {}.", id);
        EntityMessenger<ReclamoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity ReclamoModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        reclamoDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
