package es.jcyl.cs.extractor.exception;

public class PortNameInUseException extends CannotAddPortException {
	private static final long serialVersionUID = 1L;

	public PortNameInUseException() {
	}

	public PortNameInUseException(String message) {
		super(message);
	}

	public PortNameInUseException(Throwable cause) {
		super(cause);
	}

	public PortNameInUseException(String message, Throwable cause) {
		super(message, cause);
	}

}
