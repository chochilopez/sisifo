package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;

public interface EstadoReclamoService extends GenericService<EstadoReclamoCreation, EstadoReclamoModel> {
    EntityMessenger<EstadoReclamoModel> buscarPorSeguimientoId(Long id);
    EntityMessenger<EstadoReclamoModel> buscarPorSeguimientoIdAndBorradoIsNull(Long id);
    EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamo (TipoEstadoReclamoEnum estadoReclamo);
    EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamoConBorrados (TipoEstadoReclamoEnum estadoReclamo);

}
