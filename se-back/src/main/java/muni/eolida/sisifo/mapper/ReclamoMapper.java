package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.ReclamoDTO;
import muni.eolida.sisifo.model.*;
import muni.eolida.sisifo.service.implementation.ArchivoServiceImpl;
import muni.eolida.sisifo.service.implementation.CalleServiceImpl;
import muni.eolida.sisifo.service.implementation.TipoReclamoServiceImpl;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReclamoMapper {
    private final CalleServiceImpl calleServiceImpl;
    private final ArchivoServiceImpl archivoServiceImpl;
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final TipoReclamoServiceImpl tipoReclamoServiceImpl;
    private final CalleMapper calleMapper;
    private final ArchivoMapper archivoMapper;
    private final UsuarioMapper usuarioMapper;
    private final TipoReclamoMapper tipoReclamoMapper;

    public ReclamoModel toEntity(ReclamoCreation reclamoCreation) {
        try {
            log.info("Reclamo creation to entity.");
            ReclamoModel reclamoModel = new ReclamoModel();

            reclamoModel.setAltura(reclamoCreation.getAltura());
            reclamoModel.setBarrio(reclamoCreation.getBarrio());
            if (!Helper.isEmptyString(reclamoCreation.getCalle_id())) {
                EntityMessenger<CalleModel> calle = calleServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getCalle_id()));
                if (calle.getStatusCode() == 200)
                    reclamoModel.setCalle(calle.getObject());
            }
            if (!Helper.isEmptyString(reclamoCreation.getInterseccion_id())) {
                EntityMessenger<CalleModel> interseccion = calleServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getInterseccion_id()));
                if (interseccion.getStatusCode() == 200)
                    reclamoModel.setInterseccion(interseccion.getObject());
            }
            reclamoModel.setCoordinadaX(reclamoCreation.getCoordinadaX());
            reclamoModel.setCoordinadaY(reclamoCreation.getCoordinadaY());
            reclamoModel.setDescripcion(reclamoCreation.getDescripcion());
            if (!Helper.isEmptyString(reclamoCreation.getEntreCalle1_id())) {
                EntityMessenger<CalleModel> entreCalle1 = calleServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getEntreCalle1_id()));
                if (entreCalle1.getStatusCode() == 200)
                    reclamoModel.setEntreCalle1(entreCalle1.getObject());
            }
            if (!Helper.isEmptyString(reclamoCreation.getEntreCalle2_id())) {
                EntityMessenger<CalleModel> entreCalle2 = calleServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getEntreCalle2_id()));
                if (entreCalle2.getStatusCode() == 200)
                    reclamoModel.setEntreCalle2(entreCalle2.getObject());
            }
            if (!Helper.isEmptyString(reclamoCreation.getImagen_id())) {
                EntityMessenger<ArchivoModel> imagen = archivoServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getImagen_id()));
                if (imagen.getStatusCode() == 200)
                    reclamoModel.setImagen(imagen.getObject());
            }
            reclamoModel.setNumero(reclamoCreation.getNumero());
            if (!Helper.isEmptyString(reclamoCreation.getPersona_id())) {
                EntityMessenger<UsuarioModel> persona = usuarioServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getPersona_id()));
                if (persona.getStatusCode() == 200)
                    reclamoModel.setPersona(persona.getObject());
            }
            if (!Helper.isEmptyString(reclamoCreation.getTipoReclamo_id())) {
                EntityMessenger<TipoReclamoModel> tipoReclamo = tipoReclamoServiceImpl.buscarPorId(Long.getLong(reclamoCreation.getTipoReclamo_id()));
                if (tipoReclamo.getStatusCode() == 200)
                    reclamoModel.setTipoReclamo(tipoReclamo.getObject());
            }

            return reclamoModel;
        } catch (Exception e) {
            log.info("Reclamo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public ReclamoDTO toDto(ReclamoModel reclamoModel) {
        try {
            log.info("Reclamo entity to dto.");
            ReclamoDTO dto = new ReclamoDTO();

            dto.setAltura(reclamoModel.getAltura());
            dto.setBarrio(reclamoModel.getBarrio());
            if (reclamoModel.getCalle() != null)
                dto.setCalle(calleMapper.toDto(reclamoModel.getCalle()));
            if (reclamoModel.getInterseccion() != null)
                dto.setInterseccion(calleMapper.toDto(reclamoModel.getInterseccion()));
            dto.setCoordinadaX(reclamoModel.getCoordinadaX());
            dto.setCoordinadaY(reclamoModel.getCoordinadaY());
            dto.setDescripcion(reclamoModel.getDescripcion());
            if (reclamoModel.getEntreCalle1() != null)
                dto.setEntreCalle1(calleMapper.toDto(reclamoModel.getEntreCalle1()));
            if (reclamoModel.getEntreCalle2() != null)
                dto.setEntreCalle2(calleMapper.toDto(reclamoModel.getEntreCalle2()));
            dto.setDescripcion(reclamoModel.getDescripcion());
            if (reclamoModel.getPersona() != null)
                dto.setPersona(usuarioMapper.toDto(reclamoModel.getPersona()));
            if (reclamoModel.getTipoReclamo() != null)
                dto.setTipoReclamo(tipoReclamoMapper.toDto(reclamoModel.getTipoReclamo()));

            return dto;
        } catch (Exception e) {
            log.info("Reclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}