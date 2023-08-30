package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@Getter
@Hidden
@Setter
public class UsuarioCreation extends AbsAuditoriaCreation {
    private String id;
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private String habilitada;
    private String username;
    private String password;
    private String token;
    private List<String> tokens_id;
    private List<String> roles_id;

}
