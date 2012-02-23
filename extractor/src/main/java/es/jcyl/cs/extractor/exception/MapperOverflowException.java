package es.jcyl.cs.extractor.exception;

public class MapperOverflowException extends MappingException {

	private static final long serialVersionUID = 1L;

	public MapperOverflowException() {
		super();
	}

	public MapperOverflowException(String message) {
		super(message);
	}

	public MapperOverflowException(Throwable cause) {
		super(cause);
	}

	public MapperOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

}
