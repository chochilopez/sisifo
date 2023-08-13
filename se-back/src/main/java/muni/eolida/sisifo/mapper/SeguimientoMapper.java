package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeguimientoMapper {

    public SeguimientoModel toEntity(SeguimientoCreation seguimientoCreation) {
        try {
            log.info("Seguimiento creation to entity.");
            SeguimientoModel seguimientoModel = new SeguimientoModel();

            seguimientoModel.setDescripcion(seguimientoCreation.getDescripcion());
            seguimientoModel.setEstado(seguimientoCreation.getEstado());
            if (!Helper.isEmptyString(seguimientoCreation.getEstadoReclamo()))
                seguimientoModel.setEstadoReclamo(TipoEstadoReclamoEnum.valueOf(seguimientoCreation.getEstadoReclamo()));

            return seguimientoModel;
        } catch (Exception e) {
            log.info("Seguimiento creation to entity error. Exception: " + e);
            return null;
        }
    }

    public SeguimientoDTO toDto(SeguimientoModel seguimientoModel) {
        try {
            log.info("Seguimiento entity to dto.");
            SeguimientoDTO dto = new SeguimientoDTO();

            dto.setDescripcion(seguimientoModel.getDescripcion());
            dto.setEstado(seguimientoModel.getEstado());
            dto.setEstadoReclamo(seguimientoModel.getEstadoReclamo().toString());

            return dto;
        } catch (Exception e) {
            log.info("Seguimiento entity to dto error. Exception: " + e);
            return null;
        }
    }
}