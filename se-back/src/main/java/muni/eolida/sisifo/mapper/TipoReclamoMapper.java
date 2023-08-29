package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TipoReclamoMapper {
    private final TipoReclamoDAO tipoReclamoDAO;

    public TipoReclamoModel toEntity(TipoReclamoCreation tipoReclamoCreation) {
        try {
            TipoReclamoModel tipoReclamoModel = new TipoReclamoModel();

            if (Helper.getLong(tipoReclamoCreation.getId()) != null) {
                tipoReclamoModel = tipoReclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(tipoReclamoCreation.getId())).get();
            }
            tipoReclamoModel.setTipo(tipoReclamoCreation.getTipo());

            return tipoReclamoModel;
        } catch (Exception e) {
            log.info("TipoReclamo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public TipoReclamoDTO toDto(TipoReclamoModel tipoReclamoModel) {
        try {
            TipoReclamoDTO dto = new TipoReclamoDTO();

            dto.setId(tipoReclamoModel.getId().toString());
            dto.setTipo(tipoReclamoModel.getTipo());

            return dto;
        } catch (Exception e) {
            log.info("TipoReclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}