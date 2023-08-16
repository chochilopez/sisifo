package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "seguimiento")
@EqualsAndHashCode
public class SeguimientoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;

	@OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EstadoReclamoModel> estados;

	// Bidireccional
	@JoinColumn(name = "reclamo_id")
	@OneToOne(fetch = FetchType.LAZY)
	private ReclamoModel reclamo;
}