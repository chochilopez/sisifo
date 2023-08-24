package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;

public interface AreaService extends GenericService<AreaCreation, AreaModel> {
    EntityMessenger<AreaModel> buscarTodasPorArea (String area);
    EntityMessenger<AreaModel> buscarTodasPorAreaConEliminadas (String area);
    EntityMessenger<AreaModel> agregarTipoReclamoAArea (Long idArea, Long idTipoReclamo);
}
