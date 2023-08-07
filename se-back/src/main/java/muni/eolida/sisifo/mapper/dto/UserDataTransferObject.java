package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDataTransferObject implements Serializable {
    private String id;
    private String name;
    private String idNumber;
    private String address;
    private String telephone;
    private String username;
    private String password;
    private Set<String> roles = new HashSet<>();
}
