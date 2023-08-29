package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.mapper.dto.AreaDTO;
import muni.eolida.sisifo.mapper.dto.TipoReclamoDTO;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.repository.AreaDAO;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
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