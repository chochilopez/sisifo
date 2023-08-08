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
            dto.setNombre(emailModel.getNombre());
            dto.setTelefono(emailModel.getTelefono());
            dto.setEmisor(emailModel.getEmisor());
            dto.setReceptor(emailModel.getReceptor());
            dto.setCc(emailModel.getCc());
            dto.setAsunto(emailModel.getAsunto());
            dto.setEnviado(emailModel.getEnviado().toString());
            dto.setTexto(emailModel.getTexto());
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
            emailModel.setNombre(emailCreation.getNombre());
            emailModel.setTelefono(emailCreation.getTelefono());
            emailModel.setEmisor(emailCreation.getEmisor());
            emailModel.setReceptor(emailCreation.getReceptor());
            emailModel.setCc(emailCreation.getCc());
            emailModel.setAsunto(emailCreation.getAsunto());
            if (!Helper.isEmptyString(emailCreation.getEnviado()))
                emailModel.setEnviado(Boolean.parseBoolean(emailCreation.getEnviado()));
            emailModel.setTexto(emailCreation.getTexto());
            emailModel.setError(emailCreation.getError());

            return emailModel;
        } catch (Exception e) {
            log.info("Email creation to entity error. Exception: " + e);
            return null;
        }
    }
}