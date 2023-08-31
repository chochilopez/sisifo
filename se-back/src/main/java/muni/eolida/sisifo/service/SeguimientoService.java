package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.SeguimientoModel;

import java.util.List;

public interface SeguimientoService extends GenericService<SeguimientoModel, SeguimientoCreation> {
	List<SeguimientoModel> buscarTodasPorDescripcion(String descripcion);
	List<SeguimientoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
	List<SeguimientoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin);
	List<SeguimientoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin);
}
