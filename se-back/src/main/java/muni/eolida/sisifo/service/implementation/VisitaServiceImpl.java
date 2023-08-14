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
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<VisitaModel> buscarTodosPorIp(String ip) {
        log.info("Searching for entities Visit with ip: {}.", ip);
        List<VisitaModel> list = visitaDAO.findAllByIpContainingAndBorradoIsNull(ip);
        if (list.isEmpty()) {
            String message = "No entities Visit was found with ip adrress " + ip + ".";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit was found with ip adrress " + ip + ".";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarTodosPorIpConBorrados(String ip) {
        log.info("Searching for entities Visit with ip: {}, included deleted ones.", ip);
        List<VisitaModel> list = visitaDAO.findAllByIpContaining(ip);
        if (list.isEmpty()) {
            String message = "No entities Visit was found with ip adrress " + ip + ", included deleted ones.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit was found with ip adrress " + ip + ", included deleted ones.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarLosPrimerosN(int number) {
        log.info("Searching for top " + number + " entities in Visit.");
        List<VisitaModel> list = visitaDAO.findTopNAndBorradoIsNull(number);
        if (list.isEmpty()) {
            String message = "No entities Visit was found.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit was found.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarLosPrimerosNConBorrados(int number) {
        log.info("Searching for top " + number + " entities in Visit, included deleted ones.");
        List<VisitaModel> list = visitaDAO.findTopN(number);
        if (list.isEmpty()) {
            String message = "No entities Visit was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarPorId(Long id) {
        log.info("Searching for entity Visit with id: {}.", id);
        Optional<VisitaModel> object = visitaDAO.findByIdAndBorradoIsNull(id);
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
        List<VisitaModel> list = visitaDAO.findAllByBorradoIsNull();
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
        Long count = visitaDAO.countAllByBorradoIsNull();
        log.info("Table Visit possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = visitaDAO.count();
        log.info("Table Visit possess {} entities, included deleted ones.", count);
        return count;
    }

    //TODO hacer request a pagina, ademas realizar ingreso de usuario a la visita
    @Override
    public EntityMessenger<VisitaModel> insertar(VisitaCreation model) {
        try {
            log.info("Inserting entity Visit: {}.",  model);
            VisitaModel visitaModel = visitaDAO.save(visitaMapper.toEntity(model));
            visitaModel.setCreado(Helper.getNow(""));
            visitaModel.setCreador(usuarioService.obtenerUsuario().getObject());
            visitaDAO.save(visitaModel);
            String message = "The entity Visit with id: " + visitaModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(visitaModel, null, message, 201);
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
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            VisitaModel visitaModel = visitaDAO.save(model);
            String message = "The entity Visit with id: " + visitaModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(visitaModel, null, message, 201);
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
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity Visit with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
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
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
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
        if (entityMessenger.getObject().getBorrado() == null) {
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
