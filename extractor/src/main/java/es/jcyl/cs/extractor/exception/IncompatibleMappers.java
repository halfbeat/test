package es.jcyl.cs.extractor.exception;

public class IncompatibleMappers extends Exception {
	private static final long serialVersionUID = 5384110233570452303L;

	public IncompatibleMappers() {
		super();
	}

	public IncompatibleMappers(String message) {
		super(message);
	}

	public IncompatibleMappers(Throwable cause) {
		super(cause);
	}

	public IncompatibleMappers(String message, Throwable cause) {
		super(message, cause);
	}
}
