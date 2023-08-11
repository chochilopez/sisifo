package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioCreation extends AbstractAuditoriaCreation {
    private String id;
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private String username;
    private String password;
    private List<String> roles;
}
