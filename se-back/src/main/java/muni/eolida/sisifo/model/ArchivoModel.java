package muni.eolida.sisifo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@Hidden
@NoArgsConstructor
@Setter
@Table(name = "archivo")
public class ArchivoModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String nombre;
}