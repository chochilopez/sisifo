package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO implements Serializable {
    private String id;
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private String username;
    private List<RolDTO> roles = new ArrayList<>();
}
