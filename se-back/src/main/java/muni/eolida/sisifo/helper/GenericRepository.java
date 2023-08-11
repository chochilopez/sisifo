package gloit.ixionida.neso.helper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<E> extends JpaRepository<E, Long> {
    Optional<E> findById(Long id);
    Optional<E> findByIdAndRemovedIsNull(Long id);
    List<E> findAllByRemovedIsNull();
    Long countAllByRemovedIsNull();
}
