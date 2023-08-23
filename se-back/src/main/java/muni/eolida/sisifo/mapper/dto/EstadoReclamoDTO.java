package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Schema(name = "Estado del Reclamo", description = "Modelo de entidad de la entidad EstadoReclamo. Posibles estados que puede asumir un seguimiento.")
@Setter
public class EstadoReclamoDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Posibles estados que puede poseer un seguimiento de un reclamo. INICIADO - EN_CURSO - RESUELTO - PASE_SECTOR - RECHAZADO.")
    private String estado;
}
