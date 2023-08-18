package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;

public interface EstadoReclamoService extends ServicioGenerico<EstadoReclamoCreation, EstadoReclamoModel> {
    EntidadMensaje<EstadoReclamoModel> buscarPorEstadoReclamo (String estado);
    EntidadMensaje<EstadoReclamoModel> buscarPorEstadoReclamoConEliminadas (String estado);
}
