package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.BarrioModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrioDAO extends GenericRepository<BarrioModel> {
    List<BarrioModel> findAllByBarrioIgnoreCaseContaining(String barrio);
    List<BarrioModel> findAllByBarrioIgnoreCaseContainingAndBorradoIsNull(String barrio);
}
