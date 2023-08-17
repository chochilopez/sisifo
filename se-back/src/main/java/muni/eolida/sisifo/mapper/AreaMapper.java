package muni.eolida.sisifo.mapper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.mapper.dto.AreaDTO;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.model.TipoReclamoModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
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