package muni.eolida.sisifo.util.email.mapper;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class EmailDTO implements Serializable {
    private String subject;
    private String cc;
    private String sender;
    private String reciever;
    private String body;
    private String sended;
}
