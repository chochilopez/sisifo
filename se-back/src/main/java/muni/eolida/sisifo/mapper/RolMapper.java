package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.dto.RolDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.RolDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RolMapper {
    public final RolDAO rolDAO;

    public RolModel toEntity(RolCreation rolCreation) {
        try {
            log.info("Rol creation to entity.");
            RolModel rolModel = new RolModel();

            if (Helper.getLong(rolCreation.getId()) != null) {
                rolModel = rolDAO.findByIdAndEliminadaIsNull(Helper.getLong(rolCreation.getId())).get();
            }
            rolModel.setRol(RolEnum.valueOf(rolCreation.getRol()));

            return rolModel;
        } catch (Exception e) {
            log.info("Rol creation to entity error. Exception: " + e);
            return null;
        }
    }

    public RolDTO toDto(RolModel modelo) {
        try {
            log.info("Rol entity to dto.");
            RolDTO dto = new RolDTO();

            dto.setId(modelo.getId().toString());
            dto.setRol(modelo.getRol().toString());

            if (modelo.getCreada() != null)
                dto.setCreada(modelo.getCreada().toString());
            if (modelo.getModificada() != null)
                dto.setModificada(modelo.getModificada().toString());

            return dto;
        } catch (Exception e) {
            log.info("Rol entity to dto error. Exception: " + e);
            return null;
        }
    }
}