package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmailDataTransferObject implements Serializable {
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
