package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;

public interface CalleService extends GenericService<CalleCreation, CalleModel> {
    EntityMessenger<CalleModel> buscarTodasPorCalle (String calle);
    EntityMessenger<CalleModel> buscarTodasPorCalleConEliminadas (String calle);
}
