package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@MappedSuperclass
@Setter
public abstract class AbsAuditoriaDTO implements Serializable {
    @Schema(description = "Fecha de creacion.")
    private String creada;
    @Schema(description = "Fecha de modificacion.")
    private String modificada;
}
