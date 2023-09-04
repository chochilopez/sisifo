package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "area_id")
    private Set<TipoReclamoModel> tipos = new HashSet<>();
}
