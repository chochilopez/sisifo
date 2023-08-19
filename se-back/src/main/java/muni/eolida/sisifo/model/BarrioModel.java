package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "barrio")
@EqualsAndHashCode
public class BarrioModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String barrio;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "barrio_id")
	private List<ReclamoModel> reclamos;
}
