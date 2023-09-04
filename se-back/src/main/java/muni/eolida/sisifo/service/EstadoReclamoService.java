package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import java.util.List;

public interface EstadoReclamoService extends GenericService<EstadoReclamoModel, EstadoReclamoCreation> {
    EstadoReclamoModel buscarPorEstadoReclamo (String estado);
    EstadoReclamoModel buscarPorEstadoReclamoConEliminadas (String estado);

    List<EstadoReclamoModel> buscarTodasPorDescripcion(String descripcion);
    List<EstadoReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
}
