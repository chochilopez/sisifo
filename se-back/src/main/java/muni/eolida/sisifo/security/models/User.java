package muni.eolida.sisifo.security.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "usario")
public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @Column(name = "nombre")
      private String name;
      @Column(name = "dni")
      private String idNumber;
      @Column(name = "direccion")
      private String address;
      @Column(name = "telephone")
      private String telephone;
      @Column(name = "nombreUsuario")
      private String username;
      @Column(name = "contrasenia")
      private String password;
      @ManyToMany(fetch = FetchType.LAZY)
      @JoinTable(  name = "rol_usuario", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
      private Set<Role> roles = new HashSet<>();

      public User(String name, String idNumber, String address, String telephone, String username, String password) {
            this.name = name;
            this.idNumber = idNumber;
            this.address = address;
            this.telephone = telephone;
            this.username = username;
            this.password = password;
      }
}
