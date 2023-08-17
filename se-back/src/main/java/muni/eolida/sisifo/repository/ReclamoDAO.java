package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.ReclamoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReclamoDAO extends GenericRepository<ReclamoModel> {
    Optional<ReclamoModel> findByCreadorId(Long id);
    Optional<ReclamoModel> findByCreadorIdAndEliminadaIsNull(Long id);
    Optional<ReclamoModel> findByBarrioId(Long id);
    Optional<ReclamoModel> findByBarrioIdAndEliminadaIsNull(Long id);
    Optional<ReclamoModel> findByCalleId(Long id);
    Optional<ReclamoModel> findByCalleIdAndEliminadaIsNull(Long id);
    Optional<ReclamoModel> findByTipoReclamoId(Long id);
    Optional<ReclamoModel> findByTipoReclamoIdAndEliminadaIsNull(Long id);
    List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCase(String nombre);
    List<ReclamoModel> findAllByCreadorNombreContainingIgnoreCaseAndEliminadaIsNull(String nombre);
    List<ReclamoModel> findAllByBarrioBarrioContainingIgnoreCase(String barrio);
    List<ReclamoModel> findAllByBarrioBarrioContainingIgnoreCaseAndEliminadaIsNull(String barrio);
    List<ReclamoModel> findAllByCalleCalleContainingIgnoreCase(String calle);
    List<ReclamoModel> findAllByCalleCalleContainingIgnoreCaseAndEliminadaIsNull(String calle);
    List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCase(String tipo);
    List<ReclamoModel> findAllByTipoReclamoTipoContainingIgnoreCaseAndEliminadaIsNull(String tipo);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(String descripcion);
    @Query(value = "select * from reclamo r where creada BETWEEN :inicio AND :fin")
    List<ReclamoModel> findAllByCreadaBetweenInicioAndFin(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    @Query(value = "select * from reclamo r where creada BETWEEN :inicio AND :fin and eliminada is null")
    List<ReclamoModel> findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
