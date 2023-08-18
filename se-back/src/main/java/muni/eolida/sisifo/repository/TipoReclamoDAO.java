package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.RepositorioGenerico;
import muni.eolida.sisifo.model.TipoReclamoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoReclamoDAO extends RepositorioGenerico<TipoReclamoModel> {
    List<TipoReclamoModel> findAllByAreaId(Long id);
    List<TipoReclamoModel> findAllByAreaIdAndEliminadaIsNull(Long id);
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContaining(String tipo);
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContainingAndEliminadaIsNull(String tipo);
}
