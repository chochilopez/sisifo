package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.TipoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
import muni.eolida.sisifo.service.TipoReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TipoReclamoServiceImpl implements TipoReclamoService {
    private final TipoReclamoDAO tipoReclamoDAO;
    private final TipoReclamoMapper tipoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodasPorAreaId(Long id) {
        try {
            log.info("Buscando todas la entidades TipoReclamo con id de Area: {}.", id);
            List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByAreaIdAndEliminadaIsNull(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades TipoReclamo con id de Area: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades TipoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodasPorAreaIdConEliminadas(Long id) {
        try {
            log.info("Buscando todas la entidades TipoReclamo con id de Area: {}, incluidas las eliminadas.", id);
            List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByAreaId(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades TipoReclamo con id de Area: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades TipoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodasPorTipo(String tipo) {
        try {
            log.info("Buscando todas la entidades TipoReclamo con tipo: {}.", tipo);
            List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByTipoIgnoreCaseContainingAndEliminadaIsNull(tipo);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades TipoReclamo con tipo: " + tipo + ".";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades TipoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodasPorTipoConEliminadas(String tipo) {
        try {
            log.info("Buscando todas la entidades TipoReclamo con tipo: {}, incluidas las eliminadas.", tipo);
            List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByTipoIgnoreCaseContaining(tipo);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades TipoReclamo con tipo: " + tipo + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades TipoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad TipoReclamo con id: {}.", id);
            Optional<TipoReclamoModel> objeto = tipoReclamoDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad TipoReclamo con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad TipoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad TipoReclamo con id: {}, incluidas las eliminadas.", id);
            Optional<TipoReclamoModel> objeto = tipoReclamoDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad TipoReclamo con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad TipoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades TipoReclamo.");
            List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades TipoReclamo.";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades TipoReclamo.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades TipoReclamo, incluidas las eliminadas.");
            List<TipoReclamoModel> listado = tipoReclamoDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades TipoReclamo, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades TipoReclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<TipoReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = tipoReclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades TipoReclamo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = tipoReclamoDAO.count();
        log.info("Existen {} entidades TipoReclamo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntityMessenger<TipoReclamoModel> insertar(TipoReclamoCreation model) {
        try {
            log.info("Insertando la entidad TipoReclamo: {}.",  model);
            TipoReclamoModel objeto = tipoReclamoDAO.save(tipoReclamoMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            tipoReclamoDAO.save(objeto);
            String mensaje = "La entidad TipoReclamo con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<TipoReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad TipoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> actualizar(TipoReclamoModel model) {
        try {
            log.info("Actualizando la entidad TipoReclamo: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<TipoReclamoModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            TipoReclamoModel objeto = tipoReclamoDAO.save(model);
            String mensaje = "La entidad TipoReclamo con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<TipoReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad TipoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad TipoReclamo con id: {}.", id);
            EntityMessenger<TipoReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad TipoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
                log.warn(mensaje);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            objeto.getObjeto().setEliminada(null);
            objeto.getObjeto().setEliminador(null);
            objeto.setObjeto(tipoReclamoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad TipoReclamo con id: " + id + ", fue reciclada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad TipoReclamo con id: {}.", id);
            EntityMessenger<TipoReclamoModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
            objeto.setObjeto(tipoReclamoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad TipoReclamo con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<TipoReclamoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad TipoReclamo con id: {}.", id);
            EntityMessenger<TipoReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad TipoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            tipoReclamoDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad TipoReclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<TipoReclamoModel>(null, null, mensaje, 204);
        }
    }
}
