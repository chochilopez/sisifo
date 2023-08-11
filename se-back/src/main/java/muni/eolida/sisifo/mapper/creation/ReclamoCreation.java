package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReclamoCreation extends AbstractAuditoriaCreation {
    private String id;
    private String altura;
    private String barrio;
    private String calleModel;
    private String calleInterseccion;
    private String coordinadaX;
    private String coordinadaY;
    private String descripcion;
    private String entreCalle1;
    private String entreCalle2;
    private String imagen;
    private String numero;
    private String persona;
    private String tipoReclamo;
}
