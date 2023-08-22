package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.SeguimientoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeguimientoDAO extends GenericDTO<SeguimientoModel> {
    List<SeguimientoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<SeguimientoModel> findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(String descripcion);

    @Query(value = "select * from seguimiento s where creada BETWEEN :inicio AND :fin", nativeQuery = true)
    List<SeguimientoModel> findAllByCreadaBetweenInicioAndFin(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    @Query(value = "select * from seguimiento s where creada BETWEEN :inicio AND :fin and eliminada is null", nativeQuery = true)
    List<SeguimientoModel> findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
