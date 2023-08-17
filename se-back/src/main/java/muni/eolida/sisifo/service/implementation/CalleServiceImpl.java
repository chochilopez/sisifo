package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.CalleMapper;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.repository.CalleDAO;
import muni.eolida.sisifo.service.CalleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CalleServiceImpl implements CalleService {

    private final CalleDAO calleDAO;
    private final CalleMapper calleMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<CalleModel> buscarTodasPorCalle(String calle) {
        log.info("Buscando todas la entidades Calle con nombre: {}.", calle);
        List<CalleModel> list = calleDAO.findAllByCalleIgnoreCaseContainingAndEliminadaIsNull(calle);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Calle con nombre: " + calle + ".";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Calle.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodasPorCalleConEliminadas(String calle) {
        log.info("Buscando todas la entidades Calle con nombre: {}, incluidas las eliminadas.", calle);
        List<CalleModel> list = calleDAO.findAllByCalleIgnoreCaseContaining(calle);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Calle con nombre: " + calle + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Calle, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Calle con id: {}.", id);
        Optional<CalleModel> object = calleDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Calle con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Calle.";
            log.info(message);
            return new EntityMessenger<CalleModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Calle con id: {}, incluidas las eliminadas.", id);
        Optional<CalleModel> object = calleDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Calle con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Calle, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<CalleModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodas() {
        log.info("Buscando todas las entidades Calle.");
        List<CalleModel> list = calleDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Calle.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Calle.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<CalleModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Calle, incluidas las eliminadas.");
        List<CalleModel> list = calleDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Calle, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<CalleModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Calle, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<CalleModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = calleDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Calle.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = calleDAO.count();
        log.info("Existen {} entidades Calle, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<CalleModel> insertar(CalleCreation model) {
        try {
            log.info("Insertando la entidad Calle: {}.",  model);
            CalleModel object = calleDAO.save(calleMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(usuarioService.obtenerUsuario().getObject());
            calleDAO.save(object);
            String message = "La entidad Calle con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<CalleModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Calle. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<CalleModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> actualizar(CalleModel model) {
        try {
            log.info("Actualizando la entidad Calle: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<CalleModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            CalleModel object = calleDAO.save(model);
            String message = "La entidad Calle con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<CalleModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Calle. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<CalleModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<CalleModel> reciclar(Long id) {
        log.info("Reciclando la entidad Calle con id: {}.", id);
        EntityMessenger<CalleModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Calle con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(calleDAO.save(entityMessenger.getObject()));
        String message = "La entidad Calle con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<CalleModel> eliminar(Long id) {
        log.info("Borrando la entidad Calle con id: {}.", id);
        EntityMessenger<CalleModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(calleDAO.save(entityMessenger.getObject()));
        String message = "La entidad Calle con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<CalleModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Calle con id: {}.", id);
            EntityMessenger<CalleModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Calle con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            calleDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Calle. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<CalleModel>(null, null, message, 204);
        }
    }
}
