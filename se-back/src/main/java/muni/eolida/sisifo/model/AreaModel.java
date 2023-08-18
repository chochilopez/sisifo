package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "area")
@EqualsAndHashCode
public class AreaModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String area;

    // Bidireccional
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "area_id")
    private List<TipoReclamoModel> tiposReclamos;
}
