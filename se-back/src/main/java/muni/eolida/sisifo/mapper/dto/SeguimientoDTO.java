package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SeguimientoDTO extends AbstractAuditoriaDTO implements Serializable {
    private String id;
    private String descripcion;
    private String estado;
    private String estadoReclamo;
}
