package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.RolMapper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.RolDAO;
import muni.eolida.sisifo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RolServiceImpl implements RolService {

    @Autowired
    private RolDAO rolDAO;
    @Autowired
    private RolMapper rolMapper;

    @Override
    public EntityMessenger<RolModel> buscarPorRol(String rol) {
        try {
            log.info("Buscando la entidad Rol con rol: {}.", rol);
            Optional<RolModel> objeto = rolDAO.findByRolAndEliminadaIsNull(RolEnum.valueOf(rol));
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Rol con rol: " + rol + ".";
                log.warn(mensaje);
                return new EntityMessenger<RolModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Rol.";
                log.info(mensaje);
                return new EntityMessenger<RolModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorRolConEliminadas(String rol) {
        try {
            log.info("Buscando la entidad Rol con rol: {}, incluidas las eliminadas.", rol);
            Optional<RolModel> objeto = rolDAO.findByRol(RolEnum.valueOf(rol));
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Rol con rol: " + rol + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<RolModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Rol, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<RolModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorId(Long id) {
        try {
            log.info("Buscando la entidad Rol con id: {}.", id);
            Optional<RolModel> objeto = rolDAO.findByIdAndEliminadaIsNull(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Rol con id: " + id + ".";
                log.warn(mensaje);
                return new EntityMessenger<RolModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Rol.";
                log.info(mensaje);
                return new EntityMessenger<RolModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarPorIdConEliminadas(Long id) {
        try {
            log.info("Buscando la entidad Rol con id: {}, incluidas las eliminadas.", id);
            Optional<RolModel> objeto = rolDAO.findById(id);
            if (objeto.isEmpty()) {
                String mensaje = "No se encontro una entidad Rol con id: " + id + ", incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<RolModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontro una entidad Rol, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<RolModel>(objeto.get(), null, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodas() {
        try {
            log.info("Buscando todas las entidades Rol.");
            List<RolModel> listado = rolDAO.findAllByEliminadaIsNull();
            if (listado.isEmpty()) {
                String mensaje = "No se encontraron entidades Rol.";
                log.warn(mensaje);
                return new EntityMessenger<RolModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Rol.";
                log.info(mensaje);
                return new EntityMessenger<RolModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodasConEliminadas() {
        try {
            log.info("Buscando todas las entidades Rol, incluidas las eliminadas.");
            List<RolModel> listado = rolDAO.findAll();
            if (listado.isEmpty()) {
                String mensaje = "No se encontrarón entidades Rol, incluidas las eliminadas.";
                log.warn(mensaje);
                return new EntityMessenger<RolModel>(null, null, mensaje, 202);
            } else {
                String mensaje = "Se encontraron " + listado.size() + " entidades Rol, incluidas las eliminadas.";
                log.info(mensaje);
                return new EntityMessenger<RolModel>(null, listado, mensaje, 200);
            }
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al realizar la busqueda.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = rolDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Rol.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = rolDAO.count();
        log.info("Existen {} entidades Rol, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntityMessenger<RolModel> insertar(RolCreation model) {
        try {
            log.info("Insertando la entidad Rol: {}.",  model);
            RolModel objeto = rolDAO.save(rolMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            rolDAO.save(objeto);
            String mensaje = "La entidad Rol con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<RolModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Rol. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> actualizar(RolModel model) {
        try {
            log.info("Actualizando la entidad Rol: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<RolModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            RolModel objeto = rolDAO.save(model);
            String mensaje = "La entidad Rol con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<RolModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Rol. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> reciclar(Long id) {
        try {
            log.info("Reciclando la entidad Rol con id: {}.", id);
            EntityMessenger<RolModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Rol con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
                log.warn(mensaje);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            objeto.getObjeto().setEliminada(null);
            objeto.getObjeto().setEliminador(null);
            objeto.setObjeto(rolDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Rol con id: " + id + ", fue reciclada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar reciclar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> eliminar(Long id) {
        try {
            log.info("Borrando la entidad Rol con id: {}.", id);
            EntityMessenger<RolModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            objeto.getObjeto().setEliminada(Helper.getNow(""));
            objeto.setObjeto(rolDAO.save(objeto.getObjeto()));
            String mensaje = "La entidad Rol con id: " + id + ", fue borrada correctamente.";
            objeto.setMensaje(mensaje);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar eliminar la entidad.";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Rol con id: {}.", id);
            EntityMessenger<RolModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Rol con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            rolDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Rol. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<RolModel>(null, null, mensaje, 204);
        }
    }
}
