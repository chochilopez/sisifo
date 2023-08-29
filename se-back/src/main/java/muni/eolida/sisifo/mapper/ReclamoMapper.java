package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.repository.*;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.*;
import muni.eolida.sisifo.model.*;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReclamoMapper {
    private final CalleDAO calleDAO;
    private final UsuarioDAO usuarioDAO;
    private final TipoReclamoDAO tipoReclamoDAO;
    private final BarrioDAO barrioDAO;
    private final CalleMapper calleMapper;
    private final UsuarioMapper usuarioMapper;
    private final TipoReclamoMapper tipoReclamoMapper;
    private final BarrioMapper barrioMapper;
    private final ArchivoMapper archivoMapper;
    private final SeguimientoMapper seguimientoMapper;
    private final ReclamoDAO reclamoDAO;

    public ReclamoModel toEntity(ReclamoCreation reclamoCreation) {
        try {
            ReclamoModel reclamoModel = new ReclamoModel();

            if (Helper.getLong(reclamoCreation.getId()) != null) {
                reclamoModel = reclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getId())).get();
            }
            reclamoModel.setAltura(reclamoCreation.getAltura());
            reclamoModel.setCoordinadaX(reclamoCreation.getCoordinadaX());
            reclamoModel.setCoordinadaY(reclamoCreation.getCoordinadaY());
            reclamoModel.setDescripcion(reclamoCreation.getDescripcion());
            if (Helper.getLong(reclamoCreation.getBarrio_id()) != null) {
                Optional<BarrioModel> barrio = barrioDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getBarrio_id()));
                if (barrio.isPresent()) {
                    reclamoModel.setBarrio(barrio.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getCalle_id()) != null) {
                Optional<CalleModel> calle = calleDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getCalle_id()));
                if (calle.isPresent()) {
                    reclamoModel.setCalle(calle.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getInterseccion_id()) != null) {
                Optional<CalleModel> interseccion = calleDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getInterseccion_id()));
                if (interseccion.isPresent()) {
                    reclamoModel.setInterseccion(interseccion.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getEntreCalle1_id()) != null) {
                Optional<CalleModel> entreCalle1 = calleDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getEntreCalle1_id()));
                if (entreCalle1.isPresent()) {
                    reclamoModel.setEntreCalle1(entreCalle1.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getEntreCalle2_id()) != null) {
                Optional<CalleModel> entreCalle2 = calleDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getEntreCalle2_id()));
                if (entreCalle2.isPresent()) {
                    reclamoModel.setEntreCalle2(entreCalle2.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getTipoReclamo_id()) != null) {
                Optional<TipoReclamoModel> tipoReclamo = tipoReclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getTipoReclamo_id()));
                if (tipoReclamo.isPresent()) {
                    reclamoModel.setTipoReclamo(tipoReclamo.get());
                }
            }
            reclamoModel.setPersona(usuarioDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getPersona_id())).get());

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