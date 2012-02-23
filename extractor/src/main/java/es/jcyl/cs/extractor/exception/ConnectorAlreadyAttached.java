package es.jcyl.cs.extractor.exception;

public class ConnectorAlreadyAttached extends Exception {
	private static final long serialVersionUID = -1157035119328819645L;

	public ConnectorAlreadyAttached() {
		super();
	}

	public ConnectorAlreadyAttached(String message) {
		super(message);
	}

	public ConnectorAlreadyAttached(Throwable cause) {
		super(cause);
	}

	public ConnectorAlreadyAttached(String message, Throwable cause) {
		super(message, cause);
	}
}
