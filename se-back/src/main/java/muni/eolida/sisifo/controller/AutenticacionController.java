package muni.eolida.sisifo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.helper.payload.response.JwtResponse;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.helper.payload.request.LoginRequest;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.AutenticacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/autenticacion")
@RestController
@Slf4j
public class AutenticacionController {
	@Autowired
	AutenticacionServiceImpl autenticacionService;
	@Autowired
	UsuarioMapper usuarioMapper;

	@PostMapping("/ingresar")
	public ResponseEntity<?> ingresar(@Valid @RequestBody LoginRequest loginRequest) {
		EntityMessenger<JwtResponse> jwtResponseEntityMessenger = autenticacionService.ingresarUsuario(loginRequest);
		if (jwtResponseEntityMessenger.getStatusCode() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(jwtResponseEntityMessenger.getMessage())).build();
		else if (jwtResponseEntityMessenger.getStatusCode() == 200)
			return new ResponseEntity<>(jwtResponseEntityMessenger.getObject(),Helper.httpHeaders(jwtResponseEntityMessenger.getMessage()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(jwtResponseEntityMessenger.getMessage())).build();
	}

	@PostMapping("/registrarse")
	public ResponseEntity<?> registrarse(@Valid @RequestBody UsuarioCreation usuarioCreation) {
		EntityMessenger<UsuarioModel> usuarioModelEntityMessenger = autenticacionService.registrarUsuario(usuarioCreation);
		if (usuarioModelEntityMessenger.getStatusCode() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(usuarioModelEntityMessenger.getMessage())).build();
		else if (usuarioModelEntityMessenger.getStatusCode() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(usuarioModelEntityMessenger.getObject()),Helper.httpHeaders(usuarioModelEntityMessenger.getMessage()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(usuarioModelEntityMessenger.getMessage())).build();
	}

	@GetMapping(value="/confirmar-email/{id}/{token}")
	public ResponseEntity<?> confirmarEmail(
			@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id,
			@PathVariable(name = "token") @javax.validation.constraints.Size(min = 1, max = 20) String token
	) {
		EntityMessenger<UsuarioModel> usuarioModel = autenticacionService.validarToken(id, token);
		if (usuarioModel.getStatusCode() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(usuarioModel.getMessage())).build();
		else if (usuarioModel.getStatusCode() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(usuarioModel.getObject()),Helper.httpHeaders(usuarioModel.getMessage()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(usuarioModel.getMessage())).build();
	}
}
