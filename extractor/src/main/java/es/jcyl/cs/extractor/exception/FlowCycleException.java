package es.jcyl.cs.extractor.exception;

public class FlowCycleException extends ControllerException {
	private static final long serialVersionUID = 1L;

	public FlowCycleException() {
	}

	public FlowCycleException(String message) {
		super(message);
	}

	public FlowCycleException(Throwable cause) {
		super(cause);
	}

	public FlowCycleException(String message, Throwable cause) {
		super(message, cause);
	}

}
