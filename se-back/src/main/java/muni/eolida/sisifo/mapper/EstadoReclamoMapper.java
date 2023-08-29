package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstadoReclamoMapper {
    private final EstadoReclamoDAO estadoReclamoDAO;

    public EstadoReclamoModel toEntity(EstadoReclamoCreation estadoReclamoCreation) {
        try {
            EstadoReclamoModel estadoReclamoModel = new EstadoReclamoModel();

            if (Helper.getLong(estadoReclamoCreation.getId()) != null) {
                estadoReclamoModel = estadoReclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(estadoReclamoCreation.getId())).get();
            }
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