package muni.eolida.sisifo.util.exception;

public class CustomUserAlreadyCreatedException extends RuntimeException {
    public CustomUserAlreadyCreatedException() {
        super();
    }

    public CustomUserAlreadyCreatedException(String message) {
        super(message);
    }
}