package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Schema(name = "Archivo", description = "Modelo de entidad que alamcena una imagen en el servidor.")
@Setter
public class ArchivoDTO implements Serializable {
    @Schema(name = "Identificador unico.")
    private String id;
    @Schema(name = "Direccion absoluta del recurso imagen.")
    private String path;
    @Schema(name = "Nombre unico asignado automaticamente al recurso.")
    private String nombre;
}
