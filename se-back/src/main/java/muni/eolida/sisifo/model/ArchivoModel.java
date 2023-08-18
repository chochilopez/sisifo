package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "archivo")
@EqualsAndHashCode
public class ArchivoModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String nombre;

    // Bidireccional
    @JoinColumn(name = "reclamo_id")
    @OneToOne(fetch = FetchType.LAZY)
    private ReclamoModel reclamo;
}