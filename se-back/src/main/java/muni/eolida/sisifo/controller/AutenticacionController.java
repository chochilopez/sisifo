package muni.eolida.sisifo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.AutenticacionServiceImpl;
import muni.eolida.sisifo.util.exception.CustomTokenMismatchException;
import muni.eolida.sisifo.util.exception.CustomUserAlreadyCreatedException;
import muni.eolida.sisifo.util.exception.ErrorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/autenticacion")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Endpoints AUTENTICACION", description = "Recursos referidos al registro y acceso de usuarios.")
public class AutenticacionController extends BaseController {
	private final AutenticacionServiceImpl autenticacionService;
	private final UsuarioMapper usuarioMapper;
	@Value("${sisifo.app.mail.path}")
	private String path;

	@ExceptionHandler(CustomTokenMismatchException.class)
	public ResponseEntity<ErrorDTO> handleTokenMismatchException(Exception e) {
		HttpStatus status = HttpStatus.CONFLICT; // 409
		String mensaje = "Error al comparar los tokens. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@ExceptionHandler(CustomUserAlreadyCreatedException.class)
	public ResponseEntity<ErrorDTO> handleUserAlreadyCreatedException(Exception e) {
		HttpStatus status = HttpStatus.CONFLICT; // 409
		String mensaje = "El usuario ingresado ya existe. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@Operation(
			summary = "Ingreso del usuario con su nombre de usuario y contraseña.",
			description = "Rol/Autoridad requerida: NINGUNA<br><strong>De consumirse correctamente se emite el token y el token de refresco.</strong>"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Recurso consumido correctamente, se devuelve objeto JSON con los respectivos tokens de acceso.",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AutenticacionResponseDTO.class))},
					headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
			),
			@ApiResponse(
					responseCode = "400",
					description = "Los datos ingresados no poseen el formato correcto.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "404",
					description = "No se encontro el recurso buscado.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "409",
					description = "Error en la conversion de parametros ingresados.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "401",
					content = { @Content(mediaType = "", schema = @Schema())},
					description = "Debe autenticarse para acceder al recurso."
			),
			@ApiResponse(
					responseCode = "403",
					content = { @Content(mediaType = "", schema = @Schema())},
					description = "No se posee las autoridades necesarias para acceder al recurso."
			)
	})
	@Parameters({
			@Parameter(
					in = ParameterIn.QUERY,
					description = "Objeto JSON conteniendo el nombre de usuario y la contraseña del usuario.",
					example = "<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"username\": \"fulanito@gmail.com\",<br>&nbsp;&nbsp;&nbsp;&nbsp;\"password\": \"fulanito123\"<br>}"
			)
	})
	@PostMapping("/ingresar")
	public ResponseEntity<?> ingresar(@Valid @RequestBody AutenticacionRequestDTO autenticacionRequestDTO) {
		AutenticacionResponseDTO objeto = autenticacionService.ingresar(autenticacionRequestDTO);
		return new ResponseEntity<>(objeto, Helper.httpHeaders("Usuario ingresado correctamente."), HttpStatus.OK);
	}

	@Operation(
			summary = "Registro del usuario en la plataforma.",
			description = "Rol/Autoridad requerida: NINGUNA<br><strong>De consumirse correctamente se devuelve la direccion del email de confirmacion.</strong>"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Recurso consumido correctamente, se devuelve objeto JSON con el nuevo usuario creado.",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))},
					headers = {@Header(name = "mensaje", description = "URL de confirmacion de direccion de email de usuario.")}
			),
			@ApiResponse(
					responseCode = "400",
					description = "Los datos ingresados no poseen el formato correcto.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "404",
					description = "No se encontro el recurso buscado.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "409",
					description = "Error en la conversion de parametros ingresados.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "401",
					content = { @Content(mediaType = "", schema = @Schema())},
					description = "Debe autenticarse para acceder al recurso."
			),
			@ApiResponse(
					responseCode = "403",
					content = { @Content(mediaType = "", schema = @Schema())},
					description = "No se posee las autoridades necesarias para acceder al recurso."
			)
	})
	@Parameters({
			@Parameter(
					in = ParameterIn.QUERY,
					description = "Objeto JSON conteniendo el nombre de usuario y la contraseña del usuario, ademas de sus datos personales.",
					example = "" +
							"<br>{<br>&nbsp;&nbsp;&nbsp;&nbsp;\"nombre\": \"Fulanosky Fulanito\"," +
							"<br>&nbsp;&nbsp;&nbsp;&nbsp;\"dni\": \"12123123\"," +
							"<br>&nbsp;&nbsp;&nbsp;&nbsp;\"direccion\": \"Siempre Viva 1234\"," +
							"<br>&nbsp;&nbsp;&nbsp;&nbsp;\"telefono\": \"03034560303456\"," +
							"<br>&nbsp;&nbsp;&nbsp;&nbsp;\"username\": \"fulanito@gmail.com\"," +
							"<br>&nbsp;&nbsp;&nbsp;&nbsp;\"password\": \"fulanito123\"<br>}"
			)
	})
	@PostMapping("/registrarse")
	public ResponseEntity<?> registrarse(@Valid @RequestBody UsuarioCreation usuarioCreation) {
		UsuarioModel objeto = autenticacionService.registrar(usuarioCreation);
		return new ResponseEntity<>(usuarioMapper.toDto(objeto), Helper.httpHeaders(path + objeto.getId() + "/" + objeto.getToken()), HttpStatus.OK);
	}

	@Operation(
			summary = "Deslogueo, borrado de credenciales emitidas.",
			description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se borran los tokens emitidos para el usuario.</strong>"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Recurso consumido correctamente, tokens eliminados"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Los datos ingresados no poseen el formato correcto.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "404",
					description = "No se encontro el recurso buscado.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "409",
					description = "Error en la conversion de parametros ingresados.",
					content = { @Content(mediaType = "", schema = @Schema())},
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "401",
					content = { @Content(mediaType = "", schema = @Schema())},
					description = "Debe autenticarse para acceder al recurso."
			),
			@ApiResponse(
					responseCode = "403",
					content = { @Content(mediaType = "", schema = @Schema())},
					description = "No se posee las autoridades necesarias para acceder al recurso."
			)
	})
	@GetMapping(value="/salir")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		return autenticacionService.logout(request);
	}

	@Operation(hidden = true)
	@GetMapping(value="/confirmar-email/{id}/{token}")
	public ResponseEntity<?> confirmarEmail(@PathVariable(name = "id") Long id, @PathVariable(name = "token")  String token) {
		UsuarioModel objeto = autenticacionService.validarToken(id, token);
		return new ResponseEntity<>(usuarioMapper.toDto(objeto), Helper.httpHeaders("Todas las credenciales emitidas para el usuario han sido borradas"), HttpStatus.OK);
	}
}
