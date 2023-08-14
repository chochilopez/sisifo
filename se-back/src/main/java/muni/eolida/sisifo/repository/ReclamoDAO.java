package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.ReclamoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamoDAO extends GenericRepository<ReclamoModel> {
    List<ReclamoModel> findAllByCreadorId(Long id);
    List<ReclamoModel> findAllByCreadorIdAndBorradoIsNull(Long id);
}
