package muni.eolida.sisifo.util.exception;

public class CustomTokenMismatchException extends RuntimeException {
    public CustomTokenMismatchException() {
        super();
    }

    public CustomTokenMismatchException(String message) {
        super(message);
    }
}