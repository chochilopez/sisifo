package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.RoleCreation;
import muni.eolida.sisifo.mapper.dto.RoleDataTransferObject;
import muni.eolida.sisifo.security.models.Role;
import muni.eolida.sisifo.security.models.enums.RoleEnum;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleMapper {
    public RoleDataTransferObject toDto(Role roleModel) {
        try {
            log.info("Role entity to dto.");
            RoleDataTransferObject dto = new RoleDataTransferObject();

            dto.setId(roleModel.getId().toString());
            dto.setRole(roleModel.getRole().toString());

            return dto;
        } catch (Exception e) {
            log.info("Role entity to dto error. Exception: " + e);
            return null;
        }
    }

    public Role toEntity(RoleCreation roleCreation) {
        try {
            log.info("Role creation to entity.");
            Role roleModel = new Role();

            if (!Helper.isEmptyString(roleCreation.getId()))
                roleModel.setId(Helper.getLong(roleCreation.getId()));
            roleModel.setRole(RoleEnum.valueOf(roleCreation.getRole()));

            return roleModel;
        } catch (Exception e) {
            log.info("Role creation to entity error. Exception: " + e);
            return null;
        }
    }
}