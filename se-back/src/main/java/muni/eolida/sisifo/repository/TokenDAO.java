package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenDAO extends JpaRepository<TokenModel, Long> {
	@Query(value = "select t from TokenModel t inner join UsuarioModel u on t.usuario.id = u.id where u.id = :id and (t.expirado = false or t.revocado = false)")
	List<TokenModel> findAllValidTokenByUser(Long id);

	Optional<TokenModel> findByToken(String token);
}
