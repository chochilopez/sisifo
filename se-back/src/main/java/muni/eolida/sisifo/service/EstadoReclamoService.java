package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;

public interface EstadoReclamoService extends GenericService<EstadoReclamoModel, EstadoReclamoCreation> {
    EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamo (String estado);
    EntityMessenger<EstadoReclamoModel> buscarPorEstadoReclamoConEliminadas (String estado);
}
