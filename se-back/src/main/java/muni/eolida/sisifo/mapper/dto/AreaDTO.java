package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Schema(name = "Archivo", description = "Modelo de entidad que alamcena una imagen en el servidor.")
@Setter
public class AreaDTO implements Serializable {
    @Schema(name = "Archivo", description = "Modelo de entidad que alamcena una imagen en el servidor.")
    private String id;
    @Schema(name = "Archivo", description = "Modelo de entidad que alamcena una imagen en el servidor.")
    private String area;
}
