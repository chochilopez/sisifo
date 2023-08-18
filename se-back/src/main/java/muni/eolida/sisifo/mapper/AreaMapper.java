package muni.eolida.sisifo.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.mapper.dto.AreaDTO;
import muni.eolida.sisifo.model.AreaModel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AreaMapper {

    public AreaModel toEntity(AreaCreation areaCreation) {
        try {
            AreaModel areaModel = new AreaModel();

            areaModel.setArea(areaCreation.getArea());

            return areaModel;
        } catch (Exception e) {
            log.info("Area creation to entity error. Exception: " + e);
            return null;
        }
    }

    public AreaDTO toDto(AreaModel areaModel) {
        try {
            AreaDTO dto = new AreaDTO();

            dto.setId(areaModel.getId().toString());
            dto.setArea(areaModel.getArea());

            return dto;
        } catch (Exception e) {
            log.info("Area entity to dto error. Exception: " + e);
            return null;
        }
    }
}