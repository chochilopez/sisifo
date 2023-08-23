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
@Table(name = "barrio")
public class BarrioModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String barrio;
}
