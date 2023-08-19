package muni.eolida.sisifo.controller;

import jakarta.validation.Valid;
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
	private AutenticacionServiceImpl autenticacionService;
	@Autowired
	private UsuarioMapper usuarioMapper;

	@PostMapping("/ingresar")
	public ResponseEntity<?> ingresar(@Valid @RequestBody LoginRequest loginRequest) {
		EntityMessenger<JwtResponse> objeto = autenticacionService.ingresarUsuario(loginRequest);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 200)
			return new ResponseEntity<>(objeto.getObjeto(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}

	@PostMapping("/registrarse")
	public ResponseEntity<?> registrarse(@Valid @RequestBody UsuarioCreation usuarioCreation) {
		EntityMessenger<UsuarioModel> objeto = autenticacionService.registrarUsuario(usuarioCreation);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}

	@GetMapping(value="/confirmar-email/{id}/{token}")
	public ResponseEntity<?> confirmarEmail(
			@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id,
			@PathVariable(name = "token") @javax.validation.constraints.Size(min = 1, max = 20) String token
	) {
		EntityMessenger<UsuarioModel> objeto = autenticacionService.validarToken(id, token);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}
}
