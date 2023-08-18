package muni.eolida.sisifo.helper.email;

import lombok.*;

import jakarta.persistence.*;
import muni.eolida.sisifo.helper.AbstractAuditoriaModel;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "email")
@EqualsAndHashCode
public class EmailModel extends AbstractAuditoriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String cc;
    private String sender;
    private String reciever;
    @Column(columnDefinition = "TEXT")
    private String body;
    private LocalDateTime sended;

    public EmailModel(String subject, String carbonCopy, String sender, String reciever, String body) {
        this.subject = subject;
        this.cc = carbonCopy;
        this.sender = sender;
        this.reciever = reciever;
        this.body = body;
    }
}