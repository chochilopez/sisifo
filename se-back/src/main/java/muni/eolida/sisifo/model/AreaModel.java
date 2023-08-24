package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@Entity
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "area")
public class AreaModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String area;

    // Bidireccional primaria
    @OneToMany(mappedBy = "area", cascade = CascadeType.MERGE)
    private Set<TipoReclamoModel> tiposReclamos;
}
