package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.model.LocalFileModel;
import muni.eolida.sisifo.model.enumerator.FiletypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalFileRepository extends JpaRepository<LocalFileModel, Long> {
    List<LocalFileModel> findAllByFiletype(FiletypeEnum filetype);
    Long countAllByFiletype(FiletypeEnum filetype);
}
