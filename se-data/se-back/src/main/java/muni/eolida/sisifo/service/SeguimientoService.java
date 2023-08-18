package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.SeguimientoModel;

import java.util.List;

public interface SeguimientoService extends GenericService<SeguimientoCreation, SeguimientoModel> {

	List<ReclamoModel> buscarTodosPorDescripcion(String descripcion);
	List<ReclamoModel> buscarTodosPorDescripcionAndBorradoIsNull(String descripcion);
}
