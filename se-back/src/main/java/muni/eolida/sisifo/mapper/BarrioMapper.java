package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.mapper.dto.BarrioDTO;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.repository.BarrioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BarrioMapper {
    private final BarrioDAO barrioDAO;

    public BarrioModel toEntity(BarrioCreation barrioCreation) {
        try {
            BarrioModel barrioModel = new BarrioModel();

            if (Helper.getLong(barrioCreation.getId()) != null) {
                barrioModel = barrioDAO.findByIdAndEliminadaIsNull(Helper.getLong(barrioCreation.getId())).get();
            }
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