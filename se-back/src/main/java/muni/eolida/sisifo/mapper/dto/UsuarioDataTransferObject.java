package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UsuarioDataTransferObject implements Serializable {
    private String id;
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
}
