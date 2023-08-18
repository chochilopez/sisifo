package muni.eolida.sisifo.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDTO;
import muni.eolida.sisifo.model.ArchivoModel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArchivoMapper {

    public ArchivoModel toEntity(ArchivoCreation archivoCreation) {
        try {
            ArchivoModel archivoModel = new ArchivoModel();

            archivoModel.setPath(archivoCreation.getPath());
            archivoModel.setNombre(archivoCreation.getNombre());

            return archivoModel;
        } catch (Exception e) {
            log.info("Archivo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public ArchivoDTO toDto(ArchivoModel archivoModel) {
        try {
            ArchivoDTO dto = new ArchivoDTO();

            dto.setId(archivoModel.getId().toString());
            dto.setPath(archivoModel.getPath());
            dto.setNombre(archivoModel.getNombre());

            return dto;
        } catch (Exception e) {
            log.info("Archivo entity to dto error. Exception: " + e);
            return null;
        }
    }
}