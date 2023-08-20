package muni.eolida.sisifo.helper;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import muni.eolida.sisifo.model.UsuarioModel;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Setter
public abstract class AbstractAuditoriaModel {
    private LocalDateTime creada;
    private LocalDateTime modificada;
    private LocalDateTime eliminada;

    @ManyToOne()
    private UsuarioModel creador;
    @ManyToOne()
    private UsuarioModel modificador;
    @ManyToOne()
    private UsuarioModel eliminador;
}