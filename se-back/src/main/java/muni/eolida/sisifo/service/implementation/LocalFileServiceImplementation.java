package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.model.LocalFileModel;
import muni.eolida.sisifo.model.enumerator.FiletypeEnum;
import muni.eolida.sisifo.repository.LocalFileRepository;
import muni.eolida.sisifo.service.LocalFileServiceInterface;
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
public class LocalFileServiceImplementation implements LocalFileServiceInterface {

    private final LocalFileRepository localFileRepository;
    private String resourcePath;

    @Value("${sisifo.app.resourcePath}")
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public EntityMessenger<LocalFileModel> findAllByFiletype(String filetypeEnum) {
        try {
            log.info("Searching for entities LocalFile with file type: {}.", filetypeEnum);
            List<LocalFileModel> localFileModelList = localFileRepository.findAllByFiletype(FiletypeEnum.valueOf(filetypeEnum));
            if (localFileModelList.isEmpty()) {
                String message = "No entities LocalFile with file type: " + filetypeEnum + " were found.";
                log.warn(message);
                return new EntityMessenger<LocalFileModel>(null, null, message, 202);
            } else {
                String message = localFileModelList.size() + "entities LocalFile were found.";
                log.info(message);
                return new EntityMessenger<LocalFileModel>(null, localFileModelList, message, 200);
            }
        } catch (Exception e) {
            String message = "An error occurred while trying to find the entities. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<LocalFileModel> saveLocalFile(byte[] file, String filename, String filetypeEnum, String description, String filesize) {
        try {
            log.info("Saving file " + filename + " in LocalFile.");
            String filepath = "";
            switch (FiletypeEnum.valueOf(filetypeEnum)) {
                case FILE_TYPE_AUDIO -> filepath = filepath + "/media/audio/";
                case FILE_TYPE_IMAGE -> filepath = filepath + "/media/image/";
                case FILE_TYPE_VIDEO -> filepath = filepath + "/media/video/";
                case FILE_TYPE_PDF -> filepath = filepath + "/media/pdf/";
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
            Files.write(Paths.get(resourcePath + filepath + filename), file);
            LocalFileModel localFileModel = new LocalFileModel();
            localFileModel.setFiletype(FiletypeEnum.valueOf(filetypeEnum));
            localFileModel.setFilename(filename);
            localFileModel.setFilesize(filesize);
            localFileModel.setDescription(description);
            localFileModel.setPath(filepath);
            EntityMessenger<LocalFileModel> localFileModelEntityMessenger = this.insert(localFileModel);
            if (localFileModelEntityMessenger.getStatusCode() == 201) {
                String message = "LocalFile " + localFileModelEntityMessenger.getObject().getFilename() + " was saved correctly.";
                log.info(message);
                localFileModelEntityMessenger.setMessage(message);
            }
            return localFileModelEntityMessenger;
        } catch (Exception e) {
            String message = "An error occurred while trying to save de LocalFile. Exception: " + e;
            log.error(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<LocalFileModel> findById(Long id) {
        log.info("Searching for entity LocalFile with id: {}.", id);
        Optional<LocalFileModel> object = localFileRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No entity LocalFile with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 202);
        } else {
            String message = "One entity LocalFile was found.";
            log.info(message);
            return new EntityMessenger<LocalFileModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<LocalFileModel> findAll() {
        log.info("Searching for all entities LocalFile.");
        List<LocalFileModel> list = localFileRepository.findAll();
        if (list.isEmpty()) {
            String message = "No entities LocalFile were found.";
            log.warn(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities LocalFile were found.";
            log.info(message);
            return new EntityMessenger<LocalFileModel>(null, list, message, 200);
        }
    }

    @Override
    public Long countAll() {
        Long count = localFileRepository.count();
        log.info("Table LocalFile possess {} entities.", count);
        return count;
    }

    @Override
    public Long countAllByFiletype(String filetypeEnum) {
        Long count = localFileRepository.countAllByFiletype(FiletypeEnum.valueOf(filetypeEnum));
        log.info("Table LocalFile possess {} entities of file type: " + filetypeEnum + ".", count);
        return count;
    }

    @Override
    public EntityMessenger<LocalFileModel> insert(LocalFileModel newLocalFile) {
        try {
            log.info("Inserting entity LocalFile: {}.",  newLocalFile);
            LocalFileModel localFileModel = localFileRepository.save(newLocalFile);
            String message = "The entity LocalFile with id: " + localFileModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<LocalFileModel>(localFileModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<LocalFileModel> update(LocalFileModel updateLocalFile) {
        try {
            log.info("Updating entity LocalFile: {}.",  updateLocalFile);
            if (updateLocalFile.getId() != null) {
                EntityMessenger<LocalFileModel> existant = this.findById(updateLocalFile.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            LocalFileModel localFileModel = localFileRepository.save(updateLocalFile);
            String message = "The entity LocalFile with id: " + localFileModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<LocalFileModel>(localFileModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<LocalFileModel> destroy(Long id) {
        try {
            log.info("Destroying entity LocalFile with: {}.", id);
            EntityMessenger<LocalFileModel> entityMessenger = this.findById(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            Path fileToDeletePath = Paths.get(resourcePath + entityMessenger.getObject().getPath() + entityMessenger.getObject().getFilename());
            Files.delete(fileToDeletePath);
            localFileRepository.delete(entityMessenger.getObject());
            String message = "The entity was destroyed. The LocalFile " + entityMessenger.getObject().getFilename() + " was removed correctly.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "An error occurred while trying to destroy the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<LocalFileModel>(null, null, message, 204);
        }

    }
}
