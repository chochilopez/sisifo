package muni.eolida.sisifo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.AutenticacionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/autenticacion")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth management APIs")
public class AutenticacionController {
	private final AutenticacionServiceImpl autenticacionService;
	private final UsuarioMapper usuarioMapper;

	@Operation(
			summary = "Testing endpoint",
			description = "Testing endpoint for secured API calls",
			tags = { "demo", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/ingresar")
	public ResponseEntity<?> ingresar(@Valid @RequestBody AutenticacionRequestDTO autenticacionRequestDTO) {
		EntityMessenger<AutenticacionResponseDTO> objeto = autenticacionService.ingresar(autenticacionRequestDTO);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 200)
			return new ResponseEntity<>(objeto.getObjeto(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}

	@PostMapping("/registrarse")
	public ResponseEntity<?> registrarse(@Valid @RequestBody UsuarioCreation usuarioCreation) {
		EntityMessenger<UsuarioModel> objeto = autenticacionService.registrar(usuarioCreation);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}

	@GetMapping(value="/confirmar-email/{id}/{token}")
	public ResponseEntity<?> confirmarEmail(
			@PathVariable(name = "id") Long id,
			@PathVariable(name = "token")  String token
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
