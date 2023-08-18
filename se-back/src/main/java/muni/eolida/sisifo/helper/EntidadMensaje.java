package muni.eolida.sisifo.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EntidadMensaje<E> {
    private E objeto;
    private List<E> listado;
    private String mensaje;
    private Integer estado;

}
