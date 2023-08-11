package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoReclamoCreation extends AbstractAuditoriaCreation {
    private String id;
    private String areaResuelve;
    private String cantidadDiasResolucion;
    private String nombre;
    private String tipoDocumento;
    private String usuario;
    private String reclamo;
}
