package muni.eolida.sisifo.mapper;

import com.gloit.epione.helper.EntityMessenger;
import com.gloit.epione.helper.Helper;
import com.gloit.epione.mapper.creation.EmailCreation;
import com.gloit.epione.mapper.dto.EmailDataTransferObject;
import com.gloit.epione.model.EmailModel;
import com.gloit.epione.model.UserModel;
import com.gloit.epione.service.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailMapper {
    private final UserServiceImplementation userServiceImplementation;
    private final UsuarioMapper usuarioMapper;

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

            if (emailModel.getCreator() != null)
                dto.setCreator(usuarioMapper.toDto(emailModel.getCreator()));
            if (emailModel.getCreated() != null)
                dto.setCreated(Helper.localDateTimeToString(emailModel.getCreated(), ""));
            if (emailModel.getModifier() != null)
                dto.setModifier(usuarioMapper.toDto(emailModel.getModifier()));
            if (emailModel.getModified() != null)
                dto.setModified(Helper.localDateTimeToString(emailModel.getModified(), ""));
            if (emailModel.getRemover() != null)
                dto.setRemover(usuarioMapper.toDto(emailModel.getRemover()));
            if (emailModel.getRemoved() != null)
                dto.setRemoved(Helper.localDateTimeToString(emailModel.getRemoved(), ""));

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

            if (!Helper.isEmptyString(emailCreation.getCreator())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(emailCreation.getCreator());
                if (user.getStatusCode() == 200)
                    emailModel.setCreator(user.getObject());
            }
            if (!Helper.isEmptyString(emailCreation.getCreated()))
                emailModel.setCreated(Helper.stringToLocalDateTime(emailCreation.getCreated(), ""));
            if (!Helper.isEmptyString(emailCreation.getModifier())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(emailCreation.getModifier());
                if (user.getStatusCode() == 200)
                    emailModel.setModifier(user.getObject());
            }
            if (!Helper.isEmptyString(emailCreation.getModified()))
                emailModel.setModified(Helper.stringToLocalDateTime(emailCreation.getModified(), ""));
            if (!Helper.isEmptyString(emailCreation.getRemover())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(emailCreation.getRemover());
                if (user.getStatusCode() == 200)
                    emailModel.setRemover(user.getObject());
            }
            if (!Helper.isEmptyString(emailCreation.getRemoved()))
                emailModel.setRemoved(Helper.stringToLocalDateTime(emailCreation.getRemoved(), ""));

            return emailModel;
        } catch (Exception e) {
            log.info("Email creation to entity error. Exception: " + e);
            return null;
        }
    }
}