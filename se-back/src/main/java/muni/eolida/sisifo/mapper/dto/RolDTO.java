package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Hidden
@Setter
public class RolDTO implements Serializable {
    private String id;
    private String rol;
}
