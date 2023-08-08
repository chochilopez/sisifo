package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.ArchivoModel;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<ArchivoModel, Long> {
    List<ArchivoModel> buscarTodosPorTipo(TipoArchivoEnum tipo);
    Long contarTodosPorTipo(TipoArchivoEnum tipo);
}
