package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;

import java.util.List;

public interface AreaService extends GenericService<AreaModel, AreaCreation> {
    List<AreaModel> buscarTodasPorArea (String area);
    List<AreaModel> buscarTodasPorAreaConEliminadas (String area);
    AreaModel agregarTipoReclamoAArea (Long idArea, Long idTipoReclamo);
}
