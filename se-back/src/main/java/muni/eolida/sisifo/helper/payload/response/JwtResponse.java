package muni.eolida.sisifo.helper.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
  private String token;
  private String username;
  private List<String> roles;
}
