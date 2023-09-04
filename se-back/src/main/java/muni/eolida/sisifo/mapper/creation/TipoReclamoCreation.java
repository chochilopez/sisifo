package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Hidden
@Setter
public class TipoReclamoCreation extends AbsAuditoriaCreation {
    private String id;
    @NotNull(message = "El campo tipo no puede estar vacio.")
    @Size(min=4, max=40, message = "El campo tipo debe tener entre 4 y 40 caracteres.")
    private String tipo;
}
