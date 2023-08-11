package muni.eolida.sisifo.mapper.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Setter
public abstract class AbstractAuditoriaDTO {
      private LocalDateTime alta;
      private LocalDateTime baja;
      private LocalDateTime modificacion;
}