package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends GenericRepository<UsuarioModel> {
  Optional<UsuarioModel> findByUsernameContainingIgnoreCase(String username);
  Optional<UsuarioModel> findByUsernameContainingIgnoreCaseAndBorradoIsNullAndHabilitadaIsTrue(String username);
  Boolean existsByUsername(String username);
  Boolean existsByUsernameContainingIgnoreCase(String username);

  Optional<UsuarioModel> findByTokenAndBorradoIsNullAndHabilitadaIsFalse(String username);
}
