package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Schema(name = "Seguimiento", description = "Modelo de entidad del proceso de seguimiento de un reclamo, desde su inicio a su finalizacion.")
@Setter
public class SeguimientoDTO extends AbsAuditoriaDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    private List<EstadoReclamoDTO> estados;
}
