package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

import java.util.List;

@Entity
@EqualsAndHashCode
@Getter
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