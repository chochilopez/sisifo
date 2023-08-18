package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;

public interface ReclamoService extends GenericService<ReclamoCreation, ReclamoModel> {

    EntityMessenger<ReclamoModel> buscarMisReclamos();

    EntityMessenger<ReclamoModel> buscarPorCreador(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorConBorrados(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorId(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorIdAndBorradoIsNull(Long id);
    EntityMessenger<ReclamoModel> buscarPorTipoReclamoId(Long id);
    EntityMessenger<ReclamoModel> buscarPorTipoReclamoIdAndBorradoIsNull(Long id);
    EntityMessenger<ReclamoModel> buscarPorBarrioId(Long id);
    EntityMessenger<ReclamoModel> buscarPorBarrioIdAndBorradoIsNull(Long id);
    EntityMessenger<ReclamoModel> buscarPorCalleId(Long id);
    EntityMessenger<ReclamoModel> buscarPorCalleIdAndBorradoIsNull(Long id);
    EntityMessenger<ReclamoModel> buscarTodosPorByCreadorNombre(String nombre);
    EntityMessenger<ReclamoModel> buscarTodosPorCreadorNombreAndBorradoIsNull(String nombre);
    EntityMessenger<ReclamoModel> buscarTodosPorCalleCalle(String calle);
    EntityMessenger<ReclamoModel> buscarTodosPorCalleCalleAndBorradoIsNull(String calle);
    EntityMessenger<ReclamoModel> buscarTodosPorTipoReclamoTipo(String tipo);
    EntityMessenger<ReclamoModel> buscarTodosPorTipoReclamoTipoAndBorradoIsNull(Long tipo);
    EntityMessenger<ReclamoModel> buscarTodosPorDescripcion(String descripcion);
    EntityMessenger<ReclamoModel> buscarTodosPorDescripcionAndBorradoIsNull(Long descripcion);
    EntityMessenger<ReclamoModel> buscarTodosPorBarrioCalle(String barrio);
    EntityMessenger<ReclamoModel> buscarTodosPorBarrioCalleAndBorradoIsNull(String barrio);
}
