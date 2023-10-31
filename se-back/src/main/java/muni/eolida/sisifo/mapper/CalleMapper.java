package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.mapper.dto.CalleDTO;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.CalleDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalleMapper {
    private final CalleDAO calleDAO;
    public final UsuarioDAO usuarioDAO;

    public CalleModel toEntity(CalleCreation calleCreation) {
        try {
            CalleModel calleModel = new CalleModel();

            if (Helper.getLong(calleCreation.getId()) != null) {
                calleModel = calleDAO.findByIdAndEliminadaIsNull(Helper.getLong(calleCreation.getId())).get();
            }
            calleModel.setCalle(calleCreation.getCalle());

            if (Helper.getLong(calleCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(calleCreation.getCreador_id()));
                if (user.isPresent())
                    calleModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(calleCreation.getCreada()))
                calleModel.setCreada(Helper.stringToLocalDateTime(calleCreation.getCreada(), ""));
            if (Helper.getLong(calleCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(calleCreation.getModificador_id()));
                if (user.isPresent())
                    calleModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(calleCreation.getModificada()))
                calleModel.setModificada(Helper.stringToLocalDateTime(calleCreation.getModificada(), ""));
            if (Helper.getLong(calleCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(calleCreation.getEliminador_id()));
                if (user.isPresent())
                    calleModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(calleCreation.getEliminada()))
                calleModel.setEliminada(Helper.stringToLocalDateTime(calleCreation.getEliminada(), ""));

            return calleModel;
        } catch (Exception e) {
            log.info("Calle creation to entity error. Exception: " + e);
            return null;
        }
    }

    public CalleDTO toDto(CalleModel modelo) {
        try {
            CalleDTO dto = new CalleDTO();

            dto.setId(modelo.getId().toString());
            dto.setCalle(modelo.getCalle());

            if (modelo.getCreada() != null)
                dto.setCreada(modelo.getCreada().toString());
            if (modelo.getModificada() != null)
                dto.setModificada(modelo.getModificada().toString());

            return dto;
        } catch (Exception e) {
            log.info("Calle entity to dto error. Exception: " + e);
            return null;
        }
    }
}