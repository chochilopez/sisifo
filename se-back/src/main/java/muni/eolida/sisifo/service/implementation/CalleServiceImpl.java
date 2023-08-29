package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.CalleMapper;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.repository.CalleDAO;
import muni.eolida.sisifo.service.CalleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalleServiceImpl implements CalleService {
    private final CalleDAO calleDAO;
    private final CalleMapper calleMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<CalleModel> buscarTodasPorCalle(String calle) {
        try {
            log.info("Buscando todas la entidades Calle con nombre: {}.", calle);
            List<CalleModel> listado = calleDAO.findAllByCalleIgnoreCaseContainingAndEliminadaIsNull(calle);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Calle con nombre: " + calle + ".";
                log.warn(mensaje);
                return new EntityMessenger<CalleModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Calle.";
                log.info(mensaje);
                return new EntityMessenger<CalleModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodasPorCalleConEliminadas(String calle) {
        try {
            log.info("Buscando todas la entidades Calle con nombre: {}, incluidas las eliminadas.", calle);
            List<CalleModel> listado = calleDAO.findAllByCalleIgnoreCaseContaining(calle);
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Calle con nombre: " + calle + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<CalleModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Calle, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<CalleModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad Calle con id: {}.", id);
            Optional<CalleModel> objeto = calleDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Calle con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<CalleModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Calle.";
                log.info(mensaje);
                return new EntityMessenger<CalleModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad Calle con id: {}, incluidas las eliminadas.", id);
            Optional<CalleModel> objeto = calleDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Calle con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<CalleModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Calle, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<CalleModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades Calle.");
            List<CalleModel> listado = calleDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Calle.";
                log.warn(mensaje);
                return new EntityMessenger<CalleModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Calle.";
                log.info(mensaje);
                return new EntityMessenger<CalleModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades Calle, incluidas las eliminadas.");
            List<CalleModel> listado = calleDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades Calle, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<CalleModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Calle, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<CalleModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = calleDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Calle.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = calleDAO.count();
        log.info("Existen {} entidades Calle, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntityMessenger<CalleModel> insertar(CalleCreation creation) {
        try {
            log.info("Insertando la entidad Calle: {}.",  creation);
            creation.setId(null);
            CalleModel objeto = calleDAO.save(calleMapper.toEntity(creation));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            calleDAO.save(objeto);
            String mensaje = "La entidad Calle con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<CalleModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Calle. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> actualizar(CalleCreation creation) {
        try {
            log.info("Actualizando la entidad Calle: {}.",  creation);
            CalleModel entidad = calleMapper.toEntity(creation);
            entidad.setModificada(Helper.getNow(""));
            entidad.setModificador(usuarioService.obtenerUsuario().getObjeto());
            String mensaje = "La entidad Calle con id: " + creation.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<CalleModel>(calleDAO.save(entidad), null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Calle. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad Calle con id: {}.", id);
            EntityMessenger<CalleModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Calle con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
                log.warn(mensaje);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            objeto.getObjeto().setEliminada(null);
            objeto.getObjeto().setEliminador(null);
            objeto.setObjeto(calleDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Calle con id: " + id + ", fue reciclada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad Calle con id: {}.", id);
            EntityMessenger<CalleModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
            objeto.setObjeto(calleDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Calle con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Calle con id: {}.", id);
            EntityMessenger<CalleModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Calle con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            calleDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Calle. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<CalleModel>(null, null, mensaje, 204);
        }
    }
}
