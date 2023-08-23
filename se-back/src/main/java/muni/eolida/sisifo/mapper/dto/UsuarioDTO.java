package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(name = "Usuario", description = "Modelo de entidad de usuario para registro y datos personales.")
@Setter
public class UsuarioDTO implements Serializable {
    @Schema(description = "Identificador unico.")
    private String id;
    @Schema(description = "Apellido y nombre de la persona.")
    private String nombre;
    @Schema(description = "Numero de documento unico de identidad.")
    private String dni;
    @Schema(description = "Direccion fisica de la persona.")
    private String direccion;
    @Schema(description = "Telefono o celular de la persona.")
    private String telefono;
    @Schema(description = "Nombre de usuario en forma de direccion de correo.")
    private String username;
    @Schema(description = "Listado de los roles de la persona, sus niveles de autoridad.")
    private List<RolDTO> roles = new ArrayList<>();
}
