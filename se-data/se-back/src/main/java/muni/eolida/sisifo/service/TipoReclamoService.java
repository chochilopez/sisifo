package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;

import java.util.List;
import java.util.Optional;

public interface TipoReclamoService extends GenericService<TipoReclamoCreation, TipoReclamoModel> {
    EntityMessenger<TipoReclamoModel> findAllByAreaId(Long id);
    EntityMessenger<TipoReclamoModel> findAllByAreaIdAndBorradoIsNull(Long id);
    EntityMessenger<TipoReclamoModel> findAllByTipoIgnoreCaseContaining(String tipo);
    EntityMessenger<TipoReclamoModel> findAllByTipoIgnoreCaseContainingAndBorradoIsNull(String tipo);
    EntityMessenger<TipoReclamoModel> findAllByAreaAreaContainingIgnoreCase(String nombre);
    EntityMessenger<TipoReclamoModel> findAllByAreaAreaContainingIgnoreCaseAndBorradoIsNull(String nombre);
    EntityMessenger<TipoReclamoModel> buscarTodosPorNombre(String nombre);
    EntityMessenger<TipoReclamoModel> buscarTodosPorNombreConBorrados(String nombre);
}
