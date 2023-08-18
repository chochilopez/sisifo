package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "usuario")
@EqualsAndHashCode
public class UsuarioModel extends AbstractAuditoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 5, max = 50)
	private String nombre;

	@NotNull
	@Size(min = 6, max = 14)
	private String dni;

	@NotNull
	@Size(min = 6, max = 50)
	private String direccion;

	@NotNull
	@Size(min = 6, max = 20)
	private String telefono;
	private Boolean habilitada;

	@NotNull
	@Size(min = 8, max = 50)
	private String username;

	@NotNull
	@Size(min = 8, max = 20)
	private String password;
	private String token;

	@ManyToMany()
	@JoinTable(  name = "rol_usuario", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<RolModel> roles = new HashSet<>();

	// Bidireccional
	@OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReclamoModel> reclamos;
}
