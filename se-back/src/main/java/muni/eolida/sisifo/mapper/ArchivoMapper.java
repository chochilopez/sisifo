package com.gloit.epione.mapper;

import com.gloit.epione.helper.EntityMessenger;
import com.gloit.epione.helper.Helper;
import com.gloit.epione.mapper.creation.LocalFileCreation;
import com.gloit.epione.mapper.dto.LocalFileDataTransferObject;
import com.gloit.epione.model.LocalFileModel;
import com.gloit.epione.model.UserModel;
import com.gloit.epione.model.enumerator.FiletypeEnum;
import com.gloit.epione.service.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalFileMapper {
    private final UserServiceImplementation userServiceImplementation;
    private final UserMapper userMapper;

    public LocalFileDataTransferObject toDto(LocalFileModel localFileModel) {
        try {
            log.info("LocalFile entity to dto.");
            LocalFileDataTransferObject dto = new LocalFileDataTransferObject();

            dto.setId(localFileModel.getId().toString());
            dto.setPath(localFileModel.getPath());
            dto.setFilename(localFileModel.getFilename());
            dto.setFiletype(localFileModel.getFiletype().toString());
            dto.setDescription(localFileModel.getDescription());
            dto.setFilesize(localFileModel.getFilesize());

            if (localFileModel.getCreator() != null)
                dto.setCreator(userMapper.toDto(localFileModel.getCreator()));
            if (localFileModel.getCreated() != null)
                dto.setCreated(Helper.localDateTimeToString(localFileModel.getCreated(), ""));
            if (localFileModel.getModifier() != null)
                dto.setModifier(userMapper.toDto(localFileModel.getModifier()));
            if (localFileModel.getModified() != null)
                dto.setModified(Helper.localDateTimeToString(localFileModel.getModified(), ""));
            if (localFileModel.getRemover() != null)
                dto.setRemover(userMapper.toDto(localFileModel.getRemover()));
            if (localFileModel.getRemoved() != null)
                dto.setRemoved(Helper.localDateTimeToString(localFileModel.getRemoved(), ""));

            return dto;
        } catch (Exception e) {
            log.info("LocalFile entity to dto error. Exception: " + e);
            return null;
        }
    }

    public LocalFileModel toEntity(LocalFileCreation localFileCreation) {
        try {
            log.info("LocalFile creation to entity.");
            LocalFileModel localFileModel = new LocalFileModel();

            if (!Helper.isEmptyString(localFileCreation.getId()))
                localFileModel.setId(Helper.getLong(localFileCreation.getId()));
            localFileModel.setPath(localFileCreation.getPath());
            localFileModel.setFilename(localFileCreation.getFilename());
            if (!Helper.isEmptyString(localFileCreation.getFiletype()))
                localFileModel.setFiletype(FiletypeEnum.valueOf(localFileCreation.getFiletype()));
            localFileModel.setDescription(localFileCreation.getDescription());
            localFileModel.setFilesize(localFileCreation.getFilesize());

            if (!Helper.isEmptyString(localFileCreation.getCreator())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(localFileCreation.getCreator());
                if (user.getStatusCode() == 200)
                    localFileModel.setCreator(user.getObject());
            }
            if (!Helper.isEmptyString(localFileCreation.getCreated()))
                localFileModel.setCreated(Helper.stringToLocalDateTime(localFileCreation.getCreated(), ""));
            if (!Helper.isEmptyString(localFileCreation.getModifier())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(localFileCreation.getModifier());
                if (user.getStatusCode() == 200)
                    localFileModel.setModifier(user.getObject());
            }
            if (!Helper.isEmptyString(localFileCreation.getModified()))
                localFileModel.setModified(Helper.stringToLocalDateTime(localFileCreation.getModified(), ""));
            if (!Helper.isEmptyString(localFileCreation.getRemover())) {
                EntityMessenger<UserModel> user = userServiceImplementation.findByUsernameAndRemovedIsNull(localFileCreation.getRemover());
                if (user.getStatusCode() == 200)
                    localFileModel.setRemover(user.getObject());
            }
            if (!Helper.isEmptyString(localFileCreation.getRemoved()))
                localFileModel.setRemoved(Helper.stringToLocalDateTime(localFileCreation.getRemoved(), ""));

            return localFileModel;
        } catch (Exception e) {
            log.info("LocalFile creation to entity error. Exception: " + e);
            return null;
        }
    }
}