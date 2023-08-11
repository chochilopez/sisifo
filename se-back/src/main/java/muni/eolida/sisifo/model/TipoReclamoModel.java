package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import muni.eolida.sisifo.model.enums.TipoDocumentoEnum;
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
      private String areaResuelve;
      private Integer cantidadDiasResolucion;
      private String nombre;
      private TipoDocumentoEnum tipoDocumento;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = UsuarioModel.class, fetch = FetchType.EAGER)
      private UsuarioModel usuario;
      @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
      private ReclamoModel reclamo;
}