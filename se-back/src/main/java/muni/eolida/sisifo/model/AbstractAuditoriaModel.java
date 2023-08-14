package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Setter
public abstract class AbstractAuditoriaModel {
      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = UsuarioModel.class, fetch = FetchType.EAGER)
      private UsuarioModel creador;
      private LocalDateTime creado;

      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = UsuarioModel.class, fetch = FetchType.EAGER)
      private UsuarioModel borrador;
      private LocalDateTime borrado;

      @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
      @ManyToOne(targetEntity = UsuarioModel.class, fetch = FetchType.EAGER)
      private UsuarioModel modificador;
      private LocalDateTime modificacion;
}