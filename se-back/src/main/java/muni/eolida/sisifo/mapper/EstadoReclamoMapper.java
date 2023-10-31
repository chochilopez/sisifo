package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.EstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.repository.SeguimientoDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstadoReclamoMapper {
    private final EstadoReclamoDAO estadoReclamoDAO;
    private final UsuarioDAO usuarioDAO;

    public EstadoReclamoModel toEntity(EstadoReclamoCreation estadoReclamoCreation) {
        try {
            EstadoReclamoModel estadoReclamoModel = new EstadoReclamoModel();

            if (Helper.getLong(estadoReclamoCreation.getId()) != null) {
                estadoReclamoModel = estadoReclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(estadoReclamoCreation.getId())).get();
            }
            estadoReclamoModel.setEstado(EstadoReclamoEnum.valueOf(estadoReclamoCreation.getEstado()));
            estadoReclamoModel.setDescripcion(estadoReclamoCreation.getDescripcion());

            if (Helper.getLong(estadoReclamoCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(estadoReclamoCreation.getCreador_id()));
                if (user.isPresent())
                    estadoReclamoModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(estadoReclamoCreation.getCreada()))
                estadoReclamoModel.setCreada(Helper.stringToLocalDateTime(estadoReclamoCreation.getCreada(), ""));
            if (Helper.getLong(estadoReclamoCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(estadoReclamoCreation.getModificador_id()));
                if (user.isPresent())
                    estadoReclamoModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(estadoReclamoCreation.getModificada()))
                estadoReclamoModel.setModificada(Helper.stringToLocalDateTime(estadoReclamoCreation.getModificada(), ""));
            if (Helper.getLong(estadoReclamoCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(estadoReclamoCreation.getEliminador_id()));
                if (user.isPresent())
                    estadoReclamoModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(estadoReclamoCreation.getEliminada()))
                estadoReclamoModel.setEliminada(Helper.stringToLocalDateTime(estadoReclamoCreation.getEliminada(), ""));


            return estadoReclamoModel;
        } catch (Exception e) {
            log.info("EstadoReclamo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public EstadoReclamoDTO toDto(EstadoReclamoModel modelo) {
        try {
            EstadoReclamoDTO dto = new EstadoReclamoDTO();

            dto.setId(modelo.getId().toString());
            dto.setEstado(modelo.getEstado().toString());
            dto.setDescripcion(modelo.getDescripcion());

            if (modelo.getCreada() != null)
                dto.setCreada(modelo.getCreada().toString());
            if (modelo.getModificada() != null)
                dto.setModificada(modelo.getModificada().toString());

            return dto;
        } catch (Exception e) {
            log.info("EstadoReclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}