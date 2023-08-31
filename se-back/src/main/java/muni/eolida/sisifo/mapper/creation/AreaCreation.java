package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Hidden
@Setter
public class AreaCreation extends AbsAuditoriaCreation {
    private String id;
    @NotNull
    @Size(min=4, max=40, message = "El campo area debe tener entre 4 y 40 caracteres.")
    private String area;
    private List<String> tiposReclamos_id;
}
