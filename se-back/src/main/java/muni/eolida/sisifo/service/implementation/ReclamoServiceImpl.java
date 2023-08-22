package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.ReclamoDAO;
import muni.eolida.sisifo.service.ReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReclamoServiceImpl implements ReclamoService {
    private final ReclamoDAO reclamoDAO;
    private final ReclamoMapper reclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<ReclamoModel> buscarMisReclamos() {
        try {
            EntityMessenger<UsuarioModel> usuarioModel = usuarioService.obtenerUsuario();
            log.info("Obteniendo los reclamos del usuario: {}.", usuarioModel.getObjeto().getUsername());
            EntityMessenger<ReclamoModel> reclamo = this.buscarTodasPorCreadorId(usuarioModel.getObjeto().getId());
            if (reclamo.getEstado() == 200) {
                String mensaje = "Se encontraron " + reclamo.getListado().size() + " + reclamos para el usuario + " + usuarioModel.getObjeto().getUsername() + ".";
                log.info(mensaje);
                reclamo.setMensaje(mensaje);
            }
            return reclamo;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoId(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de TipoReclamo: {}.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByTipoReclamoIdAndEliminadaIsNull(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de TipoReclamo: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoIdConEliminadas(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de TipoReclamo: {}, incluidas las eliminadas.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByTipoReclamoId(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de TipoReclamo: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorBarrioId(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de Barrio: {}.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByBarrioIdAndEliminadaIsNull(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de Barrio: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorBarrioIdConEliminadas(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de Barrio: {}, incluidas las eliminadas.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByBarrioId(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de Barrio: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCalleId(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de Calle: {}.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByCalleIdAndEliminadaIsNull(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de Calle: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCalleIdConEliminadas(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de Calle: {}, incluidas las eliminadas.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByCalleId(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de Calle: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorDescripcion(String descripcion) {
        try {
            log.info("Buscando todas la entidades Reclamo con descripcion: {}.", descripcion);
            List<ReclamoModel> listado = reclamoDAO.findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(descripcion);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con descripcion: " + descripcion + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion) {
        try {
            log.info("Buscando todas la entidades Reclamo con descripcion: {}, incluidas las eliminadas.", descripcion);
            List<ReclamoModel> listado = reclamoDAO.findAllByDescripcionContainingIgnoreCase(descripcion);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con descripcion: " + descripcion + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin) {
        try {
            log.info("Buscando todas la entidades Reclamo con fecha de creacion entre {} y {}.", inicio, fin);
            List<ReclamoModel> listado = reclamoDAO.findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(
                    Helper.stringToLocalDateTime(inicio, null),
                    Helper.stringToLocalDateTime(fin, null)
            );
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con fecha de creacion entre " + inicio + " y " + fin + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin) {
        try {
            log.info("Buscando todas la entidades Reclamo con fecha de creacion entre {} y {}, incluidas las eliminadas.", inicio, fin);
            List<ReclamoModel> listado = reclamoDAO.findAllByCreadaBetweenInicioAndFin(
                    Helper.stringToLocalDateTime(inicio, null),
                    Helper.stringToLocalDateTime(fin, null)
            );
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con fecha de creacion entre " + inicio + " y " + fin + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadorId(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de Creador: {}.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByCreadorIdAndEliminadaIsNull(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de Creador: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadorIdConEliminadas(Long id) {
        try {
            log.info("Buscando todas la entidades Reclamo con id de Creador: {}, incluidas las eliminadas.", id);
            List<ReclamoModel> listado = reclamoDAO.findAllByCreadorId(id);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo con id de Creador: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad Reclamo con id: {}.", id);
            Optional<ReclamoModel> objeto = reclamoDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Reclamo con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad Reclamo con id: {}, incluidas las eliminadas.", id);
            Optional<ReclamoModel> objeto = reclamoDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Reclamo con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades Reclamo.");
            List<ReclamoModel> listado = reclamoDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Reclamo.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades Reclamo, incluidas las eliminadas.");
            List<ReclamoModel> listado = reclamoDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades Reclamo, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<ReclamoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Reclamo, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<ReclamoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = reclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Reclamo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = reclamoDAO.count();
        log.info("Existen {} entidades Reclamo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntityMessenger<ReclamoModel> insertar(ReclamoCreation model) {
        try {
            log.info("Insertando la entidad Reclamo: {}.",  model);
            ReclamoModel objeto = reclamoDAO.save(reclamoMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            reclamoDAO.save(objeto);
            String mensaje = "La entidad Reclamo con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<ReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Reclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> actualizar(ReclamoModel model) {
        try {
            log.info("Actualizando la entidad Reclamo: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<ReclamoModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            ReclamoModel objeto = reclamoDAO.save(model);
            String mensaje = "La entidad Reclamo con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<ReclamoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Reclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad Reclamo con id: {}.", id);
            EntityMessenger<ReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Reclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
                log.warn(mensaje);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            objeto.getObjeto().setEliminada(null);
            objeto.getObjeto().setEliminador(null);
            objeto.setObjeto(reclamoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Reclamo con id: " + id + ", fue reciclada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad Reclamo con id: {}.", id);
            EntityMessenger<ReclamoModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
            objeto.setObjeto(reclamoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Reclamo con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Reclamo con id: {}.", id);
            EntityMessenger<ReclamoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Reclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            reclamoDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Reclamo. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<ReclamoModel>(null, null, mensaje, 204);
        }
    }
}
