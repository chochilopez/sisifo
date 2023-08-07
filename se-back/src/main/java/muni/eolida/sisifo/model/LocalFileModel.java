package muni.eolida.sisifo.model;

import muni.eolida.sisifo.model.enumerator.FiletypeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Entity
@RequiredArgsConstructor
@Setter
public class LocalFileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    @Column(name = "nombreArchivo")
    private String filename;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoArchivo")
    private FiletypeEnum filetype;
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String description;
    @Column(name = "tamanioArchivo")
    private String filesize;
}