package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
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
public class ArchivoServiceImplementation implements ArchivoService {

    private final ArchivoDAO archivoDAO;
    private String resourcePath;

    @Value("${sisifo.app.resourcePath}")
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodosPorTipoArchivo(String tipo) {
        try {
            log.info("Searching for entities Archivo with file type: {}.", tipo);
            List<ArchivoModel> archivoModelList = archivoDAO.findAllByTipoAndBajaIsNull(TipoArchivoEnum.valueOf(tipo));
            if (archivoModelList.isEmpty()) {
                String message = "No entities Archivo with file type: " + tipo + " were found.";
                log.warn(message);
                return new EntityMessenger<ArchivoModel>(null, null, message, 202);
            } else {
                String message = archivoModelList.size() + "entities Archivo were found.";
                log.info(message);
                return new EntityMessenger<ArchivoModel>(null, archivoModelList, message, 200);
            }
        } catch (Exception e) {
            String message = "An error occurred while trying to find the entities. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodosPorTipoArchivoConBorrados(String tipo) {
        try {
            log.info("Searching for entities Archivo with file type: {}, included deleted ones.", tipo);
            List<ArchivoModel> archivoModelList = archivoDAO.findAllByTipo(TipoArchivoEnum.valueOf(tipo));
            if (archivoModelList.isEmpty()) {
                String message = "No entities Archivo with file type: " + tipo + " were found, included deleted ones.";
                log.warn(message);
                return new EntityMessenger<ArchivoModel>(null, null, message, 202);
            } else {
                String message = archivoModelList.size() + "entities Archivo were found, included deleted ones.";
                log.info(message);
                return new EntityMessenger<ArchivoModel>(null, archivoModelList, message, 200);
            }
        } catch (Exception e) {
            String message = "An error occurred while trying to find the entities. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> guardarArchivo(byte[] bytes, String nombre, String tipo, String descripcion, String tamanio) {
        try {
            log.info("Saving file " + nombre + " in Archivo.");
            String filepath = "";
            switch (TipoArchivoEnum.valueOf(tipo)) {
                case TIPO_ARCHIVO_AUDIO -> filepath = filepath + "/media/audio/";
                case TIPO_ARCHIVO_IMAGEN -> filepath = filepath + "/media/image/";
                case TIPO_ARCHIVO_VIDEO -> filepath = filepath + "/media/video/";
                case TIPO_ARCHIVO_DOCUMENTO -> filepath = filepath + "/media/pdf/";
                default -> {
                }
            }
            Path path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("The directory: {} was created.", path);
            } else {
                log.info("The directory: {} was already created.", path);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            filepath = filepath + Helper.localDateTimeToString(localDateTime, "yyyy") + "/";
            path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("The directory: {} was created.", path);
            } else {
                log.info("The directory: {} was already created.", path);
            }
            filepath = filepath + Helper.localDateTimeToString(localDateTime, "M") + "/" ;
            path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("The directory: {} was created.", path);
            } else {
                log.info("The directory: {} was already created.", path);
            }
            filepath = filepath + Helper.localDateTimeToString(localDateTime, "d") + "/" ;
            path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("The directory: {} was created.", path);
            } else {
                log.info("The directory: {} was already created.", path);
            }
            Files.write(Paths.get(resourcePath + filepath + nombre), bytes);
            ArchivoModel archivoModel = new ArchivoModel();
            archivoModel.setTipo(TipoArchivoEnum.valueOf(tipo));
            archivoModel.setNombre(nombre);
            archivoModel.setTamanio(tamanio);
            archivoModel.setDescripcion(descripcion);
            archivoModel.setPath(filepath);
            EntityMessenger<ArchivoModel> archivoModelEntityMessenger = this.insertar(archivoModel);
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
        Optional<ArchivoModel> object = archivoDAO.findByIdAndBajaIsNull(id);
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
        List<ArchivoModel> list = archivoDAO.findAllByBajaIsNull();
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
        Long count = archivoDAO.countAllByBajaIsNull();
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
    public Long contarTodosPorTipoArchivo(String tipo) {
        Long count = archivoDAO.countAllByTipoAndBajaIsNull(TipoArchivoEnum.valueOf(tipo));
        log.info("Table Archivo possess {} entities of file type: " + tipo + ".", count);
        return count;
    }

    @Override
    public Long contarTodosPorTipoArchivoConBorrados(String tipo) {
        Long count = archivoDAO.countAllByTipo(TipoArchivoEnum.valueOf(tipo));
        log.info("Table Archivo possess {} entities of file type: " + tipo + ", included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<ArchivoModel> insertar(ArchivoModel model) {
        try {
            log.info("Inserting entity Archivo: {}.",  model);
            model.setAlta(Helper.getNow(""));
            ArchivoModel archivoModel = archivoDAO.save(model);
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
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity Archivo with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(null);
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
        entityMessenger.getObject().setBaja(Helper.getNow(""));
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
            if (entityMessenger.getObject().getBaja() == null) {
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
