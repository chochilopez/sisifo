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
public class SeguimientoCreation extends AbsAuditoriaCreation {
    private String id;
    private List<String> estados_id;
}
