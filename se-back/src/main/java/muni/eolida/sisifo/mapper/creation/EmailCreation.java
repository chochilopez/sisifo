package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCreation extends AbstractAuditoriaCreation {
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
