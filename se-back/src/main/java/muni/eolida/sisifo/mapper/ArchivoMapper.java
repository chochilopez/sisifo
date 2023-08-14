package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDTO;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchivoMapper {

    public ArchivoModel toEntity(ArchivoCreation archivoCreation) {
        try {
            ArchivoModel archivoModel = new ArchivoModel();

            archivoModel.setPath(archivoCreation.getPath());
            archivoModel.setNombre(archivoCreation.getNombre());
            if (!Helper.isEmptyString(archivoCreation.getTipo()))
                archivoModel.setTipo(TipoArchivoEnum.valueOf(archivoCreation.getTipo()));
            archivoModel.setDescripcion(archivoCreation.getDescripcion());
            archivoModel.setTamanio(archivoCreation.getTamanio());

            return archivoModel;
        } catch (Exception e) {
            log.info("Archivo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public ArchivoDTO toDto(ArchivoModel archivoModel) {
        try {
            ArchivoDTO dto = new ArchivoDTO();

            dto.setPath(archivoModel.getPath());
            dto.setNombre(archivoModel.getNombre());
            dto.setTipo(archivoModel.getTipo().toString());
            dto.setDescripcion(archivoModel.getDescripcion());
            dto.setTamanio(archivoModel.getTamanio());

            return dto;
        } catch (Exception e) {
            log.info("Archivo entity to dto error. Exception: " + e);
            return null;
        }
    }
}