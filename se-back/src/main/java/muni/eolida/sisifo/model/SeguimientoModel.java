package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "seguimiento")
public class SeguimientoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;

	// Bidereccional primaria
	@OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EstadoReclamoModel> estados;
}