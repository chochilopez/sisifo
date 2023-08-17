package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.service.EstadoReclamoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EstadoReclamoServiceImpl implements EstadoReclamoService {

    private final EstadoReclamoDAO estadoReclamoDAO;
    private final EstadoReclamoMapper estadoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorSeguimientoId(Long id) {
        log.info("Buscando la entidad EstadoReclamo con id de Seguimiento: {}.", id);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findBySeguimientoIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad EstadoReclamo con id de Seguimiento: " + id + ".";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorSeguimientoIdConEliminadas(Long id) {
        log.info("Buscando la entidad EstadoReclamo con id de Seguimiento: {}, incluidas las eliminadas.", id);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findBySeguimientoId(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad EstadoReclamo con id de Seguimiento: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamo(TipoEstadoReclamoEnum estado) {
        log.info("Buscando la entidad EstadoReclamo con estado: {}.", estado);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findByEstadoAndEliminadaIsNull(estado);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad EstadoReclamo con estado: " + estado + ".";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamoConEliminadas(TipoEstadoReclamoEnum estado) {
        log.info("Buscando la entidad EstadoReclamo con estado: {}, incluidas las eliminadas.", estado);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findByEstado(estado);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad EstadoReclamo con estado: " + estado + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorId(Long id) {
        log.info("Buscando la entidad EstadoReclamo con id: {}.", id);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad EstadoReclamo con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad EstadoReclamo con id: {}, incluidas las eliminadas.", id);
        Optional<EstadoReclamoModel> object = estadoReclamoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad EstadoReclamo con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad EstadoReclamo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarTodas() {
        log.info("Buscando todas las entidades EstadoReclamo.");
        List<EstadoReclamoModel> list = estadoReclamoDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades EstadoReclamo.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades EstadoReclamo.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades EstadoReclamo, incluidas las eliminadas.");
        List<EstadoReclamoModel> list = estadoReclamoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades EstadoReclamo, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades EstadoReclamo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = estadoReclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades EstadoReclamo.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = estadoReclamoDAO.count();
        log.info("Existen {} entidades EstadoReclamo, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> insertar(EstadoReclamoCreation model) {
        try {
            log.info("Insertando la entidad EstadoReclamo: {}.",  model);
            EstadoReclamoModel object = estadoReclamoDAO.save(estadoReclamoMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(usuarioService.obtenerUsuario().getObject());
            estadoReclamoDAO.save(object);
            String message = "La entidad EstadoReclamo con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> actualizar(EstadoReclamoModel model) {
        try {
            log.info("Actualizando la entidad EstadoReclamo: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<EstadoReclamoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            EstadoReclamoModel object = estadoReclamoDAO.save(model);
            String message = "La entidad EstadoReclamo con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<EstadoReclamoModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> reciclar(Long id) {
        log.info("Reciclando la entidad EstadoReclamo con id: {}.", id);
        EntityMessenger<EstadoReclamoModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad EstadoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(estadoReclamoDAO.save(entityMessenger.getObject()));
        String message = "La entidad EstadoReclamo con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> eliminar(Long id) {
        log.info("Borrando la entidad EstadoReclamo con id: {}.", id);
        EntityMessenger<EstadoReclamoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(estadoReclamoDAO.save(entityMessenger.getObject()));
        String message = "La entidad EstadoReclamo con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<EstadoReclamoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad EstadoReclamo con id: {}.", id);
            EntityMessenger<EstadoReclamoModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad EstadoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            estadoReclamoDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad EstadoReclamo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<EstadoReclamoModel>(null, null, message, 204);
        }
    }
}
