package muni.eolida.sisifo.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EstadoReclamoMapper {

    public EstadoReclamoModel toEntity(EstadoReclamoCreation estadoReclamoCreation) {
        try {
            EstadoReclamoModel estadoReclamoModel = new EstadoReclamoModel();

            estadoReclamoModel.setEstado(TipoEstadoReclamoEnum.valueOf(estadoReclamoCreation.getEstado()));

            return estadoReclamoModel;
        } catch (Exception e) {
            log.info("EstadoReclamo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public EstadoReclamoDTO toDto(EstadoReclamoModel estadoReclamoModel) {
        try {
            EstadoReclamoDTO dto = new EstadoReclamoDTO();

            dto.setId(estadoReclamoModel.getId().toString());
            dto.setEstado(estadoReclamoModel.getEstado().toString());

            return dto;
        } catch (Exception e) {
            log.info("EstadoReclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}