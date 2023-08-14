package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.model.enums.TipoDocumentoEnum;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@Table(name = "tipo_reclamo")
@EqualsAndHashCode
public class TipoReclamoModel extends AbstractAuditoriaModel {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private Integer areaResuelve;
      private Integer cantidadDiasResolucion;
      private String nombre;
}