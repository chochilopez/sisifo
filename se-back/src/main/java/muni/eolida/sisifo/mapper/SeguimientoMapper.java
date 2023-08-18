package muni.eolida.sisifo.mapper;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.service.implementation.EstadoReclamoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SeguimientoMapper {
    @Autowired
    private EstadoReclamoServiceImpl estadoReclamoService;
    @Autowired
    private EstadoReclamoMapper estadoReclamoMapper;

    public SeguimientoModel toEntity(SeguimientoCreation seguimientoCreation) {
        try {
            SeguimientoModel seguimientoModel = new SeguimientoModel();

            seguimientoModel.setDescripcion(seguimientoCreation.getDescripcion());
            List<EstadoReclamoModel> estados = new ArrayList<>();
            estados.add(estadoReclamoService.buscarPorEstadoReclamo("INICIADO").getObjeto());
            seguimientoModel.setEstados(estados);

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

            return dto;
        } catch (Exception e) {
            log.info("Seguimiento entity to dto error. Exception: " + e);
            return null;
        }
    }
}