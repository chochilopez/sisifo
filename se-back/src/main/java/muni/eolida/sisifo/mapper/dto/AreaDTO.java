package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Schema(name = "Archivo", description = "Modelo de entidad que alamcena una imagen en el servidor.")
@Setter
public class AreaDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Nombre del area que atiende el reclamo.")
    private String area;
    @Schema(description = "Tipos de reclamo que atiende el Area.")
    private List<TipoReclamoDTO> tiposReclamos;
}
