package muni.eolida.sisifo.util.email.repository;

import muni.eolida.sisifo.util.email.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailDAO extends JpaRepository<EmailModel,Long> {
    Optional<EmailModel> findById(Long id);
}
