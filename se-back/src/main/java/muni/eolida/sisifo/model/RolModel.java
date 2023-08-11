package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.*;
import muni.eolida.sisifo.model.enums.RolEnum;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@Table(name = "rol")
@EqualsAndHashCode
public class RolModel extends AbstractAuditoriaModel {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @Enumerated(EnumType.STRING)
      private RolEnum rol;

      public RolModel(RolEnum rol) {
        this.rol = rol;
      }
}