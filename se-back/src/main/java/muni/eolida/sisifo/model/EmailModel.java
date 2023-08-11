package muni.eolida.sisifo.model;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
@Setter
@Table(name = "email")
@EqualsAndHashCode
public class EmailModel extends AbstractAuditoriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String asunto;
    private String cc;
    private String emisor;
    private Boolean enviado;
    @Column(columnDefinition = "TEXT")
    private String error;
    private String nombre;
    private String receptor;
    private String telefono;
    @Column(name = "texto")
    private String texto;

    public EmailModel(String nombre, String telefono, String emisor, String receptor, String asunto, String texto) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.emisor = emisor;
        this.receptor = receptor;
        this.asunto = asunto;
        this.texto = texto;
    }
}