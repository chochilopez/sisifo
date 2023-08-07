package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.VisitModel;
import muni.eolida.sisifo.repository.VisitRepository;
import muni.eolida.sisifo.service.VisitServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class VisitServiceImplementation implements VisitServiceInterface {

    private final VisitRepository visitRepository;

    @Override
    public EntityMessenger<VisitModel> findAllByIp(String ip) {
        log.info("Searching for entities Visit with ip: {}.", ip);
        List<VisitModel> visitModels = visitRepository.findAllByIpContaining(ip);
        if (visitModels.isEmpty()) {
            String message = "No entities Visit was found with ip adrress " + ip + ".";
            log.warn(message);
            return new EntityMessenger<VisitModel>(null, null, message, 202);
        } else {
            String message = visitModels.size() + " entities Visit was found with ip adrress " + ip + ".";
            log.info(message);
            return new EntityMessenger<VisitModel>(null, visitModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitModel> findTopN(int number) {
        log.info("Searching for top " + number + " entities in Visit.");
        List<VisitModel> visitModels = visitRepository.findTopN(number);
        if (visitModels.isEmpty()) {
            String message = "No entities Visit was found.";
            log.warn(message);
            return new EntityMessenger<VisitModel>(null, null, message, 202);
        } else {
            String message = visitModels.size() + " entities Visit was found.";
            log.info(message);
            return new EntityMessenger<VisitModel>(null, visitModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitModel> findById(Long id) {
        log.info("Searching for entity Visit with id: {}.", id);
        Optional<VisitModel> object = visitRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No entity Visit with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<VisitModel>(null, null, message, 202);
        } else {
            String message = "One entity Visit was found.";
            log.info(message);
            return new EntityMessenger<VisitModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitModel> findAll() {
        log.info("Searching for all entities Visit.");
        List<VisitModel> list = visitRepository.findAll();
        if (list.isEmpty()) {
            String message = "No entities Visit were found.";
            log.warn(message);
            return new EntityMessenger<VisitModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Visit were found.";
            log.info(message);
            return new EntityMessenger<VisitModel>(null, list, message, 200);
        }
    }

    @Override
    public Long countAll() {
        Long count = visitRepository.count();
        log.info("Table Visit possess {} entities.", count);
        return count;
    }

    @Override
    public EntityMessenger<VisitModel> insert(VisitModel newVisit) {
        try {
            log.info("Inserting entity Visit: {}.",  newVisit);
            VisitModel visitModel = visitRepository.save(newVisit);
            String message = "The entity Visit with id: " + visitModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<VisitModel>(visitModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<VisitModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<VisitModel> update(VisitModel updateVisit) {
        try {
            log.info("Updating entity Visit: {}.",  updateVisit);
            if (updateVisit.getId() != null) {
                EntityMessenger<VisitModel> existant = this.findById(updateVisit.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            VisitModel visitModel = visitRepository.save(updateVisit);
            String message = "The entity Visit with id: " + visitModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<VisitModel>(visitModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<VisitModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<VisitModel> destroy(Long id) {
        log.info("Destroying entity Visit with: {}.", id);
        EntityMessenger<VisitModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        visitRepository.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
