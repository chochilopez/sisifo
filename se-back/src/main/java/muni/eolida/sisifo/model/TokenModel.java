package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import muni.eolida.sisifo.model.enums.TipoToken;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
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
