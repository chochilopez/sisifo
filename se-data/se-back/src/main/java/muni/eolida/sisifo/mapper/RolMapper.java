package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.dto.RolDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RolMapper {

    private Long id;
    private RolEnum rol;

    public RolModel toEntity(RolCreation rolCreation) {
        try {
            log.info("Rol creation to entity.");
            RolModel rolModel = new RolModel();

            rolModel.setRol(RolEnum.valueOf(rolCreation.getRol()));

            return rolModel;
        } catch (Exception e) {
            log.info("Rol creation to entity error. Exception: " + e);
            return null;
        }
    }

    public RolDTO toDto(RolModel rolModel) {
        try {
            log.info("Rol entity to dto.");
            RolDTO dto = new RolDTO();

            dto.setId(rolModel.getId().toString());
            dto.setRol(rolModel.getRol().toString());

            return dto;
        } catch (Exception e) {
            log.info("Rol entity to dto error. Exception: " + e);
            return null;
        }
    }
}