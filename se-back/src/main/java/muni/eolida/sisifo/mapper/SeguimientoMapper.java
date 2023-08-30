package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.repository.SeguimientoDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeguimientoMapper {
    private final EstadoReclamoMapper estadoReclamoMapper;
    private final EstadoReclamoDAO estadoReclamoDAO;
    private final SeguimientoDAO seguimientoDAO;
    private final UsuarioDAO usuarioDAO;

    public SeguimientoModel toEntity(SeguimientoCreation seguimientoCreation) {
        try {
            SeguimientoModel seguimientoModel = new SeguimientoModel();

            if (Helper.getLong(seguimientoCreation.getId()) != null) {
                seguimientoModel = seguimientoDAO.findByIdAndEliminadaIsNull(Helper.getLong(seguimientoCreation.getId())).get();
            }
            seguimientoModel.setDescripcion(seguimientoCreation.getDescripcion());

            List<EstadoReclamoModel> estadoReclamos = new ArrayList<>();
            if (seguimientoCreation.getEstados_id() != null) {
                for (String estadoReclamoId:seguimientoCreation.getEstados_id()) {
                    if (Helper.getLong(estadoReclamoId) != null) {
                        Optional<EstadoReclamoModel> estadoReclamo = estadoReclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(estadoReclamoId));
						estadoReclamo.ifPresent(estadoReclamos::add);
                    }
                }
                seguimientoModel.setEstados(estadoReclamos);
            } else {
                estadoReclamos.add(estadoReclamoDAO.findByEstadoAndEliminadaIsNull(TipoEstadoReclamoEnum.INICIADO).get());
            }
            seguimientoModel.setEstados(estadoReclamos);

            if (Helper.getLong(seguimientoCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(seguimientoCreation.getCreador_id()));
                if (user.isPresent())
                    seguimientoModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(seguimientoCreation.getCreada()))
                seguimientoModel.setCreada(Helper.stringToLocalDateTime(seguimientoCreation.getCreada(), ""));
            if (Helper.getLong(seguimientoCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(seguimientoCreation.getModificador_id()));
                if (user.isPresent())
                    seguimientoModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(seguimientoCreation.getModificada()))
                seguimientoModel.setModificada(Helper.stringToLocalDateTime(seguimientoCreation.getModificada(), ""));
            if (Helper.getLong(seguimientoCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(seguimientoCreation.getEliminador_id()));
                if (user.isPresent())
                    seguimientoModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(seguimientoCreation.getEliminada()))
                seguimientoModel.setEliminada(Helper.stringToLocalDateTime(seguimientoCreation.getEliminada(), ""));

            return seguimientoModel;
        } catch (Exception e) {
            log.info("Seguimiento creation to entity error. Exception: " + e);
            return null;
        }
    }

    public SeguimientoDTO toDto(SeguimientoModel seguimientoModel) {
        try {
            SeguimientoDTO dto = new SeguimientoDTO();

            dto.setId(seguimientoModel.getId().toString());
            dto.setDescripcion(seguimientoModel.getDescripcion());
            List<EstadoReclamoDTO> estados = new ArrayList<>();
            for (EstadoReclamoModel estado:seguimientoModel.getEstados()) {
                estados.add(estadoReclamoMapper.toDto(estado));
            }
            dto.setEstados(estados);

            return dto;
        } catch (Exception e) {
            log.info("Seguimiento entity to dto error. Exception: " + e);
            return null;
        }
    }
}