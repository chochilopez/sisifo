package muni.eolida.sisifo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EntityMessenger<E> {
    private E objeto;
    private List<E> listado;
    private String mensaje;
    private Integer estado;

}
