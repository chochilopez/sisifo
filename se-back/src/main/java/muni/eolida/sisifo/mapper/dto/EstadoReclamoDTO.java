package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class EstadoReclamoDTO implements Serializable {
    private String id;
    private String estado;
}