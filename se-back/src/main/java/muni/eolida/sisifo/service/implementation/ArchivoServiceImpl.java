package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.repository.ArchivoDAO;
import muni.eolida.sisifo.service.ArchivoService;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomObjectNotDeletedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {
    private final ArchivoDAO archivoDAO;
    private final ArchivoMapper archivoMapper;
    private final UsuarioServiceImpl usuarioService;
    @Value("${sisifo.app.resourcePath}")
    private String resourcePath;

    @Override
    public ArchivoModel guardarArchivo(byte[] bytes) throws IOException {
        String usuario = usuarioService.obtenerUsuario().getNombre().trim();
        String uuid = java.util.UUID.randomUUID().toString() + ".jpg";
        log.info("Gurdando el archivo {}/{}.", usuario, uuid);
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
        log.info("El archivo fue guardado correctamente");
        return this.guardar(archivoCreation);
    }

    @Override
    public ArchivoModel buscarPorId(Long id) {
        log.info("Buscando la entidad Archivo con id: {}.", id);
        ArchivoModel archivoModel = archivoDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Archivo con id: " + id + "."));
        log.info("Se encontro una entidad Archivo con id: {}.", id);
        return archivoModel;
    }

    @Override
    public ArchivoModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Archivo con id: {}, incluidas las eliminadas.", id);
        ArchivoModel archivoModel = archivoDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Archivo con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Archivo con id: {} , incluidas las eliminadas.", id);
        return archivoModel;
    }

    @Override
    public List<ArchivoModel> buscarTodas() {
        log.info("Buscando todas las entidades Archivo.");
        List<ArchivoModel> listado = archivoDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Archivo.");
        return listado;
    }

    @Override
    public List<ArchivoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Archivo, incluidas las eliminadas.");
        List<ArchivoModel> listado = archivoDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Archivo, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = archivoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Archivo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = archivoDAO.count();
        log.info("Existen {} entidades Archivo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public ArchivoModel guardar(ArchivoCreation creation) {
        log.info("Insertando la entidad Archivo: {}.",  creation);
        ArchivoModel archivoModel = archivoDAO.save(archivoMapper.toEntity(creation));
        if (creation.getId() != null) {
            archivoModel.setCreada(Helper.getNow(""));
            archivoModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persisitio correctamente la nueva entidad.");
        } else {
            archivoModel.setModificada(Helper.getNow(""));
            archivoModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persisitio correctamente la entidad.");
        }
        return archivoDAO.save(archivoModel);
    }

    @Override
    public ArchivoModel eliminar(Long id) {
        log.info("Eliminando la entidad Archivo con id: {}.", id);
        ArchivoModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Archivo con id: {}, fue eliminada correctamente.", id);
        return archivoDAO.save(objeto);
    }

    @Override
    public ArchivoModel reciclar(Long id) {
            log.info("Reciclando la entidad Archivo con id: {}.", id);
            ArchivoModel objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEliminada() == null) {
                log.warn("La entidad Archivo con id: {}, no se encuentra eliminada, por lo tanto no es necesario reciclarla.", id);
                throw new CustomObjectNotDeletedException("No se puede reciclar la entidad.");
            }
            objeto.setEliminada(null);
            objeto.setEliminador(null);
            log.info("La entidad Archivo con id: {}, fue reciclada correctamente.", id);
            return archivoDAO.save(objeto);
    }

    @Override
    public void destruir(Long id) throws IOException {
        log.info("Destruyendo la entidad Archivo con id: {}.", id);
        ArchivoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Archivo con id: {}, no se encuentra eliminada, por lo tanto no puede ser destruida.", id);
            throw new CustomObjectNotDeletedException("No se puede destruir la entidad.");
        }
        Path fileToDeletePath = Paths.get(objeto.getPath() + "/" + objeto.getNombre());
        Files.delete(fileToDeletePath);
        archivoDAO.delete(objeto);
        log.info("La entidad fue destruida y el archivo {} fue eliminado correctamente.", id);
    }
}
