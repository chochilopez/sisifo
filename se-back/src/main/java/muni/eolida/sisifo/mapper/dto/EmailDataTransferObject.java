package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmailDataTransferObject implements Serializable {
    private String id;
    private String nombre;
    private String telefono;
    private String emisor;
    private String receptor;
    private String cc;
    private String asunto;
    private String enviado;
    private String texto;
    private String error;
}
