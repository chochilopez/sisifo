package muni.eolida.sisifo.mapper;

import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.EmailCreation;
import muni.eolida.sisifo.mapper.dto.EmailDataTransferObject;
import muni.eolida.sisifo.model.EmailModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailMapper {
    public EmailDataTransferObject toDto(EmailModel emailModel) {
        try {
            log.info("Email entity to dto.");
            EmailDataTransferObject dto = new EmailDataTransferObject();

            dto.setId(emailModel.getId().toString());
            dto.setName(emailModel.getName());
            dto.setTelephone(emailModel.getTelephone());
            dto.setSender(emailModel.getSender());
            dto.setRecepient(emailModel.getRecepient());
            dto.setCarbonCopy(emailModel.getCarbonCopy());
            dto.setSubject(emailModel.getSubject());
            dto.setSended(emailModel.getSended().toString());
            dto.setBody(emailModel.getBody());
            dto.setError(emailModel.getError());

            return dto;
        } catch (Exception e) {
            log.info("Email entity to dto error. Exception: " + e);
            return null;
        }
    }

    public EmailModel toEntity(EmailCreation emailCreation) {
        try {
            log.info("Email creation to entity.");
            EmailModel emailModel = new EmailModel();

            if (!Helper.isEmptyString(emailCreation.getId()))
                emailModel.setId(Helper.getLong(emailCreation.getId()));
            emailModel.setName(emailCreation.getName());
            emailModel.setTelephone(emailCreation.getTelephone());
            emailModel.setSender(emailCreation.getSender());
            emailModel.setRecepient(emailCreation.getRecepient());
            emailModel.setCarbonCopy(emailCreation.getCarbonCopy());
            emailModel.setSubject(emailCreation.getSubject());
            if (!Helper.isEmptyString(emailCreation.getSended()))
                emailModel.setSended(Boolean.parseBoolean(emailCreation.getSended()));
            emailModel.setBody(emailCreation.getBody());
            emailModel.setError(emailCreation.getError());

            return emailModel;
        } catch (Exception e) {
            log.info("Email creation to entity error. Exception: " + e);
            return null;
        }
    }
}