package muni.eolida.sisifo.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
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
    private LocalDateTime creado;

    @Cascade(CascadeType.ALL)
    @ManyToOne()
    private UsuarioModel borrador;
    private LocalDateTime borrado;

    @Cascade(CascadeType.ALL)
    @ManyToOne()
    private UsuarioModel modificador;
    private LocalDateTime modificacion;
}