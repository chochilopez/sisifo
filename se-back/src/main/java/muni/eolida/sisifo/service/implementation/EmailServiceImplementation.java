package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.EmailModel;
import muni.eolida.sisifo.repository.EmailDAO;
import muni.eolida.sisifo.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImplementation implements EmailService {

    private final EmailDAO emailDAO;
    JavaMailSender javaMailSender;

    @Override
    public EntityMessenger<EmailModel> sendSimpleMail(EmailModel emailModel) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String text = "\nDe: " + emailModel.getName() + "\nTelefono: " + emailModel.getTelephone() +
                    "\nEmail: " + emailModel.getSender() + "\nConsulta: " + emailModel.getBody();
            message.setFrom(emailModel.getSender());
            message.setSubject(emailModel.getSubject());
            message.setText(text);
            message.setTo(emailModel.getRecepient());
            message.setCc(emailModel.getCarbonCopy());
            javaMailSender.send(message);
            log.info("Email succesufuly sended: {}.", message);
            return this.insert(emailModel);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> findById(Long id) {
        log.info("Searching for entity Email with id: {}, included deleted ones.", id);
        Optional<EmailModel> object = emailRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No entity Email with id: " + id + " was found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<EmailModel>(null, null, message, 202);
        } else {
            String message = "One entity Email was found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<EmailModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EmailModel> findByIdAndRemovedIsNull(Long id) {
        log.info("Searching for entity Email with id: {}.", id);
        Optional<EmailModel> object = emailRepository.findByIdAndRemovedIsNull(id);
        if (object.isEmpty()) {
            String message = "No entity Email with id: " + id + " was found.";
            log.warn(message);
            return new EntityMessenger<EmailModel>(null, null, message, 202);
        } else {
            String message = "One entity Email was found.";
            log.info(message);
            return new EntityMessenger<EmailModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EmailModel> findAll() {
        log.info("Searching for all entities Email, included deleted ones.");
        List<EmailModel> list = emailRepository.findAll();
        if (list.isEmpty()) {
            String message = "No entities Email were found, included deleted ones.";
            log.warn(message);
            return new EntityMessenger<EmailModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Email were found, included deleted ones.";
            log.info(message);
            return new EntityMessenger<EmailModel>(null, list, message, 200);
        }
    }

    @Override
    public EntityMessenger<EmailModel> findAllByRemovedIsNull() {
        log.info("Searching for all entities Email.");
        List<EmailModel> list = emailRepository.findAllByRemovedIsNull();
        if (list.isEmpty()) {
            String message = "No entities Email were found.";
            log.warn(message);
            return new EntityMessenger<EmailModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entities Email were found.";
            log.info(message);
            return new EntityMessenger<EmailModel>(null, list, message, 200);
        }
    }

    @Override
    public Long countAll() {
        Long count = emailRepository.count();
        log.info("Table Email possess {} entities, included deleted ones.", count);
        return count;
    }

    @Override
    public Long countAllByRemovedIsNull() {
        Long count = emailRepository.countAllByRemovedIsNull();
        log.info("Table Email possess {} entities.", count);
        return count;
    }

    @Override
    public EntityMessenger<EmailModel> insert(EmailModel newEmail) {
        try {
            log.info("Inserting entity Email: {}.",  newEmail);
            newEmail.setCreator(userServiceImplementation.getCurrentUser().getObject());
            newEmail.setCreated(Helper.getNow(""));
            EmailModel emailModel = emailRepository.save(newEmail);
            String message = "The entity Email with id: " + emailModel.getId() + " was inserted correctly.";
            log.info(message);
            return new EntityMessenger<EmailModel>(emailModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> update(EmailModel updateEmail) {
        try {
            log.info("Updating entity Email: {}.",  updateEmail);
            if (updateEmail.getId() != null) {
                EntityMessenger<EmailModel> existant = this.findByIdAndRemovedIsNull(updateEmail.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            updateEmail.setModifier(userServiceImplementation.getCurrentUser().getObject());
            updateEmail.setModified(Helper.getNow(""));
            EmailModel emailModel = emailRepository.save(updateEmail);
            String message = "The entity Email with id: " + emailModel.getId() + " was updated correctly.";
            log.info(message);
            return new EntityMessenger<EmailModel>(emailModel, null, message, 201);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> recycle(Long id) {
        log.info("Recycling entity Email with: {}.", id);
        EntityMessenger<EmailModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getRemoved() == null) {
            String message = "The entity Email with id: " + id + " was not deleted.";
            log.warn(message);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        entityMessenger.getObject().setRemoved(null);
        entityMessenger.getObject().setRemover(null);
        entityMessenger.setObject(emailRepository.save(entityMessenger.getObject()));
        String message = "The entity Email with id: " + id + " was recycled correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<EmailModel> delete(Long id) {
        log.info("Deleting entity Email with: {}.", id);
        EntityMessenger<EmailModel> entityMessenger = this.findByIdAndRemovedIsNull(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        entityMessenger.getObject().setRemover(userServiceImplementation.getCurrentUser().getObject());
        entityMessenger.getObject().setRemoved(Helper.getNow(""));
        entityMessenger.setObject(emailRepository.save(entityMessenger.getObject()));
        String message = "The entity Email with id: " + id + " was deleted correctly.";
        entityMessenger.setMessage(message);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<EmailModel> destroy(Long id) {
        log.info("Destroying entity Email with: {}.", id);
        EntityMessenger<EmailModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        if (entityMessenger.getObject().getRemoved() == null) {
            String message = "The entity Email with id: " + id + " was not deleted correctly, thus, cannot be destroyed.";
            log.info(message);
            entityMessenger.setStatusCode(202);
            entityMessenger.setMessage(message);
            return entityMessenger;
        }
        emailRepository.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
