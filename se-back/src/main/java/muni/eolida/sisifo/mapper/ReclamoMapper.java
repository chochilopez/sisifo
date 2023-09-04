package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.enums.EstadoReclamoEnum;
import muni.eolida.sisifo.repository.*;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.mapper.dto.*;
import muni.eolida.sisifo.model.*;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    private final ArchivoDAO archivoDAO;
    private final SeguimientoDAO seguimientoDAO;
    private final UsuarioServiceImpl usuarioService;

    public ReclamoModel toEntity(ReclamoCreation reclamoCreation) {
        try {
            ReclamoModel reclamoModel = new ReclamoModel();

            if (Helper.getLong(reclamoCreation.getId()) != null) {
                reclamoModel = reclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getId())).get();
            }
            reclamoModel.setCoordinadaX(reclamoCreation.getCoordinadaX());
            reclamoModel.setCoordinadaY(reclamoCreation.getCoordinadaY());
            reclamoModel.setDescripcion(reclamoCreation.getDescripcion());
            reclamoModel.setAltura(reclamoCreation.getAltura());
            if (Helper.getLong(reclamoCreation.getImagen_id()) != null) {
                Optional<ArchivoModel> imagen = archivoDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getImagen_id()));
                if (imagen.isPresent()) {
                    reclamoModel.setImagen(imagen.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getCalle_id()) != null) {
                Optional<CalleModel> calle = calleDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getCalle_id()));
                if (calle.isPresent()) {
                    reclamoModel.setCalle(calle.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getBarrio_id()) != null) {
                Optional<BarrioModel> barrio = barrioDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getBarrio_id()));
                if (barrio.isPresent()) {
                    reclamoModel.setBarrio(barrio.get());
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
            if (Helper.getLong(reclamoCreation.getPersona_id()) != null) {
                Optional<UsuarioModel> usuario = usuarioDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getPersona_id()));
                if (usuario.isPresent()) {
                    reclamoModel.setPersona(usuario.get());
                }
            }
            if (Helper.getLong(reclamoCreation.getSeguimiento_id()) != null) {
                Optional<SeguimientoModel> seguimiento = seguimientoDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getSeguimiento_id()));
                if (seguimiento.isPresent()) {
                    reclamoModel.setSeguimiento(seguimiento.get());
                }
            } else {
                SeguimientoModel seguimiento = new SeguimientoModel();
                EstadoReclamoModel estadoReclamo = new EstadoReclamoModel(EstadoReclamoEnum.INICIADO, "Reclamo iniciado");
                Set<EstadoReclamoModel> estadosReclamo= new HashSet<>();
                estadosReclamo.add(estadoReclamo);
                seguimiento.setEstados(estadosReclamo);
                seguimiento.setCreada(Helper.getNow(""));
                seguimiento.setCreador(usuarioService.obtenerUsuario());
                seguimientoDAO.save(seguimiento);
                reclamoModel.setSeguimiento(seguimiento);
            }
            reclamoModel.setPersona(usuarioDAO.findByIdAndEliminadaIsNull(Helper.getLong(reclamoCreation.getPersona_id())).get());

            if (Helper.getLong(reclamoCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(reclamoCreation.getCreador_id()));
                if (user.isPresent())
                    reclamoModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(reclamoCreation.getCreada()))
                reclamoModel.setCreada(Helper.stringToLocalDateTime(reclamoCreation.getCreada(), ""));
            if (Helper.getLong(reclamoCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(reclamoCreation.getModificador_id()));
                if (user.isPresent())
                    reclamoModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(reclamoCreation.getModificada()))
                reclamoModel.setModificada(Helper.stringToLocalDateTime(reclamoCreation.getModificada(), ""));
            if (Helper.getLong(reclamoCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(reclamoCreation.getEliminador_id()));
                if (user.isPresent())
                    reclamoModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(reclamoCreation.getEliminada()))
                reclamoModel.setEliminada(Helper.stringToLocalDateTime(reclamoCreation.getEliminada(), ""));

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
            dto.setCoordinadaX(reclamoModel.getCoordinadaX());
            dto.setCoordinadaY(reclamoModel.getCoordinadaY());
            dto.setDescripcion(reclamoModel.getDescripcion());
            dto.setAltura(reclamoModel.getAltura());

            if (reclamoModel.getImagen() != null)
                dto.setImagen(archivoMapper.toDto(reclamoModel.getImagen()));
            if (reclamoModel.getSeguimiento() != null)
                dto.setSeguimiento(seguimientoMapper.toDto(reclamoModel.getSeguimiento()));
            if (reclamoModel.getCalle() != null)
                dto.setCalle(calleMapper.toDto(reclamoModel.getCalle()));
            if (reclamoModel.getBarrio() != null)
                dto.setBarrio(barrioMapper.toDto(reclamoModel.getBarrio()));
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

            return dto;
        } catch (Exception e) {
            log.info("Reclamo entity to dto error. Exception: " + e);
            return null;
        }
    }
}