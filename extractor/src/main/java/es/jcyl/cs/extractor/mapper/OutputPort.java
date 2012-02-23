package es.jcyl.cs.extractor.mapper;

import es.jcyl.cs.extractor.exception.ConnectorAlreadyAttached;
import es.jcyl.cs.extractor.exception.ConnectorNotAttached;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public class OutputPort extends Port {

	protected transient InputPort link = null;

	public OutputPort(VariableType type) {
		super(PORT_OUT, type);
	}

	public OutputPort(VariableType type, AbstractMapper owner) {
		super(PORT_OUT, type, owner);
	}

	public boolean isLinked(Port p) {
		return link != null && link == p;
	}

	public void linkTo(Port port) throws ConnectorAlreadyAttached {
		/*
		 * if (isLinked(c)) { return; } else { if() internalLinkTo((InputPort)
		 * c); ((InputPort) c).internalLinkTo(this); }
		 */
		if (isLinked()) {
			if (!isLinked(port)) {
				throw new ConnectorAlreadyAttached();
			}
		} else {
			internalLinkTo((InputPort) port);
			link.internalLinkTo(this);
		}
	}

	void internalLinkTo(InputPort port) {
		link = port;
	}

	public boolean moveData() throws ConnectorNotAttached {
		if (hasDataAvailable) {
			if (link == null) {
				throw new ConnectorNotAttached();
			}
			Port p = link;
			p.data = (VariableValue) data._clone();
			hasDataAvailable = false;
			p.hasDataAvailable = true;
			return true;
		} else {
			return false;
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

	public AbstractMapper[] getRemoteMappers(boolean filterDuplicates) {
		if (link != null && link.getOwner() != null) {
			return new AbstractMapper[] { link.getOwner() };
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
