package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Hidden
@Setter
public class ArchivoCreation {
    private Long id;
    private String path;
    private String nombre;
}
