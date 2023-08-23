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
@Table(name = "reclamo")
public class ReclamoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String coordinadaX;
	private String coordinadaY;
	private String descripcion;
	private String altura;

	@OneToOne()
	private ArchivoModel imagen;
	@OneToOne()
	private SeguimientoModel seguimiento;
	@ManyToOne()
	private CalleModel calle;
	@ManyToOne()
	private BarrioModel barrio;
	@ManyToOne()
	private CalleModel interseccion;
	@ManyToOne()
	private CalleModel entreCalle1;
	@ManyToOne()
	private CalleModel entreCalle2;
	@ManyToOne()
	private UsuarioModel persona;
	@ManyToOne()
	private TipoReclamoModel tipoReclamo;
}