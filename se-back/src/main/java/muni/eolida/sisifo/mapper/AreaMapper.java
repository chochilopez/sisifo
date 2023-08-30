package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.mapper.dto.AreaDTO;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.AreaDAO;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class AreaMapper {
    private final TipoReclamoDAO tipoReclamoDAO;
    private final TipoReclamoMapper tipoReclamoMapper;
    private final AreaDAO areaDAO;
    private final UsuarioDAO usuarioDAO;

    public AreaModel toEntity(AreaCreation areaCreation) {
        try {
            AreaModel areaModel = new AreaModel();

            if (Helper.getLong(areaCreation.getId()) != null) {
                areaModel = areaDAO.findByIdAndEliminadaIsNull(Helper.getLong(areaCreation.getId())).get();
            }
            areaModel.setArea(areaCreation.getArea());

            Set<TipoReclamoModel> tiposReclamo = new HashSet<>();
            if (areaCreation.getTiposReclamos_id() != null) {
                for (String tipoReclamoId:areaCreation.getTiposReclamos_id()) {
                    if (Helper.getLong(tipoReclamoId) != null) {
                        Optional<TipoReclamoModel> tipoReclamo = tipoReclamoDAO.findByIdAndEliminadaIsNull(Helper.getLong(tipoReclamoId));
                        if (tipoReclamo.isPresent()) {
                            tiposReclamo.add(tipoReclamo.get());
                        }
                    }
                }
            }
            areaModel.setTiposReclamos(tiposReclamo);

            if (Helper.getLong(areaCreation.getCreador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(areaCreation.getCreador_id()));
                if (user.isPresent())
                    areaModel.setCreador(user.get());
            }
            if (!Helper.isEmptyString(areaCreation.getCreada()))
                areaModel.setCreada(Helper.stringToLocalDateTime(areaCreation.getCreada(), ""));
            if (Helper.getLong(areaCreation.getModificador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(areaCreation.getModificador_id()));
                if (user.isPresent())
                    areaModel.setModificador(user.get());
            }
            if (!Helper.isEmptyString(areaCreation.getModificada()))
                areaModel.setModificada(Helper.stringToLocalDateTime(areaCreation.getModificada(), ""));
            if (Helper.getLong(areaCreation.getEliminador_id()) != null) {
                Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(areaCreation.getEliminador_id()));
                if (user.isPresent())
                    areaModel.setEliminador(user.get());
            }
            if (!Helper.isEmptyString(areaCreation.getEliminada()))
                areaModel.setEliminada(Helper.stringToLocalDateTime(areaCreation.getEliminada(), ""));

            return areaModel;
        } catch (Exception e) {
            log.info("Area creation to entity error. Exception: " + e);
            return null;
        }
    }

    public AreaDTO toDto(AreaModel areaModel) {
        try {
            AreaDTO dto = new AreaDTO();

            dto.setId(areaModel.getId().toString());
            dto.setArea(areaModel.getArea());
            List<TipoReclamoDTO> listado = new ArrayList<>();
            for (TipoReclamoModel tipoReclamo:areaModel.getTiposReclamos()) {
                listado.add(tipoReclamoMapper.toDto(tipoReclamo));
            }
            dto.setTiposReclamos(listado);

            return dto;
        } catch (Exception e) {
            log.info("Area entity to dto error. Exception: " + e);
            return null;
        }
    }
}