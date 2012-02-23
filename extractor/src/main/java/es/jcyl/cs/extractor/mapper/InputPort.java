package es.jcyl.cs.extractor.mapper;

import es.jcyl.cs.extractor.exception.ConnectorAlreadyAttached;
import es.jcyl.cs.extractor.exception.ConnectorNotAttached;
import es.jcyl.cs.extractor.variable.VariableType;

public class InputPort extends Port {

	protected transient OutputPort link = null;

	public InputPort(VariableType type) {
		super(PORT_OUT, type);
	}

	public InputPort(VariableType type, AbstractMapper owner) {
		super(PORT_OUT, type, owner);
	}

	public boolean isLinked(Port port) {
		return link == port && port.isLinked(this);
	}

	public void linkTo(Port port) throws ConnectorAlreadyAttached {
		if(isLinked()) {
			if(!isLinked(port)) {
				throw new ConnectorAlreadyAttached();
			} 
		} else {
			internalLinkTo((OutputPort) port);
			link.internalLinkTo(this);
		}
	}

	public void internalLinkTo(OutputPort port) {
		link = port;
	}

	public boolean moveData() throws ConnectorNotAttached {
		if (hasDataAvailable) {
			return true;
		} else {
			if (link == null) {
				throw new ConnectorNotAttached();
			}
			return link.moveData();
		}
	}

	public String toString() {
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("[").append(this.varType).append(" ");
		// if(link != null && link.owner != null) {
		// sb.append(link.owner.getName());
		// }
		sbuffer.append("]");
		return sbuffer.toString();
	}

	public OutputPort getLinkedPort() {
		return link;
	}

	public AbstractMapper[] getRemoteMappers(boolean filterDuplicates) {
		if(link != null && link.getOwner() != null) {
			return new AbstractMapper[] {
					link.getOwner()
			};
		} else {
			return null;
		}
	}

	public boolean isLinked() {
		return link != null;
	}

	public void unlink() throws ConnectorNotAttached {
		if (isLinked()) {
			link.internalLinkTo(null);
			internalLinkTo(null);
		} else {
			throw new ConnectorNotAttached();
		}
	}

	public Port getRemotePort() {
		return link != null ? link : null;
	}
}
