package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import muni.eolida.sisifo.repository.ArchivoDAO;
import muni.eolida.sisifo.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoDAO archivoDAO;
    private String resourcePath;
    private final ArchivoMapper archivoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Value("${sisifo.app.resourcePath}")
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public EntityMessenger<ArchivoModel> guardarArchivo(byte[] bytes, String usuario, String uuid) {
        try {
            log.info("Saving file " + usuario + "/" + uuid + " in Archivo.");
            Path path = Paths.get(resourcePath + "/" + usuario);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("The directory: {} was created.", path);
            } else {
                log.info("The directory: {} was already created.", path);
            }
            Files.write(Paths.get(resourcePath + "/" + usuario + "/" + uuid), bytes);
            ArchivoCreation archivoCreation = new ArchivoCreation();
            archivoCreation.setPath(resourcePath + "/" + usuario);
            archivoCreation.setNombre(uuid);
            EntityMessenger<ArchivoModel> archivoModelEntityMessenger = this.insertar(archivoCreation);
            if (archivoModelEntityMessenger.getStatusCode() == 201) {
                String message = "Archivo " + archivoModelEntityMessenger.getObject().getNombre() + " was saved correctly.";
                log.info(message);
                archivoModelEntityMessenger.setMessage(message);
            }
            return archivoModelEntityMessenger;
        } catch (Exception e) {
            String message = "An error occurred while trying to save de Archivo. Exception: " + e;
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarPorId(Long id) {
        log.info("Searching for entity Archivo with id: {}.", id);
        Optional<ArchivoModel> object = archivoDAO.findByIdAndBorradoIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity Archivo with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "One entity Archivo was found.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity Archivo with id: {}, included deleted ones.", id);
        Optional<ArchivoModel> object = archivoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity Archivo with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "One entity Archivo was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodos() {
        log.info("Searching for all entities Archivo.");
        List<ArchivoModel> list = archivoDAO.findAllByBorradoIsNull();
        if (list.isEmpty()) {
            String message = "No entities Archivo were found.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Archivo were found.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodosConBorrados() {
        log.info("Searching for all entities Archivo, included deleted ones.");
        List<ArchivoModel> list = archivoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No entities Archivo were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Archivo were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = archivoDAO.countAllByBorradoIsNull();
        log.info("Table Archivo possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = archivoDAO.count();
        log.info("Table Archivo possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<ArchivoModel> insertar(ArchivoCreation model) {
        try {
            log.info("Inserting entity Archivo: {}.",  model);
            ArchivoModel archivoModel = archivoDAO.save(archivoMapper.toEntity(model));
            archivoModel.setCreado(Helper.getNow(""));
            archivoModel.setCreador(usuarioService.obtenerUsuario().getObject());
            archivoDAO.save(archivoModel);
            String message = "The entity Archivo with id: " + archivoModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(archivoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> actualizar(ArchivoModel model) {
        try {
            log.info("Updating entity Archivo: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<ArchivoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            ArchivoModel archivoModel = archivoDAO.save(model);
            String message = "The entity Archivo with id: " + archivoModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(archivoModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> reciclar(Long id) {
        log.info("Recycling entity Archivo with: {}.", id);
        EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBorrado() == null) {
            String message = "The entity Archivo with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(null);
        entityMessenger.getObject().setBorrador(null);
        entityMessenger.setObject(archivoDAO.save(entityMessenger.getObject()));
        String message = "The entity Archivo with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ArchivoModel> borrar(Long id) {
        log.info("Deleting entity Archivo with: {}.", id);
        EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBorrado(Helper.getNow(""));
        entityMessenger.getObject().setBorrador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(archivoDAO.save(entityMessenger.getObject()));
        String message = "The entity Archivo with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ArchivoModel> destruir(Long id) {
        try {
            log.info("Destroying entity Archivo with: {}.", id);
            EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorIdConBorrados(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getBorrado() == null) {
                String message = "The entity Archivo with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            Path fileToDeletePath = Paths.get(resourcePath + entityMessenger.getObject().getPath() + entityMessenger.getObject().getNombre());
            Files.delete(fileToDeletePath);
            archivoDAO.delete(entityMessenger.getObject());
            String message = "The entity was destroyed. The Archivo " + entityMessenger.getObject().getNombre() + " was removed correctly.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "An error occurred while trying to destroy the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }

    }
}
