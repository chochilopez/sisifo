package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Schema(name = "Tipo de Reclamo", description = "Modelo de entidad de los posibles diferentes tipos de reclamo que puedan existir. TipoReclamo.")
@Setter
public class TipoReclamoDTO extends AbsAuditoriaDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Tipo de reclamo.")
    private String tipo;
}
