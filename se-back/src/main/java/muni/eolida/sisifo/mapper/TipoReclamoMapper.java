package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.TipoDocumentoEnum;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TipoReclamoMapper {
    public TipoReclamoModel toEntity(TipoReclamoCreation tipoReclamoCreation) {
        try {
            TipoReclamoModel tipoReclamoModel = new TipoReclamoModel();

            tipoReclamoModel.setAreaResuelve(Integer.valueOf(tipoReclamoCreation.getAreaResuelve()));
            tipoReclamoModel.setCantidadDiasResolucion(Integer.valueOf(tipoReclamoCreation.getCantidadDiasResolucion()));
            tipoReclamoModel.setNombre(tipoReclamoCreation.getNombre());

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
            dto.setNombre(tipoReclamoModel.getNombre());

            return dto;
        } catch (Exception e) {
            log.info("TipoReclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}