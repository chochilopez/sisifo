package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.VisitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<VisitModel, Long> {
    @Query(value = "SELECT * FROM visita desc limit :limit", nativeQuery = true)
    List<VisitModel> findTopN(@Param("limit") int limit);
    List<VisitModel> findAllByIpContaining(String ip);
}
