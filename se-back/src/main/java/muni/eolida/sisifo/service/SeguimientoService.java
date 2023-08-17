package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.SeguimientoModel;

import java.time.LocalDateTime;

public interface SeguimientoService extends GenericService<SeguimientoCreation, SeguimientoModel> {
	EntityMessenger<ReclamoModel> buscarTodasPorDescripcion(String descripcion);
	EntityMessenger<ReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
	EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechas(LocalDateTime inicio, LocalDateTime fin);
	EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(LocalDateTime inicio, LocalDateTime fin);
}
