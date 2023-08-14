package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;

public interface TipoReclamoService extends GenericService<TipoReclamoCreation, TipoReclamoModel> {
    EntityMessenger<TipoReclamoModel> buscarTodosPorNombre(String nombre);
    EntityMessenger<TipoReclamoModel> buscarTodosPorNombreConBorrados(String nombre);
}
