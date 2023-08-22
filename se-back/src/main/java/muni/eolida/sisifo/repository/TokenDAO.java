package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenDAO extends JpaRepository<TokenModel, Long> {
	@Query(value = """
            select t from token t inner join User u\s
            on t.user.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """, nativeQuery = true)
	List<TokenModel> findAllValidTokenByUser(Long id);

	Optional<TokenModel> findByToken(String token);
}
