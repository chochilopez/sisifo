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
@Table(name = "calle")
public class CalleModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
}