package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;

public interface CalleService extends GenericService<CalleModel, CalleCreation> {
    EntityMessenger<CalleModel> buscarTodasPorCalle (String calle);
    EntityMessenger<CalleModel> buscarTodasPorCalleConEliminadas (String calle);
}
