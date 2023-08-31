package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Schema(name = "Respuesta a Autenticacion", description = "Modelo de entidad de respuesta a una autenticacion. AutenticacionResponseDTO")
@Data
@NoArgsConstructor
public class AutenticacionResponseDTO {

    @Schema(description = "Cadena de caracteres. Token de acceso a recursos protegidos.")
    private String tokenAcceso;
    @Schema(description = "Roles que posee el usuario.")
    private List<String> roles;
}
