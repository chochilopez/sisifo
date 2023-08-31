package muni.eolida.sisifo.controller;

import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomErrorException;
import muni.eolida.sisifo.util.exception.CustomParameterConstraintException;
import muni.eolida.sisifo.util.exception.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public abstract class BaseController {

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

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(Exception e) {
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
