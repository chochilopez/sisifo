package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TipoReclamoDTO implements Serializable {
    private String areaResuelve;
    private String cantidadDiasResolucion;
    private String nombre;
    private String tipoDocumento;
    private UsuarioDTO usuario;
}
