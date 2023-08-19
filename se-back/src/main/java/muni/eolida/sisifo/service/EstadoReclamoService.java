package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;

public interface EstadoReclamoService extends GenericService<EstadoReclamoCreation, EstadoReclamoModel> {
    EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamo (String estado);
    EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamoConEliminadas (String estado);
}
