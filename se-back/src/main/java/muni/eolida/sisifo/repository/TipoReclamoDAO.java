package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.TipoReclamoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoReclamoDAO extends GenericDTO<TipoReclamoModel> {
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContaining(String tipo);
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContainingAndEliminadaIsNull(String tipo);
}
