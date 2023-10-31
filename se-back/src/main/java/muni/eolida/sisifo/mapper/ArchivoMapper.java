package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDTO;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.ArchivoDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchivoMapper {
    public final ArchivoDAO archivoDAO;
    public final UsuarioDAO usuarioDAO;

    public ArchivoModel toEntity(ArchivoCreation archivoCreation) {
        try {
            ArchivoModel archivoModel = new ArchivoModel();

            if (Helper.getLong(archivoCreation.getId()) != null) {
                archivoModel = archivoDAO.findByIdAndEliminadaIsNull(Helper.getLong(archivoCreation.getId())).get();
            }

            archivoModel.setPath(archivoCreation.getPath());
            archivoModel.setNombre(archivoCreation.getNombre());

            if (Helper.getLong(archivoCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(archivoCreation.getCreador_id()));
                if (user.isPresent())
                    archivoModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(archivoCreation.getCreada()))
                archivoModel.setCreada(Helper.stringToLocalDateTime(archivoCreation.getCreada(), ""));
            if (Helper.getLong(archivoCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(archivoCreation.getModificador_id()));
                if (user.isPresent())
                    archivoModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(archivoCreation.getModificada()))
                archivoModel.setModificada(Helper.stringToLocalDateTime(archivoCreation.getModificada(), ""));
            if (Helper.getLong(archivoCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(archivoCreation.getEliminador_id()));
                if (user.isPresent())
                    archivoModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(archivoCreation.getEliminada()))
                archivoModel.setEliminada(Helper.stringToLocalDateTime(archivoCreation.getEliminada(), ""));

            return archivoModel;
        } catch (Exception e) {
            log.info("Archivo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public ArchivoDTO toDto(ArchivoModel modelo) {
        try {
            ArchivoDTO dto = new ArchivoDTO();

            dto.setId(modelo.getId().toString());
            dto.setPath(modelo.getPath());
            dto.setNombre(modelo.getNombre());

            if (modelo.getCreada() != null)
                dto.setCreada(modelo.getCreada().toString());
            if (modelo.getModificada() != null)
                dto.setModificada(modelo.getModificada().toString());

            return dto;
        } catch (Exception e) {
            log.info("Archivo entity to dto error. Exception: " + e);
            return null;
        }
    }
}