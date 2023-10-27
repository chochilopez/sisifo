package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Hidden
@Setter
public class EstadoReclamoCreation extends AbsAuditoriaCreation {
    private String id;
    @NotNull(message = "El campo estado no puede estar vacio.")
    @Size(min=3, max=40, message = "El campo estado debe tener entre 3 y 40 caracteres.")
    private String estado;
    @NotNull(message = "El campo descripcion no puede estar vacio.")
    @Size(min=1, max=100, message = "El campo descripcion debe tener entre 1 y 100 caracteres.")
    private String descripcion;

    public EstadoReclamoCreation(String estado) {
        this.estado = estado;
    }
}
