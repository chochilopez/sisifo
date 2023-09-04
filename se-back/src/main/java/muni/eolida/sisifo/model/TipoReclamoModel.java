package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "tipo_reclamo")
public class TipoReclamoModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
}