package muni.eolida.sisifo.util.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomAlreadyExistantAreaException extends RuntimeException {
    private HttpStatus status = null;

    private Object data = null;

    public CustomAlreadyExistantAreaException() {
        super();
    }

    public CustomAlreadyExistantAreaException(String message) {
        super(message);
    }

    public CustomAlreadyExistantAreaException(HttpStatus status, String message) {
        this(message);
        this.status = status;
    }

    public CustomAlreadyExistantAreaException(HttpStatus status, String message, Object data) {
        this(status, message);
        this.data = data;
    }
}