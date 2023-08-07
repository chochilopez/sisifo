package muni.eolida.sisifo.security.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.security.models.enums.RoleEnum;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "rol")
public class Role {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @Enumerated(EnumType.STRING)
      @Column(name = "rol")
      private RoleEnum role;

      public Role(RoleEnum role) {
        this.role = role;
      }
}