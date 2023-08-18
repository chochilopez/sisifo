package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;

public interface AreaService extends ServicioGenerico<AreaCreation, AreaModel> {
    EntidadMensaje<AreaModel> buscarTodasPorArea (String area);
    EntidadMensaje<AreaModel> buscarTodasPorAreaConEliminadas (String area);
}
