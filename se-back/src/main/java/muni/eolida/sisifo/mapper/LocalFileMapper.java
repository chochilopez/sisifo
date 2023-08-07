package muni.eolida.sisifo.mapper;

import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.LocalFileCreation;
import muni.eolida.sisifo.mapper.dto.LocalFileDataTransferObject;
import muni.eolida.sisifo.model.LocalFileModel;
import muni.eolida.sisifo.model.enumerator.FiletypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalFileMapper {
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

            return localFileModel;
        } catch (Exception e) {
            log.info("LocalFile creation to entity error. Exception: " + e);
            return null;
        }
    }
}