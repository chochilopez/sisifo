package muni.eolida.sisifo.security.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
  private String username;
  private String password;
  private String name;
  private String idNumber;
  private String address;
  private String telephone;
}
