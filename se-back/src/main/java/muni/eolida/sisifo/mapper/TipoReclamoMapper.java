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
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final UsuarioMapper usuarioMapper;

    public TipoReclamoModel toEntity(TipoReclamoCreation tipoReclamoCreation) {
        try {
            log.info("TipoReclamo creation to entity.");
            TipoReclamoModel tipoReclamoModel = new TipoReclamoModel();

            tipoReclamoModel.setAreaResuelve(tipoReclamoCreation.getAreaResuelve());
            tipoReclamoModel.setCantidadDiasResolucion(Integer.valueOf(tipoReclamoCreation.getCantidadDiasResolucion()));
            tipoReclamoModel.setNombre(tipoReclamoCreation.getNombre());
            if (!Helper.isEmptyString(tipoReclamoCreation.getTipoDocumento()))
                tipoReclamoModel.setTipoDocumento(TipoDocumentoEnum.valueOf(tipoReclamoCreation.getTipoDocumento()));
            if (!Helper.isEmptyString(tipoReclamoCreation.getUsuario_id())) {
                EntityMessenger<UsuarioModel> usuario = usuarioServiceImpl.buscarPorId(Long.getLong(tipoReclamoCreation.getUsuario_id()));
                if (usuario.getStatusCode() == 200)
                    tipoReclamoModel.setUsuario(usuario.getObject());
            }

            return tipoReclamoModel;
        } catch (Exception e) {
            log.info("TipoReclamo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public TipoReclamoDTO toDto(TipoReclamoModel tipoReclamoModel) {
        try {
            log.info("TipoReclamo entity to dto.");
            TipoReclamoDTO dto = new TipoReclamoDTO();

            dto.setAreaResuelve(tipoReclamoModel.getAreaResuelve());
            dto.setCantidadDiasResolucion(Integer.toString(tipoReclamoModel.getCantidadDiasResolucion()));
            dto.setNombre(tipoReclamoModel.getNombre());
            dto.setTipoDocumento(tipoReclamoModel.getTipoDocumento().toString());
            if (tipoReclamoModel.getUsuario() != null)
                dto.setUsuario(usuarioMapper.toDto(tipoReclamoModel.getUsuario()));

            return dto;
        } catch (Exception e) {
            log.info("TipoReclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}