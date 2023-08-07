package muni.eolida.sisifo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Entity
@RequiredArgsConstructor
@Setter
@Table(name = "email")
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "asunto")
    private String subject;
    @Column(name = "cc")
    private String carbonCopy;
    @Column(name = "emisor")
    private String sender;
    @Column(name = "enviado")
    private Boolean sended;
    @Column(columnDefinition = "TEXT")
    private String error;
    @Column(name = "nombre")
    private String name;
    @Column(name = "receptor")
    private String recepient;
    @Column(name = "telefono")
    private String telephone;
    @Column(columnDefinition = "TEXT", name = "texto")
    private String body;

    public EmailModel(String name, String telephone, String sender, String recepient, String subject, String body) {
        this.name = name;
        this.telephone = telephone;
        this.sender = sender;
        this.recepient = recepient;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailModel that = (EmailModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(telephone, that.telephone) && Objects.equals(sender, that.sender) && Objects.equals(recepient, that.recepient) && Objects.equals(carbonCopy, that.carbonCopy) && Objects.equals(subject, that.subject) && Objects.equals(sended, that.sended) && Objects.equals(body, that.body) && Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, telephone, sender, recepient, carbonCopy, subject, sended, body, error);
    }
}