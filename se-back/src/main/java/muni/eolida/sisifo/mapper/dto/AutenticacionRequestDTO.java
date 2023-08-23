package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Schema(name = "Pedido de Autenticacion", description = "Modelo de entidad de pedido de autenticacion al servidor. AutenticacionRequestDTO")
public class AutenticacionRequestDTO {

    @Schema(description = "Nombre de usuario unico, en forma de email.")
    private String username;
    @Schema(description = "Contraseña de acceso alfanumerica del usuario.")
    private String password;
}
