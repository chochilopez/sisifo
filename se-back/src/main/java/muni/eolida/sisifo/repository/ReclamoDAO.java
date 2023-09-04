package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.ReclamoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReclamoDAO extends GenericDTO<ReclamoModel> {
    List<ReclamoModel> findAllByPersonaId(Long id);
    List<ReclamoModel> findAllByPersonaIdAndEliminadaIsNull(Long id);
    List<ReclamoModel> findAllByBarrioId(Long id);
    List<ReclamoModel> findAllByBarrioIdAndEliminadaIsNull(Long id);
    List<ReclamoModel> findAllByCalleId(Long id);
    List<ReclamoModel> findAllByCalleIdAndEliminadaIsNull(Long id);
    List<ReclamoModel> findAllByTipoReclamoId(Long id);
    List<ReclamoModel> findAllByTipoReclamoIdAndEliminadaIsNull(Long id);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(String descripcion);
    @Query(value = "select * from reclamo r where creada BETWEEN :inicio AND :fin", nativeQuery = true)
    List<ReclamoModel> findAllByCreadaBetweenInicioAndFin(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    @Query(value = "select * from reclamo r where creada BETWEEN :inicio AND :fin and eliminada is null", nativeQuery = true)
    List<ReclamoModel> findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
