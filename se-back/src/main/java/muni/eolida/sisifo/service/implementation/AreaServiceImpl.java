package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.AreaMapper;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.repository.AreaDAO;
import muni.eolida.sisifo.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaDAO areaDAO;
    private final TipoReclamoDAO tipoReclamoDAO;
    private final AreaMapper areaMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<AreaModel> agregarTipoReclamoAArea(Long idArea, Long idTipoReclamo) {
        try {
            log.info("Agregando el TipoReclamo con id: {}, al Area con id: {}.", idArea, idTipoReclamo);
            Optional<TipoReclamoModel> tipoReclamo = tipoReclamoDAO.findByIdAndEliminadaIsNull(idTipoReclamo);
            Optional<AreaModel> area = areaDAO.findByIdAndEliminadaIsNull(idArea);
            if (area.isEmpty() || tipoReclamo.isEmpty()) {
                String mensaje = "El Area o el TipoReclamo no existen.";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                if (area.get().getTiposReclamos().contains(tipoReclamo.get())) {
                    String mensaje = "El Area ya posee el TipoReclamo.";
                    log.warn(mensaje);
                    return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
                }
                area.get().getTiposReclamos().add(tipoReclamo.get());
                String mensaje = "Se agrego correctamente el TipoReclamo " + tipoReclamo.get().getTipo() + " al Area " + area.get().getArea() + ".";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(areaDAO.save(area.get()), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodasPorArea(String area) {
        try {
            log.info("Buscando todas la entidades Area con nombre: {}.", area);
            List<AreaModel> listado = areaDAO.findAllByAreaIgnoreCaseContainingAndEliminadaIsNull(area);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Area con nombre: " + area + ".";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Area.";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodasPorAreaConEliminadas(String area) {
        try {
            log.info("Buscando todas la entidades Area con nombre: {}, incluidas las eliminadas.", area);
            List<AreaModel> listado = areaDAO.findAllByAreaIgnoreCaseContaining(area);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Area con nombre: " + area + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Area, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad Area con id: {}.", id);
            Optional<AreaModel> objeto = areaDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Area con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Area.";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad Area con id: {}, incluidas las eliminadas.", id);
            Optional<AreaModel> objeto = areaDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Area con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Area, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades Area.");
            List<AreaModel> listado = areaDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Area.";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Area.";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades Area, incluidas las eliminadas.");
            List<AreaModel> listado = areaDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades Area, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<AreaModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Area, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<AreaModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
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
    public EntityMessenger<AreaModel> insertar(AreaCreation model) {
        try {
            log.info("Insertando la entidad Area: {}.",  model);
            AreaModel objeto = areaDAO.save(areaMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            areaDAO.save(objeto);
            String mensaje = "La entidad Area con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<AreaModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Area. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> actualizar(AreaModel model) {
        try {
            log.info("Actualizando la entidad Area: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<AreaModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            AreaModel objeto = areaDAO.save(model);
            String mensaje = "La entidad Area con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<AreaModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Area. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad Area con id: {}.", id);
            EntityMessenger<AreaModel> objeto = this.buscarPorIdConEliminadas(id);
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
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad Area con id: {}.", id);
            EntityMessenger<AreaModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
            objeto.setObjeto(areaDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Area con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Area con id: {}.", id);
            EntityMessenger<AreaModel> objeto = this.buscarPorIdConEliminadas(id);
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
            return new EntityMessenger<AreaModel>(null, null, mensaje, 204);
        }
    }
}
