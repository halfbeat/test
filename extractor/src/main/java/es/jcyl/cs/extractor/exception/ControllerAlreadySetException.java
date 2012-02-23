package es.jcyl.cs.extractor.exception;

public class ControllerAlreadySetException extends ControllerException {
	private static final long serialVersionUID = 1L;

	public ControllerAlreadySetException() {
	}

	public ControllerAlreadySetException(String message) {
		super(message);
	}

	public ControllerAlreadySetException(Throwable cause) {
		super(cause);
	}

	public ControllerAlreadySetException(String message, Throwable cause) {
		super(message, cause);
	}

}
