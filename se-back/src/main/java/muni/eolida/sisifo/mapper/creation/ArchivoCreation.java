package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivoCreation extends AbstractAuditoriaCreation {
    private String id;
    private String path;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String tamanio;
}
