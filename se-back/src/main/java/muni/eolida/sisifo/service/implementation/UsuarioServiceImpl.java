package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;
    private final RolServiceImpl rolService;

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String username) {
        log.info("Searching for entity User with username: {}.", username);
        Optional<UsuarioModel> object = usuarioDAO.findByUsernameContainingIgnoreCaseAndBajaIsNullAndHabilitadaIsTrue(username);
        if (object.isEmpty()) {
            String message = "No entity User with username: " + username + " was found.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioConBorrados(String username) {
        log.info("Searching for entity User with username: {}, included deleted ones.", username);
        Optional<UsuarioModel> object = usuarioDAO.findByUsernameContainingIgnoreCase(username);
        if (object.isEmpty()) {
            String message = "No entity User with username: " + username + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> darRol(UsuarioModel usuarioModel, RolEnum rolEnum) {
        log.info("Adding authority {} to username {}.", rolEnum, usuarioModel.getUsername());
        EntityMessenger<RolModel> rolModelEntityMessenger =  rolService.buscarTodosPorRol(rolEnum);
        if (rolModelEntityMessenger.getStatusCode() != 200){
            String message = "Unexistent authority: " + rolEnum + ".";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        }
        if (usuarioModel.getRoles().isEmpty()) {
            HashSet<RolModel> roles = new HashSet<>();
            usuarioModel.setRoles(roles);
        }
        usuarioModel.getRoles().add(rolModelEntityMessenger.getObject());
        this.actualizar(usuarioModel);
        String message = "The authority " + rolEnum + " was added to username " + usuarioModel.getUsername() + ".";
        log.info(message);
        return new EntityMessenger<UsuarioModel>(usuarioModel, null, message, 200);
    }

    @Override
    public EntityMessenger<UsuarioModel> obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return this.buscarPorNombreDeUsuario(authentication.getName());
        else
            return this.buscarPorNombreDeUsuario("admin@municrespo.gob.ar");
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorId(Long id) {
        log.info("Searching for entity User with id: {}.", id);
        Optional<UsuarioModel> object = usuarioDAO.findByIdAndBajaIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity User with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarPorIdConBorrados(Long id) {
        log.info("Searching for entity User with id: {}, included deleted ones.", id);
        Optional<UsuarioModel> object = usuarioDAO.findById(id);
        if (object.isEmpty()) {
            String message = "No entity User with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodos() {
        log.info("Searching for all entities User.");
        List<UsuarioModel> list = usuarioDAO.findAllByBajaIsNull();
        if (list.isEmpty()) {
            String message = "No entities User were found.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities User were found.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodosConBorrados() {
        log.info("Searching for all entities User, included deleted ones.");
        List<UsuarioModel> list = usuarioDAO.findAll()  ;
        if (list.isEmpty()) {
            String message = "No entities User were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities User were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = usuarioDAO.countAllByBajaIsNull();
        log.info("Table User possess {} entities.", count);
        return count;
    }

    @Override
    public Long contarTodosConBorrados() {
        Long count = usuarioDAO.count();
        log.info("Table User possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public EntityMessenger<UsuarioModel> insertar(UsuarioCreation model) {
        try {
            log.info("Inserting entity User: {}.",  model);
            if (usuarioDAO.existsByUsernameContainingIgnoreCase(model.getUsername()))
                return new EntityMessenger<UsuarioModel>(null, null, "Ya existe un usuario con ese email.", 202);
            UsuarioModel usuarioModel = usuarioDAO.save(usuarioMapper.toEntity(model));
            usuarioModel.setAlta(Helper.getNow(""));
            usuarioDAO.save(usuarioModel);
            String message = "The entity User with id: " + usuarioModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(usuarioModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> actualizar(UsuarioModel model) {
        try {
            log.info("Updating entity User: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<UsuarioModel> existant = this.buscarPorId(model.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            model.setModificacion(Helper.getNow(""));
            UsuarioModel usuarioModel = usuarioDAO.save(model);
            String message = "The entity User with id: " + usuarioModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(usuarioModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> reciclar(Long id) {
        log.info("Recycling entity User with: {}.", id);
        EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity User with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(null);
        entityMessenger.setObject(usuarioDAO.save(entityMessenger.getObject()));
        String message = "The entity User with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UsuarioModel> borrar(Long id) {
        log.info("Deleting entity User with: {}.", id);
        EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setBaja(Helper.getNow(""));
        entityMessenger.setObject(usuarioDAO.save(entityMessenger.getObject()));
        String message = "The entity User with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UsuarioModel> destruir(Long id) {
        log.info("Destroying entity User with: {}.", id);
        EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorIdConBorrados(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getBaja() == null) {
            String message = "The entity User with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        usuarioDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
