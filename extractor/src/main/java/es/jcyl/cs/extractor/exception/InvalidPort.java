package es.jcyl.cs.extractor.exception;

public class InvalidPort extends PortException {
	private static final long serialVersionUID = -1369769140956882365L;

	public InvalidPort() {
		super();
	}

	public InvalidPort(String message) {
		super(message);
	}

	public InvalidPort(Throwable cause) {
		super(cause);
	}

	public InvalidPort(String message, Throwable cause) {
		super(message, cause);
	}
}
