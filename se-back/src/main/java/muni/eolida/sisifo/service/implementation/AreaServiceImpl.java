package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.AreaMapper;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.repository.AreaDAO;
import muni.eolida.sisifo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDAO areaDAO;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public EntidadMensaje<AreaModel> buscarTodasPorArea(String area) {
        log.info("Buscando todas la entidades Area con nombre: {}.", area);
        List<AreaModel> listado = areaDAO.findAllByAreaIgnoreCaseContainingAndEliminadaIsNull(area);
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Area con nombre: " + area + ".";
            log.warn(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Area.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> buscarTodasPorAreaConEliminadas(String area) {
        log.info("Buscando todas la entidades Area con nombre: {}, incluidas las eliminadas.", area);
        List<AreaModel> listado = areaDAO.findAllByAreaIgnoreCaseContaining(area);
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Area con nombre: " + area + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Area, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Area con id: {}.", id);
        Optional<AreaModel> objeto = areaDAO.findByIdAndEliminadaIsNull(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Area con id: " + id + ".";
            log.warn(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Area.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Area con id: {}, incluidas las eliminadas.", id);
        Optional<AreaModel> objeto = areaDAO.findById(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Area con id: " + id + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Area, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> buscarTodas() {
        log.info("Buscando todas las entidades Area.");
        List<AreaModel> listado = areaDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Area.";
            log.warn(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Area.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Area, incluidas las eliminadas.");
        List<AreaModel> listado = areaDAO.findAll();
        if (listado.isEmpty()) {
            String mensaje = "No se encontrarón entidades Area, incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Area, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = areaDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Area.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = areaDAO.count();
        log.info("Existen {} entidades Area, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntidadMensaje<AreaModel> insertar(AreaCreation model) {
        try {
            log.info("Insertando la entidad Area: {}.",  model);
            AreaModel objeto = areaDAO.save(areaMapper.toEntity(model));
            objeto.setCreada(Ayudador.getAhora(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            areaDAO.save(objeto);
            String mensaje = "La entidad Area con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Area. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> actualizar(AreaModel model) {
        try {
            log.info("Actualizando la entidad Area: {}.",  model);
            if (model.getId() != null) {
                EntidadMensaje<AreaModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Ayudador.getAhora(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            AreaModel objeto = areaDAO.save(model);
            String mensaje = "La entidad Area con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<AreaModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Area. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<AreaModel> reciclar(Long id) {
        log.info("Reciclando la entidad Area con id: {}.", id);
        EntidadMensaje<AreaModel> objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        if (objeto.getObjeto().getEliminada() == null) {
            String mensaje = "La entidad Area con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(mensaje);
            objeto.setMensaje(mensaje);
            return objeto;
        }
        objeto.getObjeto().setEliminada(null);
        objeto.getObjeto().setEliminador(null);
        objeto.setObjeto(areaDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Area con id: " + id + ", fue reciclada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<AreaModel> eliminar(Long id) {
        log.info("Borrando la entidad Area con id: {}.", id);
        EntidadMensaje<AreaModel> objeto = this.buscarPorId(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        objeto.getObjeto().setEliminada(Ayudador.getAhora(""));
        objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
        objeto.setObjeto(areaDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Area con id: " + id + ", fue borrada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<AreaModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Area con id: {}.", id);
            EntidadMensaje<AreaModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Area con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            areaDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Area. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<AreaModel>(null, null, mensaje, 204);
        }
    }
}
