package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReclamoDTO implements Serializable {
    private String id;
    private String altura;
    private String barrio;
    private CalleDTO calle;
    private CalleDTO interseccion;
    private String coordinadaX;
    private String coordinadaY;
    private String descripcion;
    private CalleDTO entreCalle1;
    private CalleDTO entreCalle2;
    private ArchivoDTO imagen;
    private UsuarioDTO persona;
    private TipoReclamoDTO tipoReclamo;
}
