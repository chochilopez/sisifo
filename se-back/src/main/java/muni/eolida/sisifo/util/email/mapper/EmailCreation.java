package muni.eolida.sisifo.util.email.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailCreation {
    private String subject;
    private String sender;
    private String reciever;
    private String body;
}
