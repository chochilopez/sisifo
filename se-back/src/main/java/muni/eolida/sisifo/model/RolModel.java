package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.model.enums.RolEnum;

@Getter
@Entity
@RequiredArgsConstructor
@Setter
@Table(name = "rol")
@EqualsAndHashCode
public class RolModel extends AbstractAuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RolEnum rol;

    public RolModel(RolEnum rol) {
        this.rol = rol;
    }
}