package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;

import java.util.List;

public interface CalleService extends GenericService<CalleModel, CalleCreation> {
    List<CalleModel> buscarTodasPorCalle (String calle);
    List<CalleModel> buscarTodasPorCalleConEliminadas (String calle);
}
