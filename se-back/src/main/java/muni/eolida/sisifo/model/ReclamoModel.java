package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "reclamo")
@EqualsAndHashCode
public class ReclamoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Bidireccional
	@NotNull
	@Size(min = 1, max = 20)
	private String altura;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "calle_id")
	private CalleModel calle;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "barrio_id")
	private CalleModel barrio;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "interseccion_id")
	private CalleModel interseccion;
	private String coordinadaX;
	private String coordinadaY;
	private String descripcion;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "entreCalle1_id")
	private CalleModel entreCalle1;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "entreCalle2_id")
	private CalleModel entreCalle2;

	// Bidireccional
	@OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private ArchivoModel imagen;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "persona_id")
	private UsuarioModel persona;

	// Bidireccional
	@ManyToOne()
	@JoinColumn(name = "tipoReclamo_id")
	private TipoReclamoModel tipoReclamo;

	// Bidireccional
	@OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private SeguimientoModel seguimiento;
}