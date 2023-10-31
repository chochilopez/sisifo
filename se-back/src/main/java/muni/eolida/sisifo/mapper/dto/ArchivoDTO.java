package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Schema(name = "Archivo", description = "Modelo de entidad que alamcena una imagen en el servidor.")
@Setter
public class ArchivoDTO extends AbsAuditoriaDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Direccion absoluta del recurso imagen.")
    private String path;
    @Schema(description = "Nombre unico asignado automaticamente al recurso.")
    private String nombre;
}
