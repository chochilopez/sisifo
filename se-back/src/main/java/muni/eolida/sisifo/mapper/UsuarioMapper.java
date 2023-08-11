package com.gloit.epione.mapper;

import com.gloit.epione.helper.EntityMessenger;
import com.gloit.epione.helper.Helper;
import com.gloit.epione.mapper.creation.UserCreation;
import com.gloit.epione.mapper.dto.AuthorityDataTransferObject;
import com.gloit.epione.mapper.dto.UserDataTransferObject;
import com.gloit.epione.model.*;
import com.gloit.epione.model.UserModel;
import com.gloit.epione.model.enumerator.AuthMediaEnum;
import com.gloit.epione.model.enumerator.AuthorityEnum;
import com.gloit.epione.service.implementation.AuthorityServiceImplementation;
import com.gloit.epione.service.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {
    private final UserServiceImplementation userServiceImplementation;
    private final AuthorityServiceImplementation authorityServiceImplementation;
    private final AuthorityMapper authorityMapper;

    public UserDataTransferObject toDto(UserModel userModel) {
        try {
            log.info("User entity to dto.");
            UserDataTransferObject dto = new UserDataTransferObject();
            List<AuthorityDataTransferObject> objectList = new ArrayList<>();

            dto.setId(userModel.getId().toString());
            dto.setUsername(userModel.getUsername());
            List<String> authorities = new ArrayList<>();
            for (AuthorityModel authorityModel:userModel.getAuthorities()) {
                authorities.add(authorityModel.getAuthority().name());
            }
            dto.setAuthorities(authorities);
            dto.setEnabled(userModel.getEnabled().toString());

            if (userModel.getCreator() != null)
                dto.setCreator(this.toDto(userModel.getCreator()));
            if (userModel.getCreated() != null)
                dto.setCreated(Helper.localDateTimeToString(userModel.getCreated(), ""));
            if (userModel.getModifier() != null)
                dto.setModifier(this.toDto(userModel.getModifier()));
            if (userModel.getModified() != null)
                dto.setModified(Helper.localDateTimeToString(userModel.getModified(), ""));
            if (userModel.getRemover() != null)
                dto.setRemover(this.toDto(userModel.getRemover()));
            if (userModel.getRemoved() != null)
                dto.setRemoved(Helper.localDateTimeToString(userModel.getRemoved(), ""));

            return dto;
        } catch (Exception e) {
            log.info("User entity to dto error. Exception: " + e);
            return null;
        }
    }

    public UserModel toEntity(UserCreation userCreation) {
        try {
            log.info("User creation to entity.");
            UserModel userModel = new UserModel();

            if (!Helper.isEmptyString(userCreation.getId()))
                userModel.setId(Helper.getLong(userCreation.getId()));
            userModel.setUsername(userCreation.getUsername());
            userModel.setPassword(userCreation.getPassword());
            userModel.setEnabled(Boolean.valueOf(userCreation.getEnabled()));
            if (!userCreation.getAuthorities().isEmpty()) {
                HashSet<AuthorityModel> authorityModels = new HashSet<>();
                EntityMessenger<AuthorityModel> authorityModelEntityMessenger;
                for (String authId : userCreation.getAuthorities()) {
                    if (Helper.getLong(authId) != null) {
                        authorityModelEntityMessenger = authorityServiceImplementation.findByIdAndRemovedIsNull(Helper.getLong(authId));
                        if (authorityModelEntityMessenger.getStatusCode() == 200)
                            authorityModels.add(authorityModelEntityMessenger.getObject());
                    }
                }
            }
            if (!Helper.isEmptyString(userCreation.getCreator())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(userCreation.getCreator());
                if (user.getStatusCode() == 200)
                    userModel.setCreator(user.getObject());
            }
            if (!Helper.isEmptyString(userCreation.getCreated()))
                userModel.setCreated(Helper.stringToLocalDateTime(userCreation.getCreated(), ""));
            if (!Helper.isEmptyString(userCreation.getModifier())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(userCreation.getModifier());
                if (user.getStatusCode() == 200)
                    userModel.setModifier(user.getObject());
            }
            if (!Helper.isEmptyString(userCreation.getModified()))
                userModel.setModified(Helper.stringToLocalDateTime(userCreation.getModified(), ""));
            if (!Helper.isEmptyString(userCreation.getRemover())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(userCreation.getRemover());
                if (user.getStatusCode() == 200)
                    userModel.setRemover(user.getObject());
            }
            if (!Helper.isEmptyString(userCreation.getRemoved()))
                userModel.setRemoved(Helper.stringToLocalDateTime(userCreation.getRemoved(), ""));

            return userModel;
        } catch (Exception e) {
            log.info("User creation to entity error. Exception: " + e);
            return null;
        }
    }
}
