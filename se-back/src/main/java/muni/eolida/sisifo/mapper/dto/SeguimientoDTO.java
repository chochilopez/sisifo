package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Schema(name = "Seguimiento", description = "Modelo de entidad del proceso de seguimiento de un reclamo, desde su inicio a su finalizacion.")
@Setter
public class SeguimientoDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Descripcion del seguimiento, notas de los empleados.")
    private String descripcion;
    @Schema(description = "Estados posibles de seguimiento de reclamo. INICIADO - EN_CURSO - RESUELTO - PASE_SECTOR - RECHAZADO.")
    private List<EstadoReclamoDTO> estados;
}
