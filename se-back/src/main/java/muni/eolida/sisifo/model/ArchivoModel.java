package muni.eolida.sisifo.model;

import lombok.EqualsAndHashCode;
import muni.eolida.sisifo.model.enums.TipoArchivoEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
@Setter
@Table(name = "archivo")
@EqualsAndHashCode
public class ArchivoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private TipoArchivoEnum tipo;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private String tamanio;
}