package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.SeguimientoModel;

import java.time.LocalDateTime;

public interface SeguimientoService extends ServicioGenerico<SeguimientoCreation, SeguimientoModel> {
	EntidadMensaje<SeguimientoModel> buscarTodasPorDescripcion(String descripcion);
	EntidadMensaje<SeguimientoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
	EntidadMensaje<SeguimientoModel> buscarTodasPorCreadaEntreFechas(LocalDateTime inicio, LocalDateTime fin);
	EntidadMensaje<SeguimientoModel> buscarTodasPorCreadaEntreFechasConEliminadas(LocalDateTime inicio, LocalDateTime fin);
}
