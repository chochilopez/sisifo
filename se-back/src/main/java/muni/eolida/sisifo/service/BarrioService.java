package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;

import java.util.List;

public interface BarrioService extends GenericService<BarrioModel, BarrioCreation> {
    List<BarrioModel> buscarTodasPorBarrio(String barrio);
    List<BarrioModel> buscarTodasPorBarrioConEliminadas(String barrio);
}
