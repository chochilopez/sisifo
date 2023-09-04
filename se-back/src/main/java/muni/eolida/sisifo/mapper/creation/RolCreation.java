package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class RolCreation {
    private String id;
    @NotNull(message = "El campo rol no puede estar vacio.")
    @Size(min=4, max=40, message = "El campo rol debe tener entre 4 y 40 caracteres.")
    private String rol;

    public RolCreation(String rol) {
        this.rol = rol;
    }
}
