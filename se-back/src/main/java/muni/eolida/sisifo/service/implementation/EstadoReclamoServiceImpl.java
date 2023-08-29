package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.service.EstadoReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstadoReclamoServiceImpl implements EstadoReclamoService {
    private final EstadoReclamoDAO estadoReclamoDAO;
    private final EstadoReclamoMapper estadoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamo(String estado) {
        log.info("Buscando la entidad EstadoReclamo con estado: {}.", estado);
        try {
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findByEstadoAndEliminadaIsNull(TipoEstadoReclamoEnum.valueOf(estado));
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con estado: " + estado + ".";
                log.warn(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }

    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamoConEliminadas(String estado) {
        try {
            log.info("Buscando la entidad EstadoReclamo con estado: {}, incluidas las eliminadas.", estado);
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findByEstado(TipoEstadoReclamoEnum.valueOf(estado));
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con estado: " + estado + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad EstadoReclamo con id: {}.", id);
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad EstadoReclamo con id: {}, incluidas las eliminadas.", id);
            Optional<EstadoReclamoModel> objeto = estadoReclamoDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad EstadoReclamo con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades EstadoReclamo.");
            List<EstadoReclamoModel> listado = estadoReclamoDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades EstadoReclamo.";
                log.warn(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades EstadoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades EstadoReclamo, incluidas las eliminadas.");
            List<EstadoReclamoModel> listado = estadoReclamoDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades EstadoReclamo, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades EstadoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<EstadoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
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
    public EntityMessenger<EstadoReclamoModel> insertar(EstadoReclamoCreation creation) {
        try {
            log.info("Insertando la entidad EstadoReclamo: {}.",  creation);
            creation.setId(null);
            EstadoReclamoModel objeto = estadoReclamoDAO.save(estadoReclamoMapper.toEntity(creation));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            estadoReclamoDAO.save(objeto);
            String mensaje = "La entidad EstadoReclamo con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> actualizar(EstadoReclamoCreation creation) {
        try {
            log.info("Actualizando la entidad EstadoReclamo: {}.",  creation);
            EstadoReclamoModel entidad = estadoReclamoMapper.toEntity(creation);
            entidad.setModificada(Helper.getNow(""));
            entidad.setModificador(usuarioService.obtenerUsuario().getObjeto());
            String mensaje = "La entidad EstadoReclamo con id: " + creation.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(estadoReclamoDAO.save(entidad), null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad EstadoReclamo con id: {}.", id);
            EntityMessenger<EstadoReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
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
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad EstadoReclamo con id: {}.", id);
            EntityMessenger<EstadoReclamoModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
            objeto.setObjeto(estadoReclamoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad EstadoReclamo con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad EstadoReclamo con id: {}.", id);
            EntityMessenger<EstadoReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
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
            return new EntityMessenger<EstadoReclamoModel>(null, null, mensaje, 204);
        }
    }
}
