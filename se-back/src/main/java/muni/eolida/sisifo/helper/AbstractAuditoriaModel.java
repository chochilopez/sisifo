package muni.eolida.sisifo.helper;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import muni.eolida.sisifo.model.UsuarioModel;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Setter
public abstract class AbstractAuditoriaModel {

    @Cascade(CascadeType.ALL)
    @ManyToOne()
    private UsuarioModel creador;
    private LocalDateTime creada;

    @Cascade(CascadeType.ALL)
    @ManyToOne()
    private UsuarioModel eliminador;
    private LocalDateTime eliminada;

    @Cascade(CascadeType.ALL)
    @ManyToOne()
    private UsuarioModel modificador;
    private LocalDateTime modificada;
}