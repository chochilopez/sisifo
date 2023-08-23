package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "tipo_reclamo")
public class TipoReclamoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;

	// Bidireccional secundario
	@ManyToOne()
	@JoinColumn(name = "area_id")
	private AreaModel area;
}