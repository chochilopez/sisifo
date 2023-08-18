package muni.eolida.sisifo.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.mapper.dto.BarrioDTO;
import muni.eolida.sisifo.model.BarrioModel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BarrioMapper {

    public BarrioModel toEntity(BarrioCreation barrioCreation) {
        try {
            BarrioModel barrioModel = new BarrioModel();

            barrioModel.setBarrio(barrioCreation.getBarrio());

            return barrioModel;
        } catch (Exception e) {
            log.info("Barrio creation to entity error. Exception: " + e);
            return null;
        }
    }

    public BarrioDTO toDto(BarrioModel barrioModel) {
        try {
            BarrioDTO dto = new BarrioDTO();

            dto.setId(barrioModel.getId().toString());
            dto.setBarrio(barrioModel.getBarrio());

            return dto;
        } catch (Exception e) {
            log.info("Barrio entity to dto error. Exception: " + e);
            return null;
        }
    }
}