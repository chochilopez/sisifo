package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.EmailModel;
import muni.eolida.sisifo.repository.EmailRepository;
import muni.eolida.sisifo.service.EmailServiceInterface;
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
public class EmailServiceImplementation implements EmailServiceInterface {

    private final EmailRepository emailRepository;
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
            log.info("Email enviado correctamente: {}.", message);
            return this.insert(emailModel);
        } catch (Exception e) {
            String message = "Ocurrio un error al enviar el email. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> findById(Long id) {
        log.info("Searching for entity Email with id: {}.", id);
        Optional<EmailModel> object = emailRepository.findById(id);
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
        log.info("Searching for all entities Email.");
        List<EmailModel> list = emailRepository.findAll();
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
        log.info("Table Email possess {} entities.", count);
        return count;
    }

    @Override
    public EntityMessenger<EmailModel> insert(EmailModel newEmail) {
        try {
            log.info("Inserting entity Email: {}.",  newEmail);
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
                EntityMessenger<EmailModel> existant = this.findById(updateEmail.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
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
    public EntityMessenger<EmailModel> destroy(Long id) {
        log.info("Destroying entity Email with: {}.", id);
        EntityMessenger<EmailModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
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
