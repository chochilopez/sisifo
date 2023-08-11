package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@Table(name = "seguimiento")
@EqualsAndHashCode
public class SeguimientoModel extends AbstractAuditoriaModel {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String descripcion;
      private String estado;
      private TipoEstadoReclamoEnum estadoReclamo;
}