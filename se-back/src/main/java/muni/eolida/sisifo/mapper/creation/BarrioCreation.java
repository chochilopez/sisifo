package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class BarrioCreation extends AbsAuditoriaCreation {
    private String id;
    @NotNull(message = "El campo barrio no puede estar vacio.")
    @Size(min=4, max=40, message = "El campo barrio debe tener entre 4 y 40 caracteres.")
    private String barrio;
}
