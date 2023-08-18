package muni.eolida.sisifo.helper.email.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.email.repository.EmailDAO;
import muni.eolida.sisifo.helper.email.EmailModel;
import muni.eolida.sisifo.helper.email.mapper.EmailCreation;
import muni.eolida.sisifo.helper.email.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("emailSenderService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailDAO emailDAO;
    @Autowired
    EmailMapper emailMapper;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public EntityMessenger<EmailModel> sendSimpleEmail(EmailCreation emailCreation) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailCreation.getSender());
            message.setSubject(emailCreation.getSubject());
            message.setText(emailCreation.getBody());
            message.setTo(emailCreation.getReciever());
            javaMailSender.send(message);
            log.info("Email succesufuly sended: {}.", message);
            return this.insert(emailCreation);
        } catch (Exception e) {
            String message = "An error occurred while trying to persisit the entity. Exception: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> findById(Long id) {
        log.info("Searching for entity Email with id: {}.", id);
        Optional<EmailModel> object = emailDAO.findById(id);
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
        List<EmailModel> list = emailDAO.findAll();
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
        Long count = emailDAO.count();
        log.info("Table Email possess {} entities.", count);
        return count;
    }

    @Override
    public EntityMessenger<EmailModel> insert(EmailCreation model) {
        try {
            log.info("Inserting entity Email: {}.",  model);
            EmailModel emailModel = emailDAO.save(emailMapper.toEntity(model));
            emailModel.setSended(Helper.getNow(""));
            emailDAO.save(emailModel);
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
    public EntityMessenger<EmailModel> destroy(Long id) {
        log.info("Destroying entity Email with: {}.", id);
        EntityMessenger<EmailModel> entityMessenger = this.findById(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        emailDAO.delete(entityMessenger.getObject());
        String message = "The entity was destroyed.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
