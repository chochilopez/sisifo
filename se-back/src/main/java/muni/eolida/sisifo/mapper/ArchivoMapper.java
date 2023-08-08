package muni.eolida.sisifo.mapper;

import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.mapper.dto.ArchivoDataTransferObject;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchivoMapper {
    public ArchivoDataTransferObject toDto(ArchivoModel archivoModel) {
        try {
            log.info("Archivo entity to dto.");
            ArchivoDataTransferObject dto = new ArchivoDataTransferObject();

            dto.setId(archivoModel.getId().toString());
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

    public ArchivoModel toEntity(ArchivoCreation archivoCreation) {
        try {
            log.info("Archivo creation to entity.");
            ArchivoModel archivoModel = new ArchivoModel();

            if (!Helper.isEmptyString(archivoCreation.getId()))
                archivoModel.setId(Helper.getLong(archivoCreation.getId()));
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
}