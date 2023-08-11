package muni.eolida.sisifo.mapper;

import com.gloit.epione.helper.EntityMessenger;
import com.gloit.epione.helper.Helper;
import com.gloit.epione.mapper.creation.AuthorityCreation;
import com.gloit.epione.mapper.dto.AuthorityDataTransferObject;
import com.gloit.epione.model.AuthorityModel;
import com.gloit.epione.model.UserModel;
import com.gloit.epione.model.enumerator.AuthorityEnum;
import com.gloit.epione.service.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RolMapper {
    private final UserServiceImplementation userServiceImplementation;

    public AuthorityModel toEntity(AuthorityCreation authorityCreation) {
        try {
            log.info("AuthorityModel creation to entity.");
            AuthorityModel authorityModel = new AuthorityModel();

            if (!Helper.isEmptyString(authorityCreation.getId()))
                authorityModel.setId(Helper.getLong(authorityCreation.getId()));
            if (!Helper.isEmptyString(authorityCreation.getAuthority()))
                authorityModel.setAuthority(AuthorityEnum.valueOf(authorityCreation.getAuthority()));

            if (!Helper.isEmptyString(authorityCreation.getCreator())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(authorityCreation.getCreator());
                if (user.getStatusCode() == 200)
                    authorityModel.setCreator(user.getObject());
            }
            if (!Helper.isEmptyString(authorityCreation.getCreated()))
                authorityModel.setCreated(Helper.stringToLocalDateTime(authorityCreation.getCreated(), ""));
            if (!Helper.isEmptyString(authorityCreation.getModifier())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(authorityCreation.getModifier());
                if (user.getStatusCode() == 200)
                    authorityModel.setModifier(user.getObject());
            }
            if (!Helper.isEmptyString(authorityCreation.getModified()))
                authorityModel.setModified(Helper.stringToLocalDateTime(authorityCreation.getModified(), ""));
            if (!Helper.isEmptyString(authorityCreation.getRemover())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(authorityCreation.getRemover());
                if (user.getStatusCode() == 200)
                    authorityModel.setRemover(user.getObject());
            }
            if (!Helper.isEmptyString(authorityCreation.getRemoved()))
                authorityModel.setRemoved(Helper.stringToLocalDateTime(authorityCreation.getRemoved(), ""));

            return authorityModel;
        } catch (Exception e) {
            log.info("AuthorityModel creation to entity error. Exception: " + e);
            return null;
        }
    }

    public AuthorityDataTransferObject toDto(AuthorityModel authorityModel) {
        try {
            private String id;
            private String rol;

            log.info("AuthorityModel entity to dto.");
            AuthorityDataTransferObject dto = new AuthorityDataTransferObject();

            dto.setId(authorityModel.getId().toString());
            dto.setAuthority(authorityModel.getAuthority().toString());

            if (authorityModel.getCreated() != null)
                dto.setCreated(Helper.localDateTimeToString(authorityModel.getCreated(), ""));
            if (authorityModel.getModified() != null)
                dto.setModified(Helper.localDateTimeToString(authorityModel.getModified(), ""));
            if (authorityModel.getRemoved() != null)
                dto.setRemoved(Helper.localDateTimeToString(authorityModel.getRemoved(), ""));

            return dto;
        } catch (Exception e) {
            log.info("AuthorityModel entity to dto error. Exception: " + e);
            return null;
        }

    }
}
