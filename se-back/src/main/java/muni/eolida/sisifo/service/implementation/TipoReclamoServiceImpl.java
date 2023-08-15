package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.TipoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
import muni.eolida.sisifo.service.TipoReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TipoReclamoServiceImpl implements TipoReclamoService {

    private final TipoReclamoDAO tipoReclamoDAO;
    private final TipoReclamoMapper tipoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodosPorNombre(String nombre) {
        log.info("Buscando entidades TipoReclamo con nombre: {}.", nombre);
        List<TipoReclamoModel> list = tipoReclamoDAO.findAllByNombreIgnoreCaseContainingAndBorradoIsNull(nombre);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades TipoReclamo con nombre " + nombre + ".";
            log.warn(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades TipoReclamo con nombre " + nombre + ".";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodosPorNombreConBorrados(String nombre) {
        log.info("Searching for entities TipoReclamo with name: {}, included deleted ones.", nombre);
        List<TipoReclamoModel> list = tipoReclamoDAO.findAllByNombreIgnoreCaseContaining(nombre);
        if (list.isEmpty()) {
            String message = "No entities TipoReclamo was found with name " + nombre + ", included deleted ones.";
            log.warn(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities TipoReclamo was found with name " + nombre + ", included deleted ones.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarPorId(Long id) {
        log.info("Buscando entidades TipoReclamo con id: {}.", id);
        Optional<TipoReclamoModel> object = tipoReclamoDAO.findByIdAndBorradoIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontraron entidades TipoReclamo con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad TipoReclamo.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity TipoReclamoModel with id: {}, included deleted ones.", id);
        Optional<TipoReclamoModel> object = tipoReclamoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity TipoReclamoModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 202);
        } else {
            String message = "One entity TipoReclamoModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodos() {
        log.info("Buscando todas las entidades TipoReclamo.");
        List<TipoReclamoModel> list = tipoReclamoDAO.findAllByBorradoIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades TipoReclamo.";
            log.warn(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades TipoReclamo fueron encontradas.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodosConBorrados() {
        log.info("Searching for all entities TipoReclamoModel, included deleted ones.");
        List<TipoReclamoModel> list = tipoReclamoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities TipoReclamoModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities TipoReclamoModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = tipoReclamoDAO.countAllByBorradoIsNull();
        log.info("Table TipoReclamoModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = tipoReclamoDAO.count();
        log.info("Table TipoReclamoModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<TipoReclamoModel> insertar(TipoReclamoCreation model) {
        try {
            log.info("Inserting entity TipoReclamoModel: {}.",  model);
            TipoReclamoModel tipoReclamoModel = tipoReclamoDAO.save(tipoReclamoMapper.toEntity(model));
            tipoReclamoModel.setCreado(Helper.getNow(""));
            tipoReclamoModel.setCreador(usuarioService.obtenerUsuario().getObject());
            tipoReclamoDAO.save(tipoReclamoModel);
            String message = "The entity TipoReclamoModel with id: " + tipoReclamoModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(tipoReclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> actualizar(TipoReclamoModel model) {
        try {
            log.info("Updating entity TipoReclamoModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<TipoReclamoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            TipoReclamoModel tipoReclamoModel = tipoReclamoDAO.save(model);
            String message = "The entity TipoReclamoModel with id: " + tipoReclamoModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<TipoReclamoModel>(tipoReclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<TipoReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> reciclar(Long id) {
        log.info("Recycling entity TipoReclamoModel with: {}.", id);
        EntityMessenger<TipoReclamoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity TipoReclamoModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
        entityMessenger.setObject(tipoReclamoDAO.save(entityMessenger.getObject()));
        String message = "The entity TipoReclamoModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<TipoReclamoModel> borrar(Long id) {
        log.info("Deleting entity TipoReclamoModel with: {}.", id);
        EntityMessenger<TipoReclamoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(tipoReclamoDAO.save(entityMessenger.getObject()));
        String message = "The entity TipoReclamoModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<TipoReclamoModel> destruir(Long id) {
        log.info("Destroying entity TipoReclamoModel with: {}.", id);
        EntityMessenger<TipoReclamoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity TipoReclamoModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        tipoReclamoDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
