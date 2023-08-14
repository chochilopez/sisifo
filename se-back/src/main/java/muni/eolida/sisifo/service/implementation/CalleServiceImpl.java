package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.CalleMapper;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.repository.CalleDAO;
import muni.eolida.sisifo.service.CalleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CalleServiceImpl implements CalleService {

    private final CalleDAO calleDAO;
    private final CalleMapper calleMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<CalleModel> buscarTodosPorCalle(String calle) {
        log.info("Buscando entidades Calle con nombre: {}.", calle);
        List<CalleModel> list = calleDAO.findAllByCalleIgnoreCaseContainingAndBorradoIsNull(calle);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Calle con nombre " + calle + ".";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Calle con nombre " + calle + ".";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodosPorCalleConBorrados(String calle) {
        log.info("Searching for entities Calle with name: {}, included deleted ones.", calle);
        List<CalleModel> list = calleDAO.findAllByCalleIgnoreCaseContaining(calle);
        if (list.isEmpty()) {
            String message = "No entities Calle was found with name " + calle + ", included deleted ones.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Calle was found with name " + calle + ", included deleted ones.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarPorId(Long id) {
        log.info("Searching for entity CalleModel with id: {}.", id);
        Optional<CalleModel> object = calleDAO.findByIdAndBorradoIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity CalleModel with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "One entity CalleModel was found.";
            log.info(message);
            return new EntityMessenger<CalleModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity CalleModel with id: {}, included deleted ones.", id);
        Optional<CalleModel> object = calleDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity CalleModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "One entity CalleModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<CalleModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodos() {
        log.info("Searching for all entities CalleModel.");
        List<CalleModel> list = calleDAO.findAllByBorradoIsNull();
        if (list.isEmpty()) {
            String message = "No entities CalleModel were found.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities CalleModel were found.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodosConBorrados() {
        log.info("Searching for all entities CalleModel, included deleted ones.");
        List<CalleModel> list = calleDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities CalleModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities CalleModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = calleDAO.countAllByBorradoIsNull();
        log.info("Table CalleModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = calleDAO.count();
        log.info("Table CalleModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<CalleModel> insertar(CalleCreation model) {
        try {
            log.info("Inserting entity CalleModel: {}.",  model);
            CalleModel calleModel = calleDAO.save(calleMapper.toEntity(model));
            calleModel.setCreado(Helper.getNow(""));
            calleModel.setCreador(usuarioService.obtenerUsuario().getObject());
            calleDAO.save(calleModel);
            String message = "The entity CalleModel with id: " + calleModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<CalleModel>(calleModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<CalleModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> actualizar(CalleModel model) {
        try {
            log.info("Updating entity CalleModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<CalleModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            CalleModel calleModel = calleDAO.save(model);
            String message = "The entity CalleModel with id: " + calleModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<CalleModel>(calleModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<CalleModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> reciclar(Long id) {
        log.info("Recycling entity CalleModel with: {}.", id);
        EntityMessenger<CalleModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity CalleModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
        entityMessenger.setObject(calleDAO.save(entityMessenger.getObject()));
        String message = "The entity CalleModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<CalleModel> borrar(Long id) {
        log.info("Deleting entity CalleModel with: {}.", id);
        EntityMessenger<CalleModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(calleDAO.save(entityMessenger.getObject()));
        String message = "The entity CalleModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<CalleModel> destruir(Long id) {
        log.info("Destroying entity CalleModel with: {}.", id);
        EntityMessenger<CalleModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity CalleModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        calleDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
