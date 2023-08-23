package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import muni.eolida.sisifo.model.enums.TipoToken;

@AllArgsConstructor
@Builder
@Data
@Entity
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "token")
public class TokenModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@Column(unique = true)
	public String token;

	@Enumerated(EnumType.STRING)
	public TipoToken tipoToken = TipoToken.BEARER;

	public boolean revocado;

	public boolean expirado;

	@ManyToOne()
	@JoinColumn(name = "usuario_id")
	private UsuarioModel usuario;
}
