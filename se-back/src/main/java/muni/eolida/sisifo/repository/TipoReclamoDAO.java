package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.TipoReclamoModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoReclamoDAO extends GenericRepository<TipoReclamoModel> {
    List<TipoReclamoModel> findAllByAreaId(Long id);
    List<TipoReclamoModel> findAllByAreaIdAndEliminadaIsNull(Long id);
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContaining(String tipo);
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContainingAndEliminadaIsNull(String tipo);
    List<TipoReclamoModel> findAllByAreaAreaContainingIgnoreCase(String nombre);
    List<TipoReclamoModel> findAllByAreaAreaContainingIgnoreCaseAndEliminadaIsNull(String nombre);
}
