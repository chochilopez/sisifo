package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDTO extends GenericRepository<UsuarioModel> {
  Optional<UsuarioModel> findByUsername(String username);
  Optional<UsuarioModel> findByUsernameAndBajaIsNull(String username);
  Boolean existsByUsername(String username);
  Boolean existsByUsernameAndBajaIsNull(String username);
}
