package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.SeguimientoMapper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.repository.SeguimientoDAO;
import muni.eolida.sisifo.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SeguimientoServiceImpl implements SeguimientoService {

    @Autowired
    private SeguimientoDAO seguimientoDAO;
    @Autowired
    private SeguimientoMapper seguimientoMapper;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodasPorDescripcion(String descripcion) {
        try {
            log.info("Buscando todas la entidades Seguimiento con descripcion: {}.", descripcion);
            List<SeguimientoModel> listado = seguimientoDAO.findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(descripcion);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Seguimiento con descripcion: " + descripcion + ".";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Seguimiento.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodasPorDescripcionConEliminadas(String descripcion) {
        try {
            log.info("Buscando todas la entidades Seguimiento con descripcion: {}, incluidas las eliminadas.", descripcion);
            List<SeguimientoModel> listado = seguimientoDAO.findAllByDescripcionContainingIgnoreCase(descripcion);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Seguimiento con descripcion: " + descripcion + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Seguimiento, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin) {
        try {
            log.info("Buscando todas la entidades Seguimiento con fecha de creacion entre {} y {}.", inicio, fin);
            List<SeguimientoModel> listado = seguimientoDAO.findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(
                    Helper.stringToLocalDateTime(inicio, null),
                    Helper.stringToLocalDateTime(fin, null)
            );
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Seguimiento con fecha de creacion entre " + inicio + " y " + fin + ".";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Seguimiento.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin) {
        try {
            log.info("Buscando todas la entidades Seguimiento con fecha de creacion entre {} y {}, incluidas las eliminadas.", inicio, fin);
            List<SeguimientoModel> listado = seguimientoDAO.findAllByCreadaBetweenInicioAndFin(
                    Helper.stringToLocalDateTime(inicio, null),
                    Helper.stringToLocalDateTime(fin, null)
            );
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Seguimiento con fecha de creacion entre " + inicio + " y " + fin + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Seguimiento, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad Seguimiento con id: {}.", id);
            Optional<SeguimientoModel> objeto = seguimientoDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Seguimiento con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Seguimiento.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad Seguimiento con id: {}, incluidas las eliminadas.", id);
            Optional<SeguimientoModel> objeto = seguimientoDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Seguimiento con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Seguimiento, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades Seguimiento.");
            List<SeguimientoModel> listado = seguimientoDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Seguimiento.";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Seguimiento.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades Seguimiento, incluidas las eliminadas.");
            List<SeguimientoModel> listado = seguimientoDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades Seguimiento, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Seguimiento, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<SeguimientoModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = seguimientoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Seguimiento.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = seguimientoDAO.count();
        log.info("Existen {} entidades Seguimiento, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntityMessenger<SeguimientoModel> insertar(SeguimientoCreation model) {
        try {
            log.info("Insertando la entidad Seguimiento: {}.",  model);
            SeguimientoModel objeto = seguimientoDAO.save(seguimientoMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            seguimientoDAO.save(objeto);
            String mensaje = "La entidad Seguimiento con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<SeguimientoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Seguimiento. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> actualizar(SeguimientoModel model) {
        try {
            log.info("Actualizando la entidad Seguimiento: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<SeguimientoModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            SeguimientoModel objeto = seguimientoDAO.save(model);
            String mensaje = "La entidad Seguimiento con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<SeguimientoModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Seguimiento. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad Seguimiento con id: {}.", id);
            EntityMessenger<SeguimientoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Seguimiento con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
                log.warn(mensaje);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            objeto.getObjeto().setEliminada(null);
            objeto.getObjeto().setEliminador(null);
            objeto.setObjeto(seguimientoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Seguimiento con id: " + id + ", fue reciclada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad Seguimiento con id: {}.", id);
            EntityMessenger<SeguimientoModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
            objeto.setObjeto(seguimientoDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Seguimiento con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<SeguimientoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Seguimiento con id: {}.", id);
            EntityMessenger<SeguimientoModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Seguimiento con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            seguimientoDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Seguimiento. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<SeguimientoModel>(null, null, mensaje, 204);
        }
    }
}
