package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.dto.RolDataTransferObject;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RolMapper {
    public RolDataTransferObject toDto(RolModel rolModel) {
        try {
            log.info("Role entity to dto.");
            RolDataTransferObject dto = new RolDataTransferObject();

            dto.setId(rolModel.getId().toString());
            dto.setRol(rolModel.getRol().toString());

            return dto;
        } catch (Exception e) {
            log.info("Role entity to dto error. Exception: " + e);
            return null;
        }
    }

    public RolModel toEntity(RolCreation rolCreation) {
        try {
            log.info("Role creation to entity.");
            RolModel rolModel = new RolModel();

            if (!Helper.isEmptyString(rolCreation.getId()))
                rolModel.setId(Helper.getLong(rolCreation.getId()));
            rolModel.setRol(RolEnum.valueOf(rolCreation.getRol()));

            return rolModel;
        } catch (Exception e) {
            log.info("Role creation to entity error. Exception: " + e);
            return null;
        }
    }
}