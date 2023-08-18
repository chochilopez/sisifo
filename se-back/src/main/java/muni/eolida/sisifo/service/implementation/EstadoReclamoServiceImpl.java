package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.service.EstadoReclamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EstadoReclamoServiceImpl implements EstadoReclamoService {

    @Autowired
    private EstadoReclamoDAO estadoReclamoDAO;
    @Autowired
    private EstadoReclamoMapper estadoReclamoMapper;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public EntidadMensaje<EstadoReclamoModel> buscarPorEstadoReclamo(String estado) {
        log.info("Buscando la entidad EstadoReclamo con estado: {}.", estado);
        try {
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findByEstadoAndEliminadaIsNull(TipoEstadoReclamoEnum.valueOf(estado));
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con estado: " + estado + ".";
                log.warn(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo.";
                log.info(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }

    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> buscarPorEstadoReclamoConEliminadas(String estado) {
        try {
            log.info("Buscando la entidad EstadoReclamo con estado: {}, incluidas las eliminadas.", estado);
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findByEstado(TipoEstadoReclamoEnum.valueOf(estado));
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con estado: " + estado + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad EstadoReclamo con id: {}.", id);
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con id: " + id + ".";
                log.warn(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo.";
                log.info(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad EstadoReclamo con id: {}, incluidas las eliminadas.", id);
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades EstadoReclamo.");
            List<EstadoReclamoModel> listado = estadoReclamoDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades EstadoReclamo.";
                log.warn(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades EstadoReclamo.";
                log.info(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades EstadoReclamo, incluidas las eliminadas.");
            List<EstadoReclamoModel> listado = estadoReclamoDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades EstadoReclamo, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades EstadoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntidadMensaje<EstadoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = estadoReclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades EstadoReclamo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = estadoReclamoDAO.count();
        log.info("Existen {} entidades EstadoReclamo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> insertar(EstadoReclamoCreation model) {
        try {
            log.info("Insertando la entidad EstadoReclamo: {}.",  model);
            EstadoReclamoModel objeto = estadoReclamoDAO.save(estadoReclamoMapper.toEntity(model));
            objeto.setCreada(Ayudador.getAhora(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            estadoReclamoDAO.save(objeto);
            String mensaje = "La entidad EstadoReclamo con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> actualizar(EstadoReclamoModel model) {
        try {
            log.info("Actualizando la entidad EstadoReclamo: {}.",  model);
            if (model.getId() != null) {
                EntidadMensaje<EstadoReclamoModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Ayudador.getAhora(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            EstadoReclamoModel objeto = estadoReclamoDAO.save(model);
            String mensaje = "La entidad EstadoReclamo con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> reciclar(Long id) {
        log.info("Reciclando la entidad EstadoReclamo con id: {}.", id);
        EntidadMensaje<EstadoReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        if (objeto.getObjeto().getEliminada() == null) {
            String mensaje = "La entidad EstadoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(mensaje);
            objeto.setMensaje(mensaje);
            return objeto;
        }
        objeto.getObjeto().setEliminada(null);
        objeto.getObjeto().setEliminador(null);
        objeto.setObjeto(estadoReclamoDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad EstadoReclamo con id: " + id + ", fue reciclada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> eliminar(Long id) {
        log.info("Borrando la entidad EstadoReclamo con id: {}.", id);
        EntidadMensaje<EstadoReclamoModel> objeto = this.buscarPorId(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        objeto.getObjeto().setEliminada(Ayudador.getAhora(""));
        objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
        objeto.setObjeto(estadoReclamoDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad EstadoReclamo con id: " + id + ", fue borrada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<EstadoReclamoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad EstadoReclamo con id: {}.", id);
            EntidadMensaje<EstadoReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad EstadoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            estadoReclamoDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }
}
