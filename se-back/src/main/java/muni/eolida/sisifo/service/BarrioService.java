package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;

public interface BarrioService extends GenericService<BarrioCreation, BarrioModel> {
    EntityMessenger<BarrioModel> buscarTodasPorBarrio(String barrio);
    EntityMessenger<BarrioModel> buscarTodasPorBarrioConEliminadas(String barrio);
}
