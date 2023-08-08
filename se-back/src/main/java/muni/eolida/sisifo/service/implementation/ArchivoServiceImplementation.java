package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import muni.eolida.sisifo.repository.ArchivoRepository;
import muni.eolida.sisifo.service.ArchivoServiceInterface;
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
public class ArchivoServiceImplementation implements ArchivoServiceInterface {

    private final ArchivoRepository archivoRepository;
    private String resourcePath;

    @Value("${sisifo.app.resourcePath}")
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodosPorTipoArchivo(String filetypeEnum) {
        try {
            log.info(" Buscando entidades Archivo de tipo: {}.", filetypeEnum);
            List<ArchivoModel> archivoModelList = archivoRepository.buscarTodosPorTipo(TipoArchivoEnum.valueOf(filetypeEnum));
            if (archivoModelList.isEmpty()) {
                String message = "No existen entidades Archivo de tipo: " + filetypeEnum + ".";
                log.warn(message);
                return new EntityMessenger<ArchivoModel>(null, null, message, 202);
            } else {
                String message = archivoModelList.size() + "entidades Archivo fueron encontradas.";
                log.info(message);
                return new EntityMessenger<ArchivoModel>(null, archivoModelList, message, 200);
            }
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar encontrar la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> guardarArchivo(byte[] file, String filename, String filetypeEnum, String description, String filesize) {
        try {
            log.info("Guardando archivo " + filename + " en Archivo.");
            String filepath = "";
            switch (TipoArchivoEnum.valueOf(filetypeEnum)) {
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
                log.info(" El directorio: {} fue creado.", path);
            } else {
                log.info(" El directorio: {} ya fue creado.", path);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            filepath = filepath + Helper.localDateTimeToString(localDateTime, "yyyy") + "/";
            path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info(" El directorio: {} fue creado.", path);
            } else {
                log.info(" El directorio: {} ya fue creado.", path);
            }
            filepath = filepath + Helper.localDateTimeToString(localDateTime, "M") + "/" ;
            path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info(" El directorio: {} fue creado.", path);
            } else {
                log.info(" El directorio: {} ya fue creado.", path);
            }
            filepath = filepath + Helper.localDateTimeToString(localDateTime, "d") + "/" ;
            path = Paths.get(resourcePath + filepath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info(" El directorio: {} fue creado.", path);
            } else {
                log.info(" El directorio: {} ya fue creado.", path);
            }
            Files.write(Paths.get(resourcePath + filepath + filename), file);
            ArchivoModel archivoModel = new ArchivoModel();
            archivoModel.setTipo(TipoArchivoEnum.valueOf(filetypeEnum));
            archivoModel.setNombre(filename);
            archivoModel.setTamanio(filesize);
            archivoModel.setDescripcion(description);
            archivoModel.setPath(filepath);
            EntityMessenger<ArchivoModel> ArchivoModelEntityMessenger = this.insertar(archivoModel);
            if (ArchivoModelEntityMessenger.getStatusCode() == 201) {
                String message = "El Archivo " + ArchivoModelEntityMessenger.getObject().getNombre() + " fue guardado correctamente.";
                log.info(message);
                ArchivoModelEntityMessenger.setMessage(message);
            }
            return ArchivoModelEntityMessenger;
        } catch (Exception e) {
            String message = "Ocurrio un error al intenar guardar la entidad. Excepcion: " + e;
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public Long contarTodosPorTipoArchivo(String filetypeEnum) {
        Long count = archivoRepository.contarTodosPorTipo(TipoArchivoEnum.valueOf(filetypeEnum));
        log.info("Existen {} entidades Archivo de tipo: " + filetypeEnum + ".", count);
        return count;
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Archivo con id: {}.", id);
        Optional<ArchivoModel> object = archivoRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No existe la entidad Archivo con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "Una entidad Archivo encontrada.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodos() {
        log.info("Buscando todas las entidades Archivo.");
        List<ArchivoModel> list = archivoRepository.findAll();
        if (list.isEmpty()) {
            String message = "No existen entidades Archivo.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades Archivo fueron encontradas.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = archivoRepository.count();
        log.info("Existen {} entidades Archivo.", count);
        return count;
    }

    @Override
    public EntityMessenger<ArchivoModel> insertar(ArchivoModel newArchivo) {
        try {
            log.info("Insertando entidad Archivo: {}.",  newArchivo);
            ArchivoModel archivoModel = archivoRepository.save(newArchivo);
            String message = "La entidad Archivo con id: " + archivoModel.getId() + " fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(archivoModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> actualizar(ArchivoModel actualizarArchivo) {
        try {
            log.info("Actualizando entidad Archivo: {}.",  actualizarArchivo);
            if (actualizarArchivo.getId() != null) {
                EntityMessenger<ArchivoModel> existant = this.buscarPorId(actualizarArchivo.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            ArchivoModel archivoModel = archivoRepository.save(actualizarArchivo);
            String message = "La entidad Archivo con id: " + archivoModel.getId() + "fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(archivoModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Archivo con id: {}.", id);
            EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorId(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            Path fileToDeletePath = Paths.get(resourcePath + entityMessenger.getObject().getPath() + entityMessenger.getObject().getNombre());
            Files.delete(fileToDeletePath);
            archivoRepository.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida. El Archivo " + entityMessenger.getObject().getNombre() + " fue eliminado correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Un error ocurrio al intenar destruir la entidad. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }

    }
}
