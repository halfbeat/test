package es.jcyl.cs.extractor.exception;

public class InvalidTypeException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public InvalidTypeException() {
		super();
	}

	public InvalidTypeException(String message) {
		super(message);
	}

	public InvalidTypeException(Throwable cause) {
		super(cause);
	}

	public InvalidTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
