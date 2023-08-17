package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.CalleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalleDAO extends GenericRepository<CalleModel> {
    List<CalleModel> findAllByCalleIgnoreCaseContaining(String calle);
    List<CalleModel> findAllByCalleIgnoreCaseContainingAndEliminadaIsNull(String calle);
}
