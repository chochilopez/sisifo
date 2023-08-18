package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.ArchivoMapper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.ArchivoDAO;
import muni.eolida.sisifo.service.ArchivoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArchivoServiceImpl implements ArchivoService {
    @Value("${sisifo.app.resourcePath}")
    private String resourcePath;
    @Autowired
    private ArchivoDAO archivoDAO;
    @Autowired
    private ArchivoMapper archivoMapper;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public EntidadMensaje<ArchivoModel> guardarArchivo(byte[] bytes) {
        try {
            EntidadMensaje<UsuarioModel> usuario = usuarioService.obtenerUsuario();
            String uuid = java.util.UUID.randomUUID().toString();
            log.info("Gurdando el archivo " + usuario.getObjeto().getNombre() + "/" + uuid + ".");
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
            EntidadMensaje<ArchivoModel> archivoModelEntidadMensaje = this.insertar(archivoCreation);
            if (archivoModelEntidadMensaje.getEstado() == 201) {
                String mensaje = "El archivo " + archivoModelEntidadMensaje.getObjeto().getNombre() + " fue guardado correctamente.";
                log.info(mensaje);
                archivoModelEntidadMensaje.setMensaje(mensaje);
            }
            return archivoModelEntidadMensaje;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar guardar el archivo. Excepción: " + e;
            log.error(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<ArchivoModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Archivo con id: {}.", id);
        Optional<ArchivoModel> objeto = archivoDAO.findByIdAndEliminadaIsNull(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Archivo con id: " + id + ".";
            log.warn(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Archivo.";
            log.info(mensaje);
            return new EntidadMensaje<ArchivoModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<ArchivoModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Archivo con id: {}, incluidas las eliminadas.", id);
        Optional<ArchivoModel> objeto = archivoDAO.findById(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Archivo con id: " + id + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Archivo, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<ArchivoModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<ArchivoModel> buscarTodas() {
        log.info("Buscando todas las entidades Archivo.");
        List<ArchivoModel> listado = archivoDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Archivo.";
            log.warn(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Archivo.";
            log.info(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<ArchivoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Archivo, incluidas las eliminadas.");
        List<ArchivoModel> listado = archivoDAO.findAll();
        if (listado.isEmpty()) {
            String mensaje = "No se encontrarón entidades Archivo, incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Archivo, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, listado, mensaje, 200);
        }
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
    public EntidadMensaje<ArchivoModel> insertar(ArchivoCreation model) {
        try {
            log.info("Insertando la entidad Archivo: {}.",  model);
            ArchivoModel archivoModel = archivoDAO.save(archivoMapper.toEntity(model));
            archivoModel.setCreada(Ayudador.getAhora(""));
            archivoModel.setCreador(usuarioService.obtenerUsuario().getObjeto());
            archivoDAO.save(archivoModel);
            String mensaje = "La entidad Archivo con id: " + archivoModel.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<ArchivoModel>(archivoModel, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Archivo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<ArchivoModel> actualizar(ArchivoModel model) {
        try {
            log.info("Actualizando la entidad Archivo: {}.",  model);
            if (model.getId() != null) {
                EntidadMensaje<ArchivoModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Ayudador.getAhora(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            ArchivoModel archivoModel = archivoDAO.save(model);
            String mensaje = "La entidad Archivo con id: " + archivoModel.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<ArchivoModel>(archivoModel, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Archivo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<ArchivoModel> reciclar(Long id) {
        log.info("Reciclando la entidad Archivo con id: {}.", id);
        EntidadMensaje<ArchivoModel> objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        if (objeto.getObjeto().getEliminada() == null) {
            String mensaje = "La entidad Archivo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(mensaje);
            objeto.setMensaje(mensaje);
            return objeto;
        }
        objeto.getObjeto().setEliminada(null);
        objeto.getObjeto().setEliminador(null);
        objeto.setObjeto(archivoDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Archivo con id: " + id + ", fue reciclada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<ArchivoModel> eliminar(Long id) {
        log.info("Borrando la entidad Archivo con id: {}.", id);
        EntidadMensaje<ArchivoModel> objeto = this.buscarPorId(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        objeto.getObjeto().setEliminada(Ayudador.getAhora(""));
        objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
        objeto.setObjeto(archivoDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Archivo con id: " + id + ", fue borrada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<ArchivoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Archivo con id: {}.", id);
            EntidadMensaje<ArchivoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Archivo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            Path fileToDeletePath = Paths.get(resourcePath + objeto.getObjeto().getPath() + objeto.getObjeto().getNombre());
            Files.delete(fileToDeletePath);
            archivoDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida y el archivo " + objeto.getObjeto().getNombre() + " fue eliminado correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Archivo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<ArchivoModel>(null, null, mensaje, 204);
        }
    }
}
