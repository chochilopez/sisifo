package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.mapper.dto.EstadoReclamoDTO;
import muni.eolida.sisifo.mapper.dto.SeguimientoDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.service.implementation.EstadoReclamoServiceImpl;
import muni.eolida.sisifo.service.implementation.ReclamoServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeguimientoMapper {
    private final ReclamoServiceImpl reclamoService;
    private final ReclamoMapper reclamoMapper;
    private final EstadoReclamoServiceImpl estadoReclamoService;
    private final EstadoReclamoMapper estadoReclamoMapper;

    public SeguimientoModel toEntity(SeguimientoCreation seguimientoCreation) {
        try {
            SeguimientoModel seguimientoModel = new SeguimientoModel();

            seguimientoModel.setDescripcion(seguimientoCreation.getDescripcion());
            EntityMessenger<ReclamoModel> modelo = reclamoService.buscarPorId(seguimientoCreation.getReclamo_id());
            if (modelo.getStatusCode() == 200)
                seguimientoModel.setReclamo(modelo.getObject());
            List<EstadoReclamoModel> estados = new ArrayList<>();
            estados.add(estadoReclamoService.buscarPorEstadoReclamo(TipoEstadoReclamoEnum.INICIADO).getObject());
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
            dto.setReclamo(reclamoMapper.toDto(seguimientoModel.getReclamo()));

            return dto;
        } catch (Exception e) {
            log.info("Seguimiento entity to dto error. Exception: " + e);
            return null;
        }
    }
}