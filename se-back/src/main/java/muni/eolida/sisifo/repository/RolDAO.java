package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolDAO extends GenericDTO<RolModel> {
    Optional<RolModel> findByRol(RolEnum rol);
    Optional<RolModel> findByRolAndEliminadaIsNull(RolEnum rol);
}
