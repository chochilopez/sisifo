package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class ReclamoCreation {
    private String altura;
    private Long barrio_id;
    private Long calle_id;
    private Long interseccion_id;
    private String coordinadaX;
    private String coordinadaY;
    private String descripcion;
    private Long entreCalle1_id;
    private Long entreCalle2_id;
    private byte[] imagen;
    private Long tipoReclamo_id;
    private Long persona_id;
}
