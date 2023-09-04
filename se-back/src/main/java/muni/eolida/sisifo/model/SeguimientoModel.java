package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "seguimiento")
public class SeguimientoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "seguimiento_id")
	private Set<EstadoReclamoModel> estados = new HashSet<>();
}