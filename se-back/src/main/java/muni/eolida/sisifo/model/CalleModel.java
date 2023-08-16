package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToMany(mappedBy = "calle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReclamoModel> reclamos;
}