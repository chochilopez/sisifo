package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.repository.ArchivoDAO;
import muni.eolida.sisifo.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArchivoServiceImpl implements ArchivoService {
    @Value("${sisifo.app.resourcePath}")
    private String resourcePath;
    private final ArchivoDAO archivoDAO;
    private final ArchivoMapper archivoMapper;
    private final UsuarioServiceImpl usuarioService;

    // FIX Chequear que las imagenes son guardadas y que se exporta el path y que se puede mostrar
    @Override
    public EntityMessenger<ArchivoModel> guardarArchivo(byte[] bytes, String usuario, String uuid) {
        try {
            log.info("Gurdando el archivo " + usuario + "/" + uuid + ".");
            Path path = Paths.get(resourcePath + "/" + usuario);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("El directorio: {} fue creado correctamente.", path);
            } else {
                log.info("No fue necesario crear el directorio: {}, ya se encontraba creado.", path);
            }
            Files.write(Paths.get(resourcePath + "/" + usuario + "/" + uuid), bytes);
            ArchivoCreation archivoCreation = new ArchivoCreation();
            archivoCreation.setPath(resourcePath + "/" + usuario);
            archivoCreation.setNombre(uuid);
            EntityMessenger<ArchivoModel> archivoModelEntityMessenger = this.insertar(archivoCreation);
            if (archivoModelEntityMessenger.getStatusCode() == 201) {
                String message = "El archivo " + archivoModelEntityMessenger.getObject().getNombre() + " fue guardado correctamente.";
                log.info(message);
                archivoModelEntityMessenger.setMessage(message);
            }
            return archivoModelEntityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar guardar el archivo. Excepción: " + e;
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Archivo con id: {}.", id);
        Optional<ArchivoModel> object = archivoDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Archivo con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Archivo.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Archivo con id: {}, incluidas las eliminadas.", id);
        Optional<ArchivoModel> object = archivoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Archivo con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Archivo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodas() {
        log.info("Buscando todas las entidades Archivo.");
        List<ArchivoModel> list = archivoDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Archivo.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Archivo.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Archivo, incluidas las eliminadas.");
        List<ArchivoModel> list = archivoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Archivo, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Archivo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = archivoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Archivo.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = archivoDAO.count();
        log.info("Existen {} entidades Archivo, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<ArchivoModel> insertar(ArchivoCreation model) {
        try {
            log.info("Insertando la entidad Archivo: {}.",  model);
            ArchivoModel archivoModel = archivoDAO.save(archivoMapper.toEntity(model));
            archivoModel.setCreada(Helper.getNow(""));
            archivoModel.setCreador(usuarioService.obtenerUsuario().getObject());
            archivoDAO.save(archivoModel);
            String message = "La entidad Archivo con id: " + archivoModel.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(archivoModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Archivo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> actualizar(ArchivoModel model) {
        try {
            log.info("Actualizando la entidad Archivo: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<ArchivoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            ArchivoModel archivoModel = archivoDAO.save(model);
            String message = "La entidad Archivo con id: " + archivoModel.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<ArchivoModel>(archivoModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Archivo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ArchivoModel> reciclar(Long id) {
        log.info("Reciclando la entidad Archivo con id: {}.", id);
        EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Archivo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(archivoDAO.save(entityMessenger.getObject()));
        String message = "La entidad Archivo con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ArchivoModel> eliminar(Long id) {
        log.info("Borrando la entidad Archivo con id: {}.", id);
        EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(archivoDAO.save(entityMessenger.getObject()));
        String message = "La entidad Archivo con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ArchivoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Archivo con id: {}.", id);
            EntityMessenger<ArchivoModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Archivo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            Path fileToDeletePath = Paths.get(resourcePath + entityMessenger.getObject().getPath() + entityMessenger.getObject().getNombre());
            Files.delete(fileToDeletePath);
            archivoDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida y el archivo " + entityMessenger.getObject().getNombre() + " fue eliminado correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Archivo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<ArchivoModel>(null, null, message, 204);
        }
    }
}
