package es.jcyl.cs.extractor.exception;


public class ConnectorNotAttached extends MappingException {
	private static final long serialVersionUID = -4789766865913665417L;

	public ConnectorNotAttached() {
		super();
	}

	public ConnectorNotAttached(String message) {
		super(message);
	}

	public ConnectorNotAttached(Throwable cause) {
		super(cause);
	}

	public ConnectorNotAttached(String message, Throwable cause) {
		super(message, cause);
	}

}
