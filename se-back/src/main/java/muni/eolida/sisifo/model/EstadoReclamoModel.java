package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;

@Entity
@EqualsAndHashCode
@Getter
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