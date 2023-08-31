package muni.eolida.sisifo.util.exception.errors;

import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomErrorException;
import muni.eolida.sisifo.util.exception.CustomParameterConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(CustomDataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        String mensaje = "No se encontro el recurso buscado. " + e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(status, mensaje), status);
    }

    @ExceptionHandler(CustomParameterConstraintException.class)
    public ResponseEntity<ErrorResponse> handleCustomParameterConstraintExceptions(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        String mensaje = "Los datos ingresados no poseen el formato correcto. " + e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(status, mensaje),status);
    }

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorExceptions(Exception e) {
        // casting the generic Exception e to CustomErrorException
        CustomErrorException customErrorException = (CustomErrorException) e;

        HttpStatus status = customErrorException.getStatus();

        return new ResponseEntity<>(new ErrorResponse(status, customErrorException.getMessage()), status);
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity handleExceptions(Exception e ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        String mensaje = "Ocurrio un error al intentar consumir el recurso. " + e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
}
