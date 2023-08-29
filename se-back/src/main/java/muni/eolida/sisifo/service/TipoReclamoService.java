package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;

public interface TipoReclamoService extends GenericService<TipoReclamoModel, TipoReclamoCreation> {
    EntityMessenger<TipoReclamoModel> buscarTodasPorAreaId(Long id);
    EntityMessenger<TipoReclamoModel> buscarTodasPorAreaIdConEliminadas(Long id);
    EntityMessenger<TipoReclamoModel> buscarTodasPorTipo(String tipo);
    EntityMessenger<TipoReclamoModel> buscarTodasPorTipoConEliminadas(String tipo);
}
