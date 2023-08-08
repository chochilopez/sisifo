package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@Table(name = "usuario")
@EqualsAndHashCode
public class UsuarioModel {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String nombre;
      private String dni;
      private String direccion;
      private String telefono;
      private String username;
      private String password;
      @ManyToMany(fetch = FetchType.LAZY)
      @JoinTable(  name = "rol_usuario", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
      private Set<RolModel> roles = new HashSet<>();

      public UsuarioModel(String nombre, String dni, String direccion, String telefono, String username, String password) {
            this.nombre = nombre;
            this.dni = dni;
            this.direccion = direccion;
            this.telefono = telefono;
            this.username = username;
            this.password = password;
      }
}
