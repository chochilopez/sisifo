package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoReclamoDAO extends GenericRepository<EstadoReclamoModel> {
    Optional<EstadoReclamoModel> findBySeguimientoId(Long id);
    Optional<EstadoReclamoModel> findBySeguimientoIdAndBorradoIsNull(Long id);
    Optional<EstadoReclamoModel> findByEstado(TipoEstadoReclamoEnum estadoReclamo);
    Optional<EstadoReclamoModel> findByEstadoAndBorradoIsNull(TipoEstadoReclamoEnum estadoReclamo);
}
