package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;

public interface TipoReclamoService extends GenericService<TipoReclamoCreation, TipoReclamoModel> {
    EntityMessenger<TipoReclamoModel> buscarTodasPorAreaId(Long id);
    EntityMessenger<TipoReclamoModel> buscarTodasPorAreaIdConEliminadas(Long id);
    EntityMessenger<TipoReclamoModel> buscarTodasPorTipo(String tipo);
    EntityMessenger<TipoReclamoModel> buscarTodasPorTipoConEliminadas(String tipo);
    EntityMessenger<TipoReclamoModel> buscarTodasPorAreaArea(String nombre);
    EntityMessenger<TipoReclamoModel> buscarTodasPorAreaAreaConEliminadas(String nombre);
    EntityMessenger<TipoReclamoModel> buscarTodasPorNombre(String nombre);
    EntityMessenger<TipoReclamoModel> buscarTodasPorNombreConEliminadas(String nombre);
}
