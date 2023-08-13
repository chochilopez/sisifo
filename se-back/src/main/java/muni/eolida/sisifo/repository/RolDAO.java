package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolDAO extends GenericRepository<RolModel> {
    Optional<RolModel> findByRol(RolEnum rol);
    Optional<RolModel> findByRolAndBajaIsNull(RolEnum rol);
}
