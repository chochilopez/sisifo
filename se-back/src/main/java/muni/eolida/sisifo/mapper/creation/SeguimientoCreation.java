package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Hidden
@Setter
public class SeguimientoCreation extends AbsAuditoriaCreation {
    private String id;
    private String descripcion;
    private List<String> estados_id;
}
