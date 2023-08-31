package muni.eolida.sisifo.controller;

import io.swagger.v3.core.util.Json;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.AutenticacionServiceImpl;
import muni.eolida.sisifo.util.exception.CustomTokenMismatchException;
import muni.eolida.sisifo.util.exception.CustomUserAlreadyCreatedException;
import muni.eolida.sisifo.util.exception.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/autenticacion")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Endpoints AUTENTICACION", description = "Recursos referidos al registro y acceso de usuarios.")
public class AutenticacionController extends BaseController {
	private final AutenticacionServiceImpl autenticacionService;
	private final UsuarioMapper usuarioMapper;

	@ExceptionHandler(CustomTokenMismatchException.class)
	public ResponseEntity<ErrorDTO> handleTokenMismatchException(Exception e) {
		HttpStatus status = HttpStatus.CONFLICT; // 409
		String mensaje = "Error en la comparar los tokens. " + e.getMessage();

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
					responseCode = "202",
					description = "Recurso consumido correctamente, sin embargo no se puedo devolver el usuario.",
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "204",
					description = "Ocurrio un error al consumir el recurso.",
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "401",
					description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
		EntityMessenger<AutenticacionResponseDTO> objeto = autenticacionService.ingresar(autenticacionRequestDTO);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 200)
			return new ResponseEntity<>(objeto.getObjeto(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}

	@Operation(
			summary = "Registro del usuario en la plataforma.",
			description = "Rol/Autoridad requerida: NINGUNA<br><strong>De consumirse correctamente se envia automiticamente el email de confirmacion.</strong>"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Recurso consumido correctamente, se devuelve objeto JSON con el nuevo usuario creado.",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))},
					headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
			),
			@ApiResponse(
					responseCode = "202",
					description = "Recurso consumido correctamente, sin embargo no se puedo crear el usuario.",
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "204",
					description = "Ocurrio un error al consumir el recurso.",
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "401",
					description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
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
		EntityMessenger<UsuarioModel> objeto = autenticacionService.registrar(usuarioCreation);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}

	@Operation(
			summary = "Solicitud de credenciales expiradas a traves del token de refresco.",
			description = "Rol/Autoridad requerida: CONTRIBUYENTE<br><strong>De consumirse correctamente se emiten nuevamente el token y el token de refresco, con fechas de expiracion actualizadas.</strong>"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Recurso consumido correctamente, se devuelve objeto JSON con los nuevos tokens.",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AutenticacionResponseDTO.class))},
					headers = {@Header(name = "mensaje", description = "Estado de la consulta devuelta por el servidor.")}
			),
			@ApiResponse(
					responseCode = "202",
					description = "Recurso consumido correctamente, sin embargo no se puedieron emitir los tokens.",
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "204",
					description = "Ocurrio un error al consumir el recurso.",
					headers = {@Header(name = "mensaje", description = "Mensaje con informacion extra sobre el error.")}
			),
			@ApiResponse(
					responseCode = "401",
					description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
			)
	})
	@PostMapping(value="/token-refresco")
	public ResponseEntity<?> tokenRefresco(HttpServletRequest request, HttpServletResponse response) {
		EntityMessenger<AutenticacionResponseDTO> objeto = autenticacionService.refreshToken(request, response);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 200)
			return new ResponseEntity<>(objeto.getObjeto(), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
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
					responseCode = "202",
					description = "Recurso consumido correctamente, sin embargo no se puedo eliminar los tokens."
			),
			@ApiResponse(
					responseCode = "204",
					description = "Ocurrio un error al consumir el recurso."
			),
			@ApiResponse(
					responseCode = "401",
					description = "No se posee (o expiraron) autoridades necesarias para acceder al recurso o el token esta mal formado."
			)
	})
	@GetMapping(value="/salir")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		return autenticacionService.logout(request);
	}

	@Operation(hidden = true)
	@GetMapping(value="/confirmar-email/{id}/{token}")
	public ResponseEntity<?> confirmarEmail(@PathVariable(name = "id") Long id, @PathVariable(name = "token")  String token) {
		EntityMessenger<UsuarioModel> objeto = autenticacionService.validarToken(id, token);
		if (objeto.getEstado() == 202)
			return ResponseEntity.accepted().headers(Helper.httpHeaders(objeto.getMensaje())).build();
		else if (objeto.getEstado() == 201)
			return new ResponseEntity<>(usuarioMapper.toDto(objeto.getObjeto()), Helper.httpHeaders(objeto.getMensaje()), HttpStatus.OK);
		else
			return ResponseEntity.noContent().headers(Helper.httpHeaders(objeto.getMensaje())).build();
	}
}
