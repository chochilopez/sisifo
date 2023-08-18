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
public class EntityMessenger<E> {
    private E object;
    private List<E> list;
    private String mensaje;
    private Integer statusCode;

}
