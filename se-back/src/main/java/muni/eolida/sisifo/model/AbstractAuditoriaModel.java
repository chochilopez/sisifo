package muni.eolida.sisifo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Setter
public class AuditoriaModel {

      private LocalDateTime alta;
      private LocalDateTime baja;
      private LocalDateTime modificacion;
}