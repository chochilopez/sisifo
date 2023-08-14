package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.VisitaModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaDAO extends GenericRepository<VisitaModel> {
    @Query(value = "SELECT * FROM visita desc limit :limit", nativeQuery = true)
    List<VisitaModel> findTopN(@Param("limit") int limit);
    @Query(value = "SELECT * FROM visita and baja is null desc limit :limit", nativeQuery = true)
    List<VisitaModel> findTopNAndBorradoIsNull(@Param("limit") int limit);
    List<VisitaModel> findAllByIpContaining(String ip);
    List<VisitaModel> findAllByIpContainingAndBorradoIsNull(String ip);
}
