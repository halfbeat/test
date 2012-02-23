package es.jcyl.cs.extractor.exception;

import java.io.IOException;

public class UnalignedVariableException extends IOException {
	private static final long serialVersionUID = 1L;

	public UnalignedVariableException() {
		super();
	}

	public UnalignedVariableException(String message) {
		super(message);
	}

	public UnalignedVariableException(Throwable cause) {
		super(cause.getMessage());
	}

	public UnalignedVariableException(String message, Throwable cause) {
		super(message + " : " + cause.getMessage());
	}

}
