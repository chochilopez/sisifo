package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class ArchivoDTO implements Serializable {
    private String path;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String tamanio;
}
