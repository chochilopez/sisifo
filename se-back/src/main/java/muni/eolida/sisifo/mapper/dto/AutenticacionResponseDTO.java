package muni.eolida.sisifo.mapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AutenticacionResponseDTO {

    @JsonProperty("token_acceso")
    private String tokenAcceso;
    @JsonProperty("token_refresco")
    private String tokenRefresco;
}
