package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;

import java.util.List;

public interface ReclamoService extends GenericService<ReclamoCreation, ReclamoModel> {
    EntityMessenger<ReclamoModel> buscarPorCreador(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorConBorrados(Long id);
    EntityMessenger<ReclamoModel> buscarMisReclamos();

    List<ReclamoModel> findAllByCreadorId(Long id);
    List<ReclamoModel> findAllByCreadorIdAndBorradoIsNull(Long id);
    List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCase(String nombre);
    List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCaseAndBorradoIsNull(String nombre);
    List<ReclamoModel> findAllByCalleId(Long id);
    List<ReclamoModel> findAllByCalleIdAndBorradoIsNull(Long id);
    List<ReclamoModel> findAllByCalleCalleContainingIgnoreCase(String calle);
    List<ReclamoModel> findAllByCalleCalleContainingIgnoreCaseAndBorradoIsNull(String calle);
    List<ReclamoModel> findAllByTipoReclamoId(Long id);
    List<ReclamoModel> findAllByTipoReclamoIdAndBorradoIsNull(Long id);
    List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCase(String tipo);
    List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCaseAndBorradoIsNull(Long tipo);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCaseAndBorradoIsNull(Long descripcion);
}
