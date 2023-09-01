package muni.eolida.sisifo.mapper.creation;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Hidden
@Setter
public class TipoReclamoCreation extends AbsAuditoriaCreation {
    private String id;
    private String tipo;
    private String area_id;
}
