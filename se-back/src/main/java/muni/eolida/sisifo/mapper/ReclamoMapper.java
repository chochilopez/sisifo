package muni.eolida.sisifo.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.*;
import muni.eolida.sisifo.model.*;
import muni.eolida.sisifo.service.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReclamoMapper {
    @Autowired
    private CalleServiceImpl calleServiceImpl;
    @Autowired
    private ArchivoServiceImpl archivoServiceImpl;
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;
    @Autowired
    private TipoReclamoServiceImpl tipoReclamoServiceImpl;
    @Autowired
    private CalleMapper calleMapper;
    @Autowired
    private UsuarioMapper usuarioMapper;
    private TipoReclamoMapper tipoReclamoMapper;
    @Autowired
    private BarrioServiceImpl barrioService;
    @Autowired
    private BarrioMapper barrioMapper;
    @Autowired
    private ArchivoMapper archivoMapper;
    @Autowired
    private SeguimientoMapper seguimientoMapper;

    public ReclamoModel toEntity(ReclamoCreation reclamoCreation) {
        try {
            ReclamoModel reclamoModel = new ReclamoModel();
            reclamoModel.setAltura(reclamoCreation.getAltura());
            reclamoModel.setCoordinadaX(reclamoCreation.getCoordinadaX());
            reclamoModel.setCoordinadaY(reclamoCreation.getCoordinadaY());
            reclamoModel.setDescripcion(reclamoCreation.getDescripcion());
            EntityMessenger<BarrioModel> barrio = barrioService.buscarPorId(reclamoCreation.getCalle_id());
            if (barrio.getEstado() == 200)
                reclamoModel.setBarrio(barrio.getObjeto());
            EntityMessenger<CalleModel> calle = calleServiceImpl.buscarPorId(reclamoCreation.getCalle_id());
            if (calle.getEstado() == 200)
                reclamoModel.setCalle(calle.getObjeto());
            EntityMessenger<CalleModel> interseccion = calleServiceImpl.buscarPorId(reclamoCreation.getInterseccion_id());
            if (interseccion.getEstado() == 200)
                reclamoModel.setInterseccion(interseccion.getObjeto());
            EntityMessenger<CalleModel> entreCalle1 = calleServiceImpl.buscarPorId(reclamoCreation.getEntreCalle1_id());
            if (entreCalle1.getEstado() == 200)
                reclamoModel.setEntreCalle1(entreCalle1.getObjeto());
            EntityMessenger<CalleModel> entreCalle2 = calleServiceImpl.buscarPorId(reclamoCreation.getEntreCalle2_id());
            if (entreCalle2.getEstado() == 200)
                reclamoModel.setEntreCalle2(entreCalle2.getObjeto());
            EntityMessenger<TipoReclamoModel> tipoReclamo = tipoReclamoServiceImpl.buscarPorId(reclamoCreation.getTipoReclamo_id());
            if (tipoReclamo.getEstado() == 200)
                reclamoModel.setTipoReclamo(tipoReclamo.getObjeto());
            EntityMessenger<UsuarioModel> usuarioModelEntidadMensaje = usuarioServiceImpl.obtenerUsuario();
            reclamoModel.setPersona(usuarioModelEntidadMensaje.getObjeto());
            try {
                if (reclamoCreation.getImagen() != null) {
                    EntityMessenger<ArchivoModel> imagen = archivoServiceImpl.guardarArchivo(reclamoCreation.getImagen());
                    if (imagen.getEstado() == 200)
                        reclamoModel.setImagen(imagen.getObjeto());
                }
            } catch (Exception e) {
                log.error("An error ocurred while trying to save the image: " + e);
            }

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
            dto.setCoordinadaX(reclamoModel.getCoordinadaX());
            dto.setCoordinadaY(reclamoModel.getCoordinadaY());
            dto.setDescripcion(reclamoModel.getDescripcion());
            if (reclamoModel.getBarrio() != null)
                dto.setBarrio(barrioMapper.toDto(reclamoModel.getBarrio()));
            if (reclamoModel.getCalle() != null)
                dto.setCalle(calleMapper.toDto(reclamoModel.getCalle()));
            if (reclamoModel.getInterseccion() != null)
                dto.setInterseccion(calleMapper.toDto(reclamoModel.getInterseccion()));
            if (reclamoModel.getEntreCalle1() != null)
                dto.setEntreCalle1(calleMapper.toDto(reclamoModel.getEntreCalle1()));
            if (reclamoModel.getEntreCalle2() != null)
                dto.setEntreCalle2(calleMapper.toDto(reclamoModel.getEntreCalle2()));
            if (reclamoModel.getPersona() != null)
                dto.setPersona(usuarioMapper.toDto(reclamoModel.getPersona()));
            if (reclamoModel.getTipoReclamo() != null)
                dto.setTipoReclamo(tipoReclamoMapper.toDto(reclamoModel.getTipoReclamo()));
            if (reclamoModel.getImagen() != null)
                dto.setImagen(archivoMapper.toDto(reclamoModel.getImagen()));
            if (reclamoModel.getSeguimiento() != null)
                dto.setSeguimiento(seguimientoMapper.toDto(reclamoModel.getSeguimiento()));

            return dto;
        } catch (Exception e) {
            log.info("Reclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}