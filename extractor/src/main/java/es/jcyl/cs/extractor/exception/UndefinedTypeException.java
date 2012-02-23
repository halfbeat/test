package es.jcyl.cs.extractor.exception;

import java.io.IOException;

public class UndefinedTypeException extends IOException {
	private static final long serialVersionUID = 1L;

	public UndefinedTypeException() {
		super();
	}

	public UndefinedTypeException(String message) {
		super(message);
	}

	public UndefinedTypeException(Throwable cause) {
		super(cause.getMessage());
	}

	public UndefinedTypeException(String message, Throwable cause) {
		super(message + " : " + cause.getMessage());
	}

}
