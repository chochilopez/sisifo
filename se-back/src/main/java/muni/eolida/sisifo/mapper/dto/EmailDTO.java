package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class EmailDTO extends AbstractAuditoriaDTO implements Serializable {
    private String id;
    private String asunto;
    private String cc;
    private String emisor;
    private String enviado;
    private String error;
    private String nombre;
    private String receptor;
    private String telefono;
    private String texto;
}
