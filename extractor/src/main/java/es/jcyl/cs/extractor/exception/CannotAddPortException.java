package es.jcyl.cs.extractor.exception;

public class CannotAddPortException extends PortException {
	private static final long serialVersionUID = 1L;

	public CannotAddPortException() {
	}

	public CannotAddPortException(String message) {
		super(message);
	}

	public CannotAddPortException(Throwable cause) {
		super(cause);
	}

	public CannotAddPortException(String message, Throwable cause) {
		super(message, cause);
	}

}
