package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoDAO extends GenericRepository<ArchivoModel> {
    List<ArchivoModel> findAllByTipo(TipoArchivoEnum tipo);
    List<ArchivoModel> findAllByTipoAndBorradoIsNull(TipoArchivoEnum tipo);
    Long countAllByTipo(TipoArchivoEnum tipo);
    Long countAllByTipoAndBorradoIsNull(TipoArchivoEnum tipo);
}
