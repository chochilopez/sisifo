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
    public EntityMessenger<EmailModel> enviarEmailSimple(EmailModel emailModel) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String text = "\nDe: " + emailModel.getNombre() + "\nTelefono: " + emailModel.getTelefono() +
                    "\nEmail: " + emailModel.getEmisor() + "\nConsulta: " + emailModel.getTexto();
            message.setFrom(emailModel.getEmisor());
            message.setSubject(emailModel.getAsunto());
            message.setText(text);
            message.setTo(emailModel.getReceptor());
            message.setCc(emailModel.getCc());
            javaMailSender.send(message);
            log.info("Email enviado correctamente: {}.", message);
            return this.insertar(emailModel);
        } catch (Exception e) {
            String message = "Ocurrio un error al enviar el email. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Email con id: {}.", id);
        Optional<EmailModel> object = emailRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No existe la entidad Email con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<EmailModel>(null, null, message, 202);
        } else {
            String message = "Una entidad Email encontrada.";
            log.info(message);
            return new EntityMessenger<EmailModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<EmailModel> buscarTodos() {
        log.info("Buscando todas las entidades Email.");
        List<EmailModel> list = emailRepository.findAll();
        if (list.isEmpty()) {
            String message = "No existen entidades Email.";
            log.warn(message);
            return new EntityMessenger<EmailModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades Email fueron encontradas.";
            log.info(message);
            return new EntityMessenger<EmailModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = emailRepository.count();
        log.info("Existen {} entidades Email.", count);
        return count;
    }

    @Override
    public EntityMessenger<EmailModel> insertar(EmailModel newEmail) {
        try {
            log.info("Insertando entidad Email: {}.",  newEmail);
            EmailModel emailModel = emailRepository.save(newEmail);
            String message = "La entidad Email con id: " + emailModel.getId() + " fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<EmailModel>(emailModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> actualizar(EmailModel actualizarEmail) {
        try {
            log.info("Actualizando entidad Email: {}.",  actualizarEmail);
            if (actualizarEmail.getId() != null) {
                EntityMessenger<EmailModel> existant = this.buscarPorId(actualizarEmail.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            EmailModel emailModel = emailRepository.save(actualizarEmail);
            String message = "La entidad Email con id: " + emailModel.getId() + "fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<EmailModel>(emailModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<EmailModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> destruir(Long id) {
        log.info("Destruyendo la entidad Email con id: {}.", id);
        EntityMessenger<EmailModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        emailRepository.delete(entityMessenger.getObject());
        String message = "La entidad fue destruida.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
