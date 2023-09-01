package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "area")
public class AreaModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String area;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<TipoReclamoModel> tiposReclamos;
}
