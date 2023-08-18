package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.AreaMapper;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.repository.AreaDAO;
import muni.eolida.sisifo.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    private final AreaDAO areaDAO;
    private final AreaMapper areaMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EntityMessenger<AreaModel> buscarTodasPorArea(String area) {
        log.info("Buscando todas la entidades Area con nombre: {}.", area);
        List<AreaModel> list = areaDAO.findAllByAreaIgnoreCaseContainingAndEliminadaIsNull(area);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Area con nombre: " + area + ".";
            log.warn(message);
            return new EntityMessenger<AreaModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Area.";
            log.info(message);
            return new EntityMessenger<AreaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodasPorAreaConEliminadas(String area) {
        log.info("Buscando todas la entidades Area con nombre: {}, incluidas las eliminadas.", area);
        List<AreaModel> list = areaDAO.findAllByAreaIgnoreCaseContaining(area);
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Area con nombre: " + area + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<AreaModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Area, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<AreaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Area con id: {}.", id);
        Optional<AreaModel> object = areaDAO.findByIdAndEliminadaIsNull(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Area con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<AreaModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Area.";
            log.info(message);
            return new EntityMessenger<AreaModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Area con id: {}, incluidas las eliminadas.", id);
        Optional<AreaModel> object = areaDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No se encontro una entidad Area con id: " + id + ", incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<AreaModel>(null, null, message, 202);
        } else {
            String message = "Se encontro una entidad Area, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<AreaModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodas() {
        log.info("Buscando todas las entidades Area.");
        List<AreaModel> list = areaDAO.findAllByEliminadaIsNull();
        if (list.isEmpty()) {
            String message = "No se encontraron entidades Area.";
            log.warn(message);
            return new EntityMessenger<AreaModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Area.";
            log.info(message);
            return new EntityMessenger<AreaModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<AreaModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Area, incluidas las eliminadas.");
        List<AreaModel> list = areaDAO.findAll();
        if (list.isEmpty()) {
            String message = "No se encontrarón entidades Area, incluidas las eliminadas.";
            log.warn(message);
            return new EntityMessenger<AreaModel>(null, null, message, 202);
        } else {
            String message = "Se encontraron " + list.size() + " entidades Area, incluidas las eliminadas.";
            log.info(message);
            return new EntityMessenger<AreaModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long count = areaDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Area.", count);
        return count;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long count = areaDAO.count();
        log.info("Existen {} entidades Area, incluidas las eliminadas.", count);
        return count;
    }

    @Override
    public EntityMessenger<AreaModel> insertar(AreaCreation model) {
        try {
            log.info("Insertando la entidad Area: {}.",  model);
            AreaModel object = areaDAO.save(areaMapper.toEntity(model));
            object.setCreada(Helper.getNow(""));
            object.setCreador(usuarioService.obtenerUsuario().getObject());
            areaDAO.save(object);
            String message = "La entidad Area con id: " + object.getId() + ", fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<AreaModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar insertar la entidad Area. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<AreaModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> actualizar(AreaModel model) {
        try {
            log.info("Actualizando la entidad Area: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<AreaModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObject());
            AreaModel object = areaDAO.save(model);
            String message = "La entidad Area con id: " + object.getId() + ", fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<AreaModel>(object, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar actualizar la entidad Area. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<AreaModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<AreaModel> reciclar(Long id) {
        log.info("Reciclando la entidad Area con id: {}.", id);
        EntityMessenger<AreaModel> entityMessenger = this.buscarPorIdConEliminadas(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getEliminada() == null) {
            String message = "La entidad Area con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(null);
        entityMessenger.getObject().setEliminador(null);
        entityMessenger.setObject(areaDAO.save(entityMessenger.getObject()));
        String message = "La entidad Area con id: " + id + ", fue reciclada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<AreaModel> eliminar(Long id) {
        log.info("Borrando la entidad Area con id: {}.", id);
        EntityMessenger<AreaModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setEliminada(Helper.getNow(""));
        entityMessenger.getObject().setEliminador(usuarioService.obtenerUsuario().getObject());
        entityMessenger.setObject(areaDAO.save(entityMessenger.getObject()));
        String message = "La entidad Area con id: " + id + ", fue borrada correctamente.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<AreaModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Area con id: {}.", id);
            EntityMessenger<AreaModel> entityMessenger = this.buscarPorIdConEliminadas(id);
            if (entityMessenger.getStatusCode() == 202) {
                return entityMessenger;
            }
            if (entityMessenger.getObject().getEliminada() == null) {
                String message = "La entidad Area con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(message);
                entityMessenger.setStatusCode(202);
                entityMessenger.setMessage(message);
                return entityMessenger;
            }
            areaDAO.delete(entityMessenger.getObject());
            String message = "La entidad fue destruida correctamente.";
            entityMessenger.setMessage(message);
            entityMessenger.setObject(null);
            log.info(message);
            return entityMessenger;
        } catch (Exception e) {
            String message = "Ocurrió un error al intentar destruir la entidad Area. Excepción: " + e + ".";
            log.error(message);
            return new EntityMessenger<AreaModel>(null, null, message, 204);
        }
    }
}
