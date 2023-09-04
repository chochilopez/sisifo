package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.EstadoReclamoEnum;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoReclamoDAO extends GenericDTO<EstadoReclamoModel> {

    List<EstadoReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<EstadoReclamoModel> findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(String descripcion);

    Optional<EstadoReclamoModel> findByEstado(EstadoReclamoEnum estado);
    Optional<EstadoReclamoModel> findByEstadoAndEliminadaIsNull(EstadoReclamoEnum estado);
}
