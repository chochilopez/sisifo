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
import muni.eolida.sisifo.mapper.dto.ArchivoDTO;
import muni.eolida.sisifo.mapper.dto.CalleDTO;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReclamoMapper {
    private final UserServiceImplementation userServiceImplementation;
    private final UsuarioMapper usuarioMapper;

    public EmailModel toEntity(EmailCreation emailCreation) {
        try {
            private String id;
            private String altura;
            private String barrio;
            private String calleModel;
            private String calleInterseccion;
            private String coordinadaX;
            private String coordinadaY;
            private String descripcion;
            private String entreCalle1;
            private String entreCalle2;
            private String imagen;
            private String numero;
            private String persona;
            private String tipoReclamo;

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

    public EmailDataTransferObject toDto(EmailModel emailModel) {
        try {
            private String id;
            private String altura;
            private String barrio;
            private CalleDTO calleModel;
            private CalleDTO calleInterseccion;
            private String coordinadaX;
            private String coordinadaY;
            private String descripcion;
            private CalleDTO entreCalle1;
            private CalleDTO entreCalle2;
            private ArchivoDTO imagen;
            private String numero;
            private UsuarioDTO persona;
            private TipoReclamoDTO tipoReclamo;

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
}