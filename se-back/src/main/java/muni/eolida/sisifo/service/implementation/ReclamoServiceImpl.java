package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.ReclamoDAO;
import muni.eolida.sisifo.service.ReclamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReclamoServiceImpl implements ReclamoService {

    private final ReclamoDAO reclamoDAO;
    private final ReclamoMapper reclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<ReclamoModel> buscarMisReclamos() {
        EntityMessenger<UsuarioModel> usuarioModel = usuarioService.obtenerUsuario();
        log.info("Obteniendo los reclamos del usuario: {}.", usuarioModel.getObject().getUsername());
        EntityMessenger<ReclamoModel> reclamo = this.buscarPorCreador(usuarioModel.getObject().getId());
        if (reclamo.getStatusCode() == 200) {
            String message = "Se encontraron " + reclamo.getList().size() + " + reclamos para el usuario + " + usuarioModel.getObject().getUsername() + ".";
            log.info(message);
            reclamo.setMessage(message);
        }
        return reclamo;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreador(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreadorConEliminadas(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreadorId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCreadorIdConEliminadas(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorTipoReclamoId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorTipoReclamoIdConEliminadas(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorBarrioId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorBarrioIdConEliminadas(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCalleId(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorCalleIdConEliminadas(Long id) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorByCreadorNombre(String nombre) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadorNombreConEliminadas(String nombre) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCalleCalle(String calle) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCalleCalleConEliminadas(String calle) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoTipo(String tipo) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoTipoConEliminadas(Long tipo) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorDescripcion(String descripcion) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorDescripcionConEliminadas(Long descripcion) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorBarrioCalle(String barrio) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorBarrioCalleConEliminadas(String barrio) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(LocalDateTime inicio, LocalDateTime fin) {
        return null;
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Reclamo con id: {}.", id);
        Optional<ReclamoModel> object = reclamoDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Reclamo con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Reclamo.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Reclamo con id: {}, incluidas las eliminadas.", id);
        Optional<ReclamoModel> object = reclamoDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Reclamo con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Reclamo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodas() {
        log.info("Buscando todas las entidades Reclamo.");
        List<ReclamoModel> list = reclamoDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Reclamo.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Reclamo.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Reclamo, incluidas las eliminadas.");
        List<ReclamoModel> list = reclamoDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Reclamo, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Reclamo, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = reclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Reclamo.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = reclamoDAO.count();
        log.info("Existen {} entidades Reclamo, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<ReclamoModel> insertar(ReclamoCreation model) {
        try {
            log.info("Insertando la entidad Reclamo: {}.",  model);
            ReclamoModel object = reclamoDAO.save(reclamoMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(usuarioService.obtenerUsuario().getObject());
            reclamoDAO.save(object);
            String message = "La entidad Reclamo con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Reclamo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> actualizar(ReclamoModel model) {
        try {
            log.info("Actualizando la entidad Reclamo: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<ReclamoModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            ReclamoModel object = reclamoDAO.save(model);
            String message = "La entidad Reclamo con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<ReclamoModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Reclamo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<ReclamoModel> reciclar(Long id) {
        log.info("Reciclando la entidad Reclamo con id: {}.", id);
        EntityMessenger<ReclamoModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Reclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(reclamoDAO.save(entityMessenger.getObject()));
        String message = "La entidad Reclamo con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ReclamoModel> eliminar(Long id) {
        log.info("Borrando la entidad Reclamo con id: {}.", id);
        EntityMessenger<ReclamoModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(reclamoDAO.save(entityMessenger.getObject()));
        String message = "La entidad Reclamo con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<ReclamoModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Reclamo con id: {}.", id);
            EntityMessenger<ReclamoModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Reclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            reclamoDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Reclamo. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<ReclamoModel>(null, null, message, 204);
        }
    }
}
