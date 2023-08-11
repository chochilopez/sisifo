package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@Table(name = "reclamo")
@EqualsAndHashCode
public class ReclamoModel extends AuditoriaModel {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String altura;
      private String barrio;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = CalleModel.class, fetch = FetchType.EAGER)
      private CalleModel calleModel;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = CalleModel.class, fetch = FetchType.EAGER)
      private CalleModel calleInterseccion;
      private String coordinadaX;
      private String coordinadaY;
      private String descripcion;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = CalleModel.class, fetch = FetchType.EAGER)
      private CalleModel entreCalle1;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = CalleModel.class, fetch = FetchType.EAGER)
      private CalleModel entreCalle2;
      @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
      private ArchivoModel imagen;
      private String numero;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = UsuarioModel.class, fetch = FetchType.EAGER)
      private UsuarioModel persona;
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = TipoReclamoModel.class, fetch = FetchType.EAGER)
      private TipoReclamoModel tipoReclamo;
}