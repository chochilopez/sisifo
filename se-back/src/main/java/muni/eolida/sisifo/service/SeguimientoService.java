package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.SeguimientoModel;

public interface SeguimientoService extends GenericService<SeguimientoCreation, SeguimientoModel> {
	EntityMessenger<SeguimientoModel> buscarTodasPorDescripcion(String descripcion);
	EntityMessenger<SeguimientoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
	EntityMessenger<SeguimientoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin);
	EntityMessenger<SeguimientoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin);
}
