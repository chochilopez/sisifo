package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCreation extends AbstractAuditoriaCreation {
    private String id;
    private String nombre;
    private String telefono;
    private String emisor;
    private String receptor;
    private String cc;
    private String asunto;
    private String enviado;
    private String texto;
    private String error;
}
