package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SeguimientoDTO implements Serializable {
    private String id;
    private String descripcion;
    private List<EstadoReclamoDTO> estados;
}
