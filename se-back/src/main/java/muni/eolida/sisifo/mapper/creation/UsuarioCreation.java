package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "El nombre del contribuyente no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El nombre del contribuyente debe tener entre 2 y 100 caracteres.")
    private String nombre;
    @NotNull(message = "El dni del contribuyente no puede ser nulo.")
    @Size(min = 7, max = 20, message = "El dni del contribuyente debe tener entre 7 y 20 caracteres.")
    private String dni;
    @NotNull(message = "La direccion del contribuyente no puede ser nulo.")
    @Size(min = 8, max = 100, message = "La direccion del contribuyente debe tener entre 8 y 100 caracteres.")
    private String direccion;
    @NotNull(message = "El telefono del contribuyente no puede ser nulo.")
    @Size(min = 7, max = 20, message = "El telefono del contribuyente debe tener entre 7 y 20 caracteres.")
    private String telefono;
    private String habilitada;
    @Email(message = "El nombre de usuario debe tener formato de email valido.")
    @NotNull(message = "El nombre de usuario no puede ser nulo.")
    @Size(min = 8, max = 50, message = "El nombre de usuario debe tener entre 8 y 50 caracteres.")
    private String username;
    @NotNull(message = "La contraseña de usuario no puede ser nula.")
    @Size(min = 8, max = 50, message = "La contraseña de usuario debe tener entre 8 y 50 caracteres.")
    private String password;
    private String token;
    private List<String> tokens_id;
    private List<String> roles_id;

}
