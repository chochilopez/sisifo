package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "estado_reclamo")
public class EstadoReclamoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private TipoEstadoReclamoEnum estado;

	// Bidereccional secundaria
	@ManyToOne()
	@JoinColumn(name = "seguimiento_id")
	private SeguimientoModel seguimiento;
}