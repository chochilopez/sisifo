package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.RepositorioGenerico;
import muni.eolida.sisifo.model.AreaModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDAO extends RepositorioGenerico<AreaModel> {
    List<AreaModel> findAllByAreaIgnoreCaseContaining(String area);
    List<AreaModel> findAllByAreaIgnoreCaseContainingAndEliminadaIsNull(String area);
}
