package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "tipo_reclamo")
@EqualsAndHashCode
public class TipoReclamoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;

	// Bidireccional
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "reclamo_id")
	private List<ReclamoModel> reclamos;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "area_id")
	private AreaModel area;
}