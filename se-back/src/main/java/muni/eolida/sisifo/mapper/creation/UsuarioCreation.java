package muni.eolida.sisifo.mapper.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsuarioCreation {
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private String username;
    private String password;
}
