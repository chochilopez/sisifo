package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.UsuarioModel;
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
    public EntityMessenger<ReclamoModel> buscarMisReclamos() {
        EntityMessenger<UsuarioModel> usuarioModel = usuarioService.obtenerUsuario();
        return this.buscarPorCreador(usuarioModel.getObject().getId());
    }

    @Override
    public List<ReclamoModel> findAllByCreadorId(Long id) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCreadorIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCaseAndBorradoIsNull(String nombre) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCalleId(Long id) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCalleIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCalleCalleContainingIgnoreCase(String calle) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByCalleCalleContainingIgnoreCaseAndBorradoIsNull(String calle) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByTipoReclamoId(Long id) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByTipoReclamoIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCase(String tipo) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCaseAndBorradoIsNull(Long tipo) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ReclamoModel> findAllByDescripcionContainingIgnoreCaseAndBorradoIsNull(Long descripcion) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreador(Long id) {
        log.info("Buscando entidades Reclamo con id creador: {}.", id);
        List<ReclamoModel> list = reclamoDAO.findAllByCreadorIdAndBorradoIsNull(id);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Reclamo con id creador " + id + ".";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Reclamo con id creador " + id + ".";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreadorConBorrados(Long id) {
        log.info("Searching for entities Reclamo with id creador: {}, included deleted ones.", id);
        List<ReclamoModel> list = reclamoDAO.findAllByCreadorId(id);
        if (list.isEmpty()) {
            String message = "No entities Reclamo was found with id creador " + id + ", included deleted ones.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Reclamo was found with id creador " + id + ", included deleted ones.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreadorId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreadorIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorTipoReclamoId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorTipoReclamoIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorBarrioId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorBarrioIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCalleId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCalleIdAndBorradoIsNull(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorByCreadorNombre(String nombre) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorCreadorNombreAndBorradoIsNull(String nombre) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorCalleCalle(String calle) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorCalleCalleAndBorradoIsNull(String calle) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorTipoReclamoTipo(String tipo) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorTipoReclamoTipoAndBorradoIsNull(Long tipo) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorDescripcion(String descripcion) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorDescripcionAndBorradoIsNull(Long descripcion) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorBarrioCalle(String barrio) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodosPorBarrioCalleAndBorradoIsNull(String barrio) {
        return null;
    }

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
            log.info("Insertando entidad Reclamo: {}.",  model);
            ReclamoModel reclamoModel = reclamoDAO.save(reclamoMapper.toEntity(model));
            reclamoModel.setCreado(Helper.getNow(""));
            reclamoModel.setCreador(usuarioService.obtenerUsuario().getObject());
            reclamoDAO.save(reclamoModel);
            String message = "La entidad Reclamo con id: " + reclamoModel.getId() + " fue creada correctamente.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(reclamoModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio . Exception: " + e + ".";
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