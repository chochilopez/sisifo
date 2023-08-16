package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.TipoReclamoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoReclamoDAO extends GenericRepository<TipoReclamoModel> {
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContaining(String tipo);
    List<TipoReclamoModel> findAllByTipoIgnoreCaseContainingAndBorradoIsNull(String tipo);
}
