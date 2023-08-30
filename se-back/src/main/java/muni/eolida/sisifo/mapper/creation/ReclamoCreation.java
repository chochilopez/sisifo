package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class ReclamoCreation extends AbsAuditoriaCreation {
    private String id;
    private String coordinadaX;
    private String coordinadaY;
    private String descripcion;
    private String altura;
    private String imagen_id;
    private String seguimiento_id;
    private String calle_id;
    private String barrio_id;
    private String interseccion_id;
    private String entreCalle1_id;
    private String entreCalle2_id;
    private String persona_id;
    private String tipoReclamo_id;
}
