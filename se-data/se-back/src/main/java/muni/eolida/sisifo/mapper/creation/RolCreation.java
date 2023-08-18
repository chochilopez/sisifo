package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolCreation {
    private String rol;

    public RolCreation(String rol) {
        this.rol = rol;
    }
}
