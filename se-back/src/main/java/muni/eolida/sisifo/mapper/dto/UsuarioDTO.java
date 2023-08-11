package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO extends AbstractAuditoriaDTO implements Serializable {
    private String id;
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private String username;
    private String password;
    private List<RolDTO> roles;
}
