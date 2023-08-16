package muni.eolida.sisifo.repository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.model.TipoReclamoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDAO extends GenericRepository<AreaModel> {
    List<AreaModel> findAllByAreaIgnoreCaseContaining(String area);
    List<AreaModel> findAllByAreaIgnoreCaseContainingAndBorradoIsNull(String area);
}
