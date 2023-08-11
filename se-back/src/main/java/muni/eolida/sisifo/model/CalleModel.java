package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@Table(name = "calle")
@EqualsAndHashCode
public class CalleModel extends AuditoriaModel {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String calle;
}