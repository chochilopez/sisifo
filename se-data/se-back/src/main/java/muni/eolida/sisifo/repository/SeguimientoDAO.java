package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.model.SeguimientoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoDAO extends GenericRepository<SeguimientoModel> {
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCase(String descripcion);
    List<ReclamoModel> findAllByDescripcionContainingIgnoreCaseAndBorradoIsNull(String descripcion);
}
