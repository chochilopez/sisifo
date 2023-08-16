package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamoDAO extends GenericRepository<ReclamoModel> {
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
