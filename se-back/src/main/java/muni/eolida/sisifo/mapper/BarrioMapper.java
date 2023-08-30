package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.mapper.dto.BarrioDTO;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.BarrioDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BarrioMapper {
    private final BarrioDAO barrioDAO;
    public final UsuarioDAO usuarioDAO;

    public BarrioModel toEntity(BarrioCreation barrioCreation) {
        try {
            BarrioModel barrioModel = new BarrioModel();

            if (Helper.getLong(barrioCreation.getId()) != null) {
                barrioModel = barrioDAO.findByIdAndEliminadaIsNull(Helper.getLong(barrioCreation.getId())).get();
            }
            barrioModel.setBarrio(barrioCreation.getBarrio());

            if (Helper.getLong(barrioCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(barrioCreation.getCreador_id()));
                if (user.isPresent())
                    barrioModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(barrioCreation.getCreada()))
                barrioModel.setCreada(Helper.stringToLocalDateTime(barrioCreation.getCreada(), ""));
            if (Helper.getLong(barrioCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(barrioCreation.getModificador_id()));
                if (user.isPresent())
                    barrioModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(barrioCreation.getModificada()))
                barrioModel.setModificada(Helper.stringToLocalDateTime(barrioCreation.getModificada(), ""));
            if (Helper.getLong(barrioCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(barrioCreation.getEliminador_id()));
                if (user.isPresent())
                    barrioModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(barrioCreation.getEliminada()))
                barrioModel.setEliminada(Helper.stringToLocalDateTime(barrioCreation.getEliminada(), ""));

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