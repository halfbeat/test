package es.jcyl.cs.extractor.exception;

public class UndefinedVariable extends Exception {
	private static final long serialVersionUID = 1L;

	public UndefinedVariable() {
	}

	public UndefinedVariable(String message) {
		super(message);
	}

	public UndefinedVariable(Throwable cause) {
		super(cause);
	}

	public UndefinedVariable(String message, Throwable cause) {
		super(message, cause);
	}

}
