package muni.eolida.sisifo.mapper.creation;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Setter
public abstract class AbstractAuditoriaCreation {
      private String alta;
      private LocalDateTime baja;
      private LocalDateTime modificacion;
}