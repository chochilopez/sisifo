package muni.eolida.sisifo.util.exception;

public class CustomObjectNotDeletedException extends RuntimeException {
	public CustomObjectNotDeletedException() {
		super();
	}

	public CustomObjectNotDeletedException(String message) {
		super(message);
	}
}
