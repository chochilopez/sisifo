package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Builder
@Entity
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Schema(description = "Tutorial Model Information")
@Setter
@Table(name = "archivo")
public class ArchivoModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "Product ID", example = "1", required = true)
    private String path;
    @Schema(name = "Product ID", example = "1", required = true)
    private String nombre;
}