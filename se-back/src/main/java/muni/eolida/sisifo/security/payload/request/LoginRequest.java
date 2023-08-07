package muni.eolida.sisifo.security.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  	private String username;
	private String password;
}
