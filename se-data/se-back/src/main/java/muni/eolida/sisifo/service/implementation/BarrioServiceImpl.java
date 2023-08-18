package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.BarrioMapper;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.repository.BarrioDAO;
import muni.eolida.sisifo.service.BarrioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BarrioServiceImpl implements BarrioService {

    private final BarrioDAO barrioDAO;
    private final BarrioMapper barrioMapper;
    private final UsuarioServiceImpl usuarioService;


    @Override
    public EntityMessenger<BarrioModel> buscarTodosPorBarrioIgnoreCaseContaining(String barrio) {
        return null;
    }

    @Override
    public EntityMessenger<BarrioModel> buscarTodosPorBarrioIgnoreCaseContainingAndBorradoIsNull(String barrio) {
        return null;
    }

    @Override
    public EntityMessenger<BarrioModel> buscarPorId(Long id) {
        log.info("Buscando entidades Barrio con id: {}.", id);
        Optional<BarrioModel> object = barrioDAO.findByIdAndBorradoIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontraron entidades Barrio con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Barrio.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity BarrioModel with id: {}, included deleted ones.", id);
        Optional<BarrioModel> object = barrioDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity BarrioModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "One entity BarrioModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarTodos() {
        log.info("Buscando todas las entidades Barrio.");
        List<BarrioModel> list = barrioDAO.findAllByBorradoIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Barrio.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades Barrio fueron encontradas.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarTodosConBorrados() {
        log.info("Searching for all entities BarrioModel, included deleted ones.");
        List<BarrioModel> list = barrioDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities BarrioModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities BarrioModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = barrioDAO.countAllByBorradoIsNull();
        log.info("Table BarrioModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = barrioDAO.count();
        log.info("Table BarrioModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<BarrioModel> insertar(BarrioCreation model) {
        try {
            log.info("Inserting entity BarrioModel: {}.",  model);
            BarrioModel barrioModel = barrioDAO.save(barrioMapper.toEntity(model));
            barrioModel.setCreado(Helper.getNow(""));
            barrioModel.setCreador(usuarioService.obtenerUsuario().getObject());
            barrioDAO.save(barrioModel);
            String message = "The entity BarrioModel with id: " + barrioModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(barrioModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> actualizar(BarrioModel model) {
        try {
            log.info("Updating entity BarrioModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<BarrioModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            BarrioModel barrioModel = barrioDAO.save(model);
            String message = "The entity BarrioModel with id: " + barrioModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(barrioModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> reciclar(Long id) {
        log.info("Recycling entity BarrioModel with: {}.", id);
        EntityMessenger<BarrioModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity BarrioModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
        entityMessenger.setObject(barrioDAO.save(entityMessenger.getObject()));
        String message = "The entity BarrioModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<BarrioModel> borrar(Long id) {
        log.info("Deleting entity BarrioModel with: {}.", id);
        EntityMessenger<BarrioModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(barrioDAO.save(entityMessenger.getObject()));
        String message = "The entity BarrioModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<BarrioModel> destruir(Long id) {
        log.info("Destroying entity BarrioModel with: {}.", id);
        EntityMessenger<BarrioModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity BarrioModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        barrioDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
