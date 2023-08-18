package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;

public interface BarrioService extends ServicioGenerico<BarrioCreation, BarrioModel> {
    EntidadMensaje<BarrioModel> buscarTodasPorBarrio(String barrio);
    EntidadMensaje<BarrioModel> buscarTodasPorBarrioConEliminadas(String barrio);
}
