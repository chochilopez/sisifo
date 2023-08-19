package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "calle")
@EqualsAndHashCode
public class CalleModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;

    // Bidireccional
    @OneToMany(mappedBy = "calle", cascade = CascadeType.PERSIST)
    private List<ReclamoModel> reclamos;
}