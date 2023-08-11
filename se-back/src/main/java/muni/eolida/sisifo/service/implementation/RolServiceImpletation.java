package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.repository.RolDAO;
import muni.eolida.sisifo.service.RolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RolServiceImpletation implements RolService {

    private final RolDAO rolDAO;

    @Override
    public EntityMessenger<AuthorityModel> findByAuthority(String authority) {
        try {
            log.info("Searching for entity AuthorityModel with name: {}, included deleted ones.", authority);
            Optional<AuthorityModel> object = authorityRepository.findByAuthority(AuthorityEnum.valueOf(authority));
            if (object.isEmpty()) {
                String message = "No entity AuthorityModel with name: " + authority + " was found, included deleted ones.";
                log.warn(message);
                return new EntityMessenger<AuthorityModel>(null, null, message, 202);
            } else {
                String message = "One entity AuthorityModel was found, included deleted ones.";
                log.info(message);
                return new EntityMessenger<AuthorityModel>(object.get(), null, message, 200);
            }
        } catch (Exception e) {
            String message = "An error occurred while trying to find the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> findByAuthorityAndRemovedIsNull(String authority) {
        try {
            log.info("Searching for entity AuthorityModel with name: {}.", authority);
            Optional<AuthorityModel> object = authorityRepository.findByAuthorityAndRemovedIsNull(AuthorityEnum.valueOf(authority));
            if (object.isEmpty()) {
                String message = "No entity AuthorityModel with name: " + authority + " was found.";
                log.warn(message);
                return new EntityMessenger<AuthorityModel>(null, null, message, 202);
            } else {
                String message = "One entity AuthorityModel was found.";
                log.info(message);
                return new EntityMessenger<AuthorityModel>(object.get(), null, message, 200);
            }
        } catch (Exception e) {
            String message = "An error occurred while trying to find the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> findById(Long id) {
        log.info("Searching for entity AuthorityModel with id: {}, included deleted ones.", id);
        Optional<AuthorityModel> object = authorityRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No entity AuthorityModel with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 202);
        } else {
            String message = "One entity AuthorityModel was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<AuthorityModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> findByIdAndRemovedIsNull(Long id) {
        log.info("Searching for entity AuthorityModel with id: {}.", id);
        Optional<AuthorityModel> object = authorityRepository.findByIdAndRemovedIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity AuthorityModel with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 202);
        } else {
            String message = "One entity AuthorityModel was found.";
            log.info(message);
            return new EntityMessenger<AuthorityModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> findAll() {
        log.info("Searching for all entities AuthorityModel, included deleted ones.");
        List<AuthorityModel> list = authorityRepository.findAll();
        if (list.isEmpty()) {
            String message = "No entities AuthorityModel were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities AuthorityModel were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<AuthorityModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> findAllByRemovedIsNull() {
        log.info("Searching for all entities AuthorityModel.");
        List<AuthorityModel> list = authorityRepository.findAllByRemovedIsNull();
        if (list.isEmpty()) {
            String message = "No entities AuthorityModel were found.";
            log.warn(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities AuthorityModel were found.";
            log.info(message);
            return new EntityMessenger<AuthorityModel>(null, list, message, 200);
        }
    }

    @Override
    public Long countAll() {
        Long count = authorityRepository.count();
        log.info("Table AuthorityModel possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public Long countAllByRemovedIsNull() {
        Long count = authorityRepository.countAllByRemovedIsNull();
        log.info("Table AuthorityModel possess {} entities.", count);
        return count;
    }

    @Override
    public EntityMessenger<AuthorityModel> insert(AuthorityModel newAuthority) {
        try {
            log.info("Inserting entity AuthorityModel: {}.",  newAuthority);
            newAuthority.setCreated(Helper.getNow(""));
            AuthorityModel authorityModel = authorityRepository.save(newAuthority);
            String message = "The entity AuthorityModel with id: " + authorityModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<AuthorityModel>(authorityModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> update(AuthorityModel updateAuthority) {
        try {
            log.info("Updating entity AuthorityModel: {}.",  updateAuthority);
            if (updateAuthority.getId() != null) {
                EntityMessenger<AuthorityModel> existant = this.findByIdAndRemovedIsNull(updateAuthority.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            updateAuthority.setModified(Helper.getNow(""));
            AuthorityModel authorityModel = authorityRepository.save(updateAuthority);
            String message = "The entity AuthorityModel with id: " + authorityModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<AuthorityModel>(authorityModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<AuthorityModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<AuthorityModel> recycle(Long id) {
        log.info("Recycling entity AuthorityModel with: {}.", id);
        EntityMessenger<AuthorityModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getRemoved() == null) {
            String message = "The entity AuthorityModel with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setRemoved(null);
        entityMessenger.getObject().setRemover(null);
        entityMessenger.setObject(authorityRepository.save(entityMessenger.getObject()));
        String message = "The entity AuthorityModel with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<AuthorityModel> delete(Long id) {
        log.info("Deleting entity AuthorityModel with: {}.", id);
        EntityMessenger<AuthorityModel> entityMessenger = this.findByIdAndRemovedIsNull(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setRemoved(Helper.getNow(""));
        entityMessenger.setObject(authorityRepository.save(entityMessenger.getObject()));
        String message = "The entity AuthorityModel with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<AuthorityModel> destroy(Long id) {
        log.info("Destroying entity AuthorityModel with: {}.", id);
        EntityMessenger<AuthorityModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getRemoved() == null) {
            String message = "The entity AuthorityModel with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        authorityRepository.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
