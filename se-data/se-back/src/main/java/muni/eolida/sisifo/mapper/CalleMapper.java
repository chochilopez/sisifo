package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.mapper.dto.CalleDTO;
import muni.eolida.sisifo.model.CalleModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalleMapper {

    public CalleModel toEntity(CalleCreation calleCreation) {
        try {
            CalleModel calleModel = new CalleModel();

            calleModel.setCalle(calleCreation.getCalle());

            return calleModel;
        } catch (Exception e) {
            log.info("Calle creation to entity error. Exception: " + e);
            return null;
        }
    }

    public CalleDTO toDto(CalleModel calleModel) {
        try {
            CalleDTO dto = new CalleDTO();

            dto.setId(calleModel.getId().toString());
            dto.setCalle(calleModel.getCalle());

            return dto;
        } catch (Exception e) {
            log.info("Calle entity to dto error. Exception: " + e);
            return null;
        }
    }
}