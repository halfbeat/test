package es.jcyl.cs.extractor.exception;

public class ConversionNotAllowed extends InvalidTypeException {
	private static final long serialVersionUID = 1L;

	public ConversionNotAllowed() {
	}

	public ConversionNotAllowed(String message) {
		super(message);
	}

	public ConversionNotAllowed(Throwable cause) {
		super(cause);
	}

	public ConversionNotAllowed(String message, Throwable cause) {
		super(message, cause);
	}

}
