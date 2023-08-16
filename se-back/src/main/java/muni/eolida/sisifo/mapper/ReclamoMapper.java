package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.*;
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
    private final UsuarioMapper usuarioMapper;
    private final TipoReclamoMapper tipoReclamoMapper;

    public ReclamoModel toEntity(ReclamoCreation reclamoCreation) {
        try {
            ReclamoModel reclamoModel = new ReclamoModel();
            reclamoModel.setAltura(reclamoCreation.getAltura());
            EntityMessenger<CalleModel> calle = calleServiceImpl.buscarPorId(reclamoCreation.getCalle_id());
            if (calle.getStatusCode() == 200)
                reclamoModel.setCalle(calle.getObject());
            EntityMessenger<CalleModel> interseccion = calleServiceImpl.buscarPorId(reclamoCreation.getInterseccion_id());
            if (interseccion.getStatusCode() == 200)
                reclamoModel.setInterseccion(interseccion.getObject());
            reclamoModel.setCoordinadaX(reclamoCreation.getCoordinadaX());
            reclamoModel.setCoordinadaY(reclamoCreation.getCoordinadaY());
            reclamoModel.setDescripcion(reclamoCreation.getDescripcion());
            EntityMessenger<CalleModel> entreCalle1 = calleServiceImpl.buscarPorId(reclamoCreation.getEntreCalle1_id());
            if (entreCalle1.getStatusCode() == 200)
                reclamoModel.setEntreCalle1(entreCalle1.getObject());
            EntityMessenger<CalleModel> entreCalle2 = calleServiceImpl.buscarPorId(reclamoCreation.getEntreCalle2_id());
            if (entreCalle2.getStatusCode() == 200)
                reclamoModel.setEntreCalle2(entreCalle2.getObject());
            String usuario = usuarioServiceImpl.obtenerUsuario().getObject().getId().toString() + "-" + usuarioServiceImpl.obtenerUsuario().getObject().getNombre();
            String uuid = java.util.UUID.randomUUID().toString();
            try {
                if (reclamoCreation.getImagen() != null) {
                    EntityMessenger<ArchivoModel> imagen = archivoServiceImpl.guardarArchivo(reclamoCreation.getImagen(), usuario, uuid);
                    if (imagen.getStatusCode() == 200)
                        reclamoModel.setImagen(imagen.getObject());
                }
            } catch (Exception e) {
                log.error("An error ocurred while trying to save the image: " + e);
            }

            EntityMessenger<UsuarioModel> usuarioModelEntityMessenger = usuarioServiceImpl.obtenerUsuario();
            reclamoModel.setPersona(usuarioModelEntityMessenger.getObject());
            EntityMessenger<TipoReclamoModel> tipoReclamo = tipoReclamoServiceImpl.buscarPorId(reclamoCreation.getTipoReclamo_id());
            if (tipoReclamo.getStatusCode() == 200)
                reclamoModel.setTipoReclamo(tipoReclamo.getObject());

            return reclamoModel;
        } catch (Exception e) {
            log.info("Reclamo creation to entity error. Exception: " + e);
            return null;
        }
    }

    public ReclamoDTO toDto(ReclamoModel reclamoModel) {
        try {
            ReclamoDTO dto = new ReclamoDTO();

            dto.setId(reclamoModel.getId().toString());
            dto.setAltura(reclamoModel.getAltura());
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