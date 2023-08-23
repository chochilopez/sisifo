package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Schema(name = "Rol", description = "Modelo de entidad de un rol especifico de autoridad.")
@Setter
public class RolDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Nombre del rol o nuevo nivel de autoridad.")
    private String rol;
}
