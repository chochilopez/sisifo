package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;

import java.util.List;

public interface TipoReclamoService extends GenericService<TipoReclamoModel, TipoReclamoCreation> {
    List<TipoReclamoModel> buscarTodasPorTipo(String tipo);
    List<TipoReclamoModel> buscarTodasPorTipoConEliminadas(String tipo);
}
