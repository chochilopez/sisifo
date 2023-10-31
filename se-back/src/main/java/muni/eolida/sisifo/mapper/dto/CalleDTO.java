package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Schema(name = "Calle", description = "Modelo de entidad de una calle de la ciudad.")
@Setter
public class CalleDTO extends AbsAuditoriaDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Nombre completo de la calle.")
    private String calle;
}
