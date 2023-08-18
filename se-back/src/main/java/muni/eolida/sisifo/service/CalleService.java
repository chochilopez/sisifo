package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;

public interface CalleService extends ServicioGenerico<CalleCreation, CalleModel> {
    EntidadMensaje<CalleModel> buscarTodasPorCalle (String calle);
    EntidadMensaje<CalleModel> buscarTodasPorCalleConEliminadas (String calle);
}
