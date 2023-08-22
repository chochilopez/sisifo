package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.AreaModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDAO extends GenericDTO<AreaModel> {
    List<AreaModel> findAllByAreaIgnoreCaseContaining(String area);
    List<AreaModel> findAllByAreaIgnoreCaseContainingAndEliminadaIsNull(String area);
}
