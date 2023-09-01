package muni.eolida.sisifo.controller;

import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.util.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST; // 400
		String mensaje = "No se cumplieron los requisitos del objeto. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST; // 400
		String clave = "";
		String valor = "";
		String mensaje = "";
		for (ObjectError error:e.getBindingResult().getAllErrors()) {
			clave = ((FieldError) error).getField();
			valor = error.getDefaultMessage();
			if (mensaje.isEmpty())
				mensaje = "Error en la validacion de los siguientes campos: " + clave + ": " + valor;
			else
				mensaje = mensaje + " --- " + clave + ": " + valor;
		}
		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@ExceptionHandler(CustomParameterConstraintException.class)
	public ResponseEntity<ErrorDTO> handleCustomParameterConstraintExceptions(Exception e) {
		HttpStatus status = HttpStatus.BAD_REQUEST; // 400
		String mensaje = "Los datos ingresados no poseen el formato correcto. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje),status);
	}

	@ExceptionHandler(CustomDataNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleCustomDataNotFoundExceptions(Exception e) {
		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		String mensaje = "No se encontro el recurso buscado. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@ExceptionHandler(CustomObjectNotDeletedException.class)
	public ResponseEntity<ErrorDTO> handleCustomObjectNotDeletedException(Exception e) {
		HttpStatus status = HttpStatus.CONFLICT; // 409
		String mensaje = "El objeto no se encuentra eliminado. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		HttpStatus status = HttpStatus.CONFLICT; // 409
		String mensaje = "Error en la conversion de parametros ingresados. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, mensaje), status);
	}

	@ExceptionHandler(CustomErrorException.class)
	public ResponseEntity<ErrorDTO> handleCustomErrorExceptions(Exception e) {
		// casting the generic Exception e to CustomErrorException
		CustomErrorException customErrorException = (CustomErrorException) e;

		HttpStatus status = customErrorException.getStatus();

		return new ResponseEntity<>(new ErrorDTO(status, customErrorException.getMessage()), status);
	}

	// fallback method
	@ExceptionHandler(Exception.class) // exception handled
	public ResponseEntity handleExceptions(Exception e ) {
		// ... potential custom logic

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
		String mensaje = "Ocurrio un error al intentar consumir el recurso. " + e.getMessage();

		return new ResponseEntity<>(new ErrorDTO(status, e.getMessage()), status);
	}
}
