package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericDTO;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoReclamoDAO extends GenericDTO<EstadoReclamoModel> {
    Optional<EstadoReclamoModel> findByEstado(TipoEstadoReclamoEnum estado);
    Optional<EstadoReclamoModel> findByEstadoAndEliminadaIsNull(TipoEstadoReclamoEnum estado);
}
