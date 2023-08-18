package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;

public interface TipoReclamoService extends ServicioGenerico<TipoReclamoCreation, TipoReclamoModel> {
    EntidadMensaje<TipoReclamoModel> buscarTodasPorAreaId(Long id);
    EntidadMensaje<TipoReclamoModel> buscarTodasPorAreaIdConEliminadas(Long id);
    EntidadMensaje<TipoReclamoModel> buscarTodasPorTipo(String tipo);
    EntidadMensaje<TipoReclamoModel> buscarTodasPorTipoConEliminadas(String tipo);
}
