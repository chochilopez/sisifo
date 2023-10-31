package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Schema(name = "Barrio", description = "Modelo de entidad de los barrios de la ciudad.")
@Setter
public class BarrioDTO extends AbsAuditoriaDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Nombre de los barrios de la ciudad.")
    private String barrio;
}
