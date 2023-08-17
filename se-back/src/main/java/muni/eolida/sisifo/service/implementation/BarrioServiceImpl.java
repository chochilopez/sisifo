package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.BarrioMapper;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.repository.BarrioDAO;
import muni.eolida.sisifo.service.BarrioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BarrioServiceImpl implements BarrioService {

    private final BarrioDAO barrioDAO;
    private final BarrioMapper barrioMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<BarrioModel> buscarTodasPorBarrio(String barrio) {
        log.info("Buscando todas la entidades Barrio con nombre: {}.", barrio);
        List<BarrioModel> list = barrioDAO.findAllByBarrioIgnoreCaseContainingAndEliminadaIsNull(barrio);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Barrio con nombre: " + barrio + ".";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Barrio.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarTodasPorBarrioConEliminadas(String barrio) {
        log.info("Buscando todas la entidades Barrio con nombre: {}, incluidas las eliminadas.", barrio);
        List<BarrioModel> list = barrioDAO.findAllByBarrioIgnoreCaseContaining(barrio);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Barrio con nombre: " + barrio + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Barrio, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Barrio con id: {}.", id);
        Optional<BarrioModel> object = barrioDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Barrio con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Barrio.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Barrio con id: {}, incluidas las eliminadas.", id);
        Optional<BarrioModel> object = barrioDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Barrio con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Barrio, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarTodas() {
        log.info("Buscando todas las entidades Barrio.");
        List<BarrioModel> list = barrioDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Barrio.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Barrio.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Barrio, incluidas las eliminadas.");
        List<BarrioModel> list = barrioDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Barrio, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Barrio, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = barrioDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Barrio.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = barrioDAO.count();
        log.info("Existen {} entidades Barrio, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<BarrioModel> insertar(BarrioCreation model) {
        try {
            log.info("Insertando la entidad Barrio: {}.",  model);
            BarrioModel object = barrioDAO.save(barrioMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(usuarioService.obtenerUsuario().getObject());
            barrioDAO.save(object);
            String message = "La entidad Barrio con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Barrio. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> actualizar(BarrioModel model) {
        try {
            log.info("Actualizando la entidad Barrio: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<BarrioModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            BarrioModel object = barrioDAO.save(model);
            String message = "La entidad Barrio con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<BarrioModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Barrio. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<BarrioModel> reciclar(Long id) {
        log.info("Reciclando la entidad Barrio con id: {}.", id);
        EntityMessenger<BarrioModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Barrio con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(barrioDAO.save(entityMessenger.getObject()));
        String message = "La entidad Barrio con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<BarrioModel> eliminar(Long id) {
        log.info("Borrando la entidad Barrio con id: {}.", id);
        EntityMessenger<BarrioModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(barrioDAO.save(entityMessenger.getObject()));
        String message = "La entidad Barrio con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<BarrioModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Barrio con id: {}.", id);
            EntityMessenger<BarrioModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Barrio con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            barrioDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Barrio. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<BarrioModel>(null, null, message, 204);
        }
    }
}
