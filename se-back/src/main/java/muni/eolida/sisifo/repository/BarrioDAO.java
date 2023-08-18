package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.RepositorioGenerico;
import muni.eolida.sisifo.model.BarrioModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrioDAO extends RepositorioGenerico<BarrioModel> {
    List<BarrioModel> findAllByBarrioIgnoreCaseContaining(String barrio);
    List<BarrioModel> findAllByBarrioIgnoreCaseContainingAndEliminadaIsNull(String barrio);
}
