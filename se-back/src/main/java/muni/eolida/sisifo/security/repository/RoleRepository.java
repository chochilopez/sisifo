package muni.eolida.sisifo.security.repository;

import muni.eolida.sisifo.security.models.Role;
import muni.eolida.sisifo.security.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRole(RoleEnum role);
}
