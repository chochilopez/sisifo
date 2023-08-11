package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.UsuarioModel;
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
public class UsuarioServiceImpletation implements UsuarioService {
    
    private final UsuarioDAO usuarioDTO;

    @Override
    public EntityMessenger<UserModel> findByUsername(String username) {
        log.info("Searching for entity User with username: {}, included deleted ones.", username);
        Optional<UserModel> object = userRepository.findByUsernameContainingIgnoreCase(username);
        if (object.isEmpty()) {
            String message = "No entity User with username: " + username + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<UserModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UserModel> findByUsernameAndRemovedIsNull(String username) {
        log.info("Searching for entity User with username: {}.", username);
        Optional<UserModel> object = userRepository.findByUsernameContainingIgnoreCase(username);
        if (object.isEmpty()) {
            String message = "No entity User with username: " + username + " was found.";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found.";
            log.info(message);
            return new EntityMessenger<UserModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UserModel> addAuthority(UserModel userModel, String authority) {
        log.info("Adding authority {} to username {}.", authority, userModel.getUsername());
        EntityMessenger<AuthorityModel> authorityModelEntityMessenger =  authorityServiceImplementation.findByAuthorityAndRemovedIsNull(authority);
        if (authorityModelEntityMessenger.getStatusCode() != 200){
            String message = "Unexistent authority: " + authority + ".";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        }
        if (userModel.getAuthorities().isEmpty()) {
            HashSet<AuthorityModel> authorities = new HashSet<>();
            userModel.setAuthorities(authorities);
        }
        userModel.getAuthorities().add(authorityModelEntityMessenger.getObject());
        this.update(userModel);
        String message = "The authority " + authority + " was added to username " + userModel.getUsername() + ".";
        log.info(message);
        return new EntityMessenger<UserModel>(userModel, null, message, 200);
    }

    @Override
    public EntityMessenger<UserModel> saveAdmin(LoginRequest loginRequest) {
        try {
            log.info("Adding admin user: " + loginRequest.getUsername());
            UserModel userModel = new UserModel(loginRequest.getUsername(), bcryptEncoder.encode(loginRequest.getPassword()), true);
            userRepository.save(userModel);
            this.addAuthority(userModel, AuthorityEnum.AUTHORITY_ADMIN.toString());
            this.addAuthority(userModel, AuthorityEnum.AUTHORITY_EDITOR.toString());
            this.addAuthority(userModel, AuthorityEnum.AUTHORITY_GUEST.toString());
            String message = "User admin " + loginRequest.getUsername() + " successfully created.";
            log.info(message);
            return new EntityMessenger<UserModel>(userModel, null, message, 200);
        } catch (Exception e) {
            String message = "An error occurred while trying to save admin user: " + loginRequest.getUsername() + ". Exception: " + e + ".";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UserModel> saveEditor(LoginRequest loginRequest) {
        try {
            log.info("Adding admin user: " + loginRequest.getUsername());
            UserModel userModel = new UserModel(loginRequest.getUsername(), bcryptEncoder.encode(loginRequest.getPassword()), true);
            userRepository.save(userModel);
            this.addAuthority(userModel, AuthorityEnum.AUTHORITY_EDITOR.toString());
            this.addAuthority(userModel, AuthorityEnum.AUTHORITY_GUEST.toString());
            String message = "User admin " + loginRequest.getUsername() + " successfully created.";
            log.info(message);
            return new EntityMessenger<UserModel>(userModel, null, message, 200);
        } catch (Exception e) {
            String message = "An error occurred while trying to save admin user: " + loginRequest.getUsername() + ". Exception: " + e + ".";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UserModel> saveGuest(LoginRequest loginRequest) {
        try {
            log.info("Adding admin user: " + loginRequest.getUsername());
            UserModel userModel = new UserModel(loginRequest.getUsername(), bcryptEncoder.encode(loginRequest.getPassword()), true);
            userRepository.save(userModel);
            this.addAuthority(userModel, AuthorityEnum.AUTHORITY_GUEST.toString());
            String message = "User admin " + loginRequest.getUsername() + " successfully created.";
            log.info(message);
            return new EntityMessenger<UserModel>(userModel, null, message, 200);
        } catch (Exception e) {
            String message = "An error occurred while trying to save admin user: " + loginRequest.getUsername() + ". Exception: " + e + ".";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UserModel> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return this.findByUsernameAndRemovedIsNull(authentication.getName());
        else
            return this.findByUsernameAndRemovedIsNull("system@lebe.com.ar");
    }

    @Override
    public EntityMessenger<UserModel> findById(Long id) {
        log.info("Searching for entity User with id: {}, included deleted ones.", id);
        Optional<UserModel> object = userRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No entity User with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<UserModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UserModel> findByIdAndRemovedIsNull(Long id) {
        log.info("Searching for entity User with id: {}.", id);
        Optional<UserModel> object = userRepository.findByIdAndRemovedIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity User with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        } else {
            String message = "One entity User was found.";
            log.info(message);
            return new EntityMessenger<UserModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UserModel> findAll() {
        log.info("Searching for all entities User, included deleted ones.");
        List<UserModel> list = userRepository.findAll();
        if (list.isEmpty()) {
            String message = "No entities User were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities User were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<UserModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<UserModel> findAllByRemovedIsNull() {
        log.info("Searching for all entities User.");
        List<UserModel> list = userRepository.findAllByRemovedIsNull();
        if (list.isEmpty()) {
            String message = "No entities User were found.";
            log.warn(message);
            return new EntityMessenger<UserModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities User were found.";
            log.info(message);
            return new EntityMessenger<UserModel>(null, list, message, 200);
        }
    }

    @Override
    public Long countAll() {
        Long count = userRepository.count();
        log.info("Table User possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public Long countAllByRemovedIsNull() {
        Long count = userRepository.countAllByRemovedIsNull();
        log.info("Table User possess {} entities.", count);
        return count;
    }

    @Override
    public EntityMessenger<UserModel> insert(UserModel newUser) {
        try {
            log.info("Inserting entity User: {}.",  newUser);
            newUser.setCreator(this.getCurrentUser().getObject());
            newUser.setCreated(Helper.getNow(""));
            UserModel userModel = userRepository.save(newUser);
            String message = "The entity User with id: " + userModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<UserModel>(userModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<UserModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UserModel> update(UserModel updateUser) {
        try {
            log.info("Updating entity User: {}.",  updateUser);
            if (updateUser.getId() != null) {
                EntityMessenger<UserModel> existant = this.findByIdAndRemovedIsNull(updateUser.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            updateUser.setModifier(this.getCurrentUser().getObject());
            updateUser.setModified(Helper.getNow(""));
            UserModel userModel = userRepository.save(updateUser);
            String message = "The entity User with id: " + userModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<UserModel>(userModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<UserModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UserModel> recycle(Long id) {
        log.info("Recycling entity User with: {}.", id);
        EntityMessenger<UserModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getRemoved() == null) {
            String message = "The entity User with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setRemoved(null);
        entityMessenger.getObject().setRemover(null);
        entityMessenger.setObject(userRepository.save(entityMessenger.getObject()));
        String message = "The entity User with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UserModel> delete(Long id) {
        log.info("Deleting entity User with: {}.", id);
        EntityMessenger<UserModel> entityMessenger = this.findByIdAndRemovedIsNull(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setRemover(this.getCurrentUser().getObject());
        entityMessenger.getObject().setRemoved(Helper.getNow(""));
        entityMessenger.setObject(userRepository.save(entityMessenger.getObject()));
        String message = "The entity User with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UserModel> destroy(Long id) {
        log.info("Destroying entity User with: {}.", id);
        EntityMessenger<UserModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getRemoved() == null) {
            String message = "The entity User with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        userRepository.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
