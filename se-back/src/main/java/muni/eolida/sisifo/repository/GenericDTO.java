package muni.eolida.sisifo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericDTO<E> extends JpaRepository<E, Long> {
    Optional<E> findById(Long id);
    Optional<E> findByIdAndEliminadaIsNull(Long id);
    List<E> findAllByEliminadaIsNull();
    Long countAllByEliminadaIsNull();
}
