package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCreation {
    private String id;
    private String name;
    private String telephone;
    private String sender;
    private String recepient;
    private String carbonCopy;
    private String subject;
    private String sended;
    private String body;
    private String error;
}
