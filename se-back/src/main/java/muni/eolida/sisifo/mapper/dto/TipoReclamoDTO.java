package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TipoReclamoDTO implements Serializable {
    private String id;
    private String nombre;
}
