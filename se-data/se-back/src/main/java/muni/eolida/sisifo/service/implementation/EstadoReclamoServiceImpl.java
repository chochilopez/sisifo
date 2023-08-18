package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.service.EstadoReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EstadoReclamoServiceImpl implements EstadoReclamoService {

    private final EstadoReclamoDAO estadoReclamoDAO;
    private final EstadoReclamoMapper estadoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorSeguimientoId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorSeguimientoIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamo(TipoEstadoReclamoEnum estadoReclamo) {
        return null;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamoConBorrados(TipoEstadoReclamoEnum estadoReclamo) {
        return null;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorId(Long id) {
        log.info("Buscando entidades EstadoReclamo con id: {}.", id);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findByIdAndBorradoIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontraron entidades EstadoReclamo con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity EstadoReclamoModel with id: {}, included deleted ones.", id);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity EstadoReclamoModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "One entity EstadoReclamoModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarTodos() {
        log.info("Buscando todas las entidades EstadoReclamo.");
        List<EstadoReclamoModel> list = estadoReclamoDAO.findAllByBorradoIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades EstadoReclamo.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades EstadoReclamo fueron encontradas.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarTodosConBorrados() {
        log.info("Searching for all entities EstadoReclamoModel, included deleted ones.");
        List<EstadoReclamoModel> list = estadoReclamoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities EstadoReclamoModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities EstadoReclamoModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = estadoReclamoDAO.countAllByBorradoIsNull();
        log.info("Table EstadoReclamoModel possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = estadoReclamoDAO.count();
        log.info("Table EstadoReclamoModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> insertar(EstadoReclamoCreation model) {
        try {
            log.info("Inserting entity EstadoReclamoModel: {}.",  model);
            EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.save(estadoReclamoMapper.toEntity(model));
            estadoReclamoModel.setCreado(Helper.getNow(""));
            estadoReclamoModel.setCreador(usuarioService.obtenerUsuario().getObject());
            estadoReclamoDAO.save(estadoReclamoModel);
            String message = "The entity EstadoReclamoModel with id: " + estadoReclamoModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(estadoReclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> actualizar(EstadoReclamoModel model) {
        try {
            log.info("Updating entity EstadoReclamoModel: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<EstadoReclamoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.save(model);
            String message = "The entity EstadoReclamoModel with id: " + estadoReclamoModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(estadoReclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> reciclar(Long id) {
        log.info("Recycling entity EstadoReclamoModel with: {}.", id);
        EntityMessenger<EstadoReclamoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity EstadoReclamoModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
        entityMessenger.setObject(estadoReclamoDAO.save(entityMessenger.getObject()));
        String message = "The entity EstadoReclamoModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> borrar(Long id) {
        log.info("Deleting entity EstadoReclamoModel with: {}.", id);
        EntityMessenger<EstadoReclamoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(estadoReclamoDAO.save(entityMessenger.getObject()));
        String message = "The entity EstadoReclamoModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> destruir(Long id) {
        log.info("Destroying entity EstadoReclamoModel with: {}.", id);
        EntityMessenger<EstadoReclamoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity EstadoReclamoModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        estadoReclamoDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
