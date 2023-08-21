package muni.eolida.sisifo.util.email.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.email.EmailModel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailMapper {

    public EmailModel toEntity(EmailCreation emailCreation) {
        try {
            log.info("Email creation to entity.");
            EmailModel emailModel = new EmailModel();

            emailModel.setSubject(emailCreation.getSubject());
            emailModel.setSender(emailCreation.getSender());
            emailModel.setReciever(emailCreation.getReciever());
            emailModel.setBody(emailCreation.getBody());

            return emailModel;
        } catch (Exception e) {
            log.info("Email creation to entity error. Exception: " + e);
            return null;
        }
    }

    public EmailDTO toDto(EmailModel emailModel) {
        try {
            log.info("Email entity to dto.");
            EmailDTO dto = new EmailDTO();

            dto.setSubject(emailModel.getSubject());
            dto.setCc(emailModel.getCc());
            dto.setSender(emailModel.getSender());
            dto.setReciever(emailModel.getReciever());
            dto.setBody(emailModel.getBody());
            dto.setSended(emailModel.getSended().toString());

            return dto;
        } catch (Exception e) {
            log.info("Email entity to dto error. Exception: " + e);
            return null;
        }
    }
}