package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;
import muni.eolida.sisifo.model.enums.RolEnum;

@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "rol")
@EqualsAndHashCode
public class RolModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RolEnum rol;
}