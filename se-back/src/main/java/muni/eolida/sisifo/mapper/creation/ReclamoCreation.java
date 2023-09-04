package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class ReclamoCreation extends AbsAuditoriaCreation {
    private String id;
    private String coordinadaX;
    private String coordinadaY;
    @NotNull(message = "El campo descripcion no puede estar vacio.")
    @Size(min=3, max=200, message = "El campo descripcion debe tener entre 3 y 200 caracteres.")
    private String descripcion;
    @NotNull(message = "El campo altura no puede estar vacio.")
    @Size(min=3, max=40, message = "El campo altura debe tener entre 3 y 40 caracteres.")
    private String altura;
    private String imagen_id;
    private String seguimiento_id;
    @NotNull(message = "El campo calle no puede estar vacio.")
    private String calle_id;
    private String barrio_id;
    private String interseccion_id;
    private String entreCalle1_id;
    private String entreCalle2_id;
    @NotNull(message = "El campo persona no puede estar vacio.")
    private String persona_id;
    @NotNull(message = "El campo tipo reclamo no puede estar vacio.")
    private String tipoReclamo_id;
}
