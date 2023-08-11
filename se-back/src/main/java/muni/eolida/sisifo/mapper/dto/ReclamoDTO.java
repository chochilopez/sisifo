package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReclamoDTO extends AbstractAuditoriaDTO implements Serializable {
    private String id;
    private String altura;
    private String barrio;
    private CalleDTO calleModel;
    private CalleDTO calleInterseccion;
    private String coordinadaX;
    private String coordinadaY;
    private String descripcion;
    private CalleDTO entreCalle1;
    private CalleDTO entreCalle2;
    private ArchivoDTO imagen;
    private String numero;
    private UsuarioDTO persona;
    private TipoReclamoDTO tipoReclamo;
}
