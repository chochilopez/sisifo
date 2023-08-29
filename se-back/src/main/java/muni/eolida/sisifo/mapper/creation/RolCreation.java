package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class RolCreation {
    private String id;
    private String rol;

    public RolCreation(String rol) {
        this.rol = rol;
    }
}
