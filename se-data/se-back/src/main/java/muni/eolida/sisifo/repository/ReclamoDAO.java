package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReclamoDAO extends GenericRepository<ReclamoModel> {
    Optional<ReclamoModel> findByCreadorId(Long id);
    Optional<ReclamoModel> findByCreadorIdAndBorradoIsNull(Long id);
    Optional<ReclamoModel> findByBarrioId(Long id);
    Optional<ReclamoModel> findByBarrioIdAndBorradoIsNull(Long id);
    Optional<ReclamoModel> findByCalleId(Long id);
    Optional<ReclamoModel> findByCalleIdAndBorradoIsNull(Long id);
    Optional<ReclamoModel> findByTipoReclamoId(Long id);
    Optional<ReclamoModel> findByTipoReclamoIdAndBorradoIsNull(Long id);
    List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCase(String nombre);
    List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCaseAndBorradoIsNull(String nombre);
    List<ReclamoModel> findAllByBarrioCalleContainingIgnoreCase(String barrio);
    List<ReclamoModel> findAllByBarrioCalleContainingIgnoreCaseAndBorradoIsNull(String barrio);
    List<ReclamoModel> findAllByCalleCalleContainingIgnoreCase(String calle);
    List<ReclamoModel> findAllByCalleCalleContainingIgnoreCaseAndBorradoIsNull(String calle);
    List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCase(String tipo);
    List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCaseAndBorradoIsNull(Long tipo);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCaseAndBorradoIsNull(Long descripcion);
}
