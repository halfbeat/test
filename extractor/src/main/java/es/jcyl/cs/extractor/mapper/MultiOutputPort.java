package es.jcyl.cs.extractor.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.jcyl.cs.extractor.exception.ConnectorAlreadyAttached;
import es.jcyl.cs.extractor.exception.ConnectorNotAttached;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public class MultiOutputPort extends OutputPort {

	protected ArrayList link = new ArrayList();

	public MultiOutputPort(VariableType type) {
		super(type);
	}

	public MultiOutputPort(VariableType type, AbstractMapper owner) {
		super(type, owner);
	}

	public boolean isLinked(Port p) {
		return link != null && link.contains(p);
	}

	public void linkTo(Port c) throws ConnectorAlreadyAttached {
		if (isLinked(c)) {
			return;
		} else {
			internalLinkTo((InputPort) c);
			((InputPort) c).internalLinkTo(this);
		}
	}

	void internalLinkTo(InputPort p) {
		link.add(p);
	}

	public void unlink(Port c) throws ConnectorNotAttached {
		if (isLinked(c)) {
			link.remove(c);
			c.unlink(this);
		}
	}

	public boolean moveData() throws ConnectorNotAttached {
		if (hasDataAvailable) {
			if (link == null) {
				throw new ConnectorNotAttached();
			}
			Iterator it = link.iterator();
			while (it.hasNext()) {
				Port p = (Port) it.next();
				p.data = (VariableValue) data._clone();
				hasDataAvailable = false;
				p.hasDataAvailable = true;
			}
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(this.varType).append(" ");
		Iterator it = link.iterator();
		while (it.hasNext()) {
			sb.append(it.next()).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public AbstractMapper[] getRemoteMappers(boolean filterDuplicates) {
		if(link != null && !link.isEmpty()) {
			List result = new ArrayList();
			Iterator it = link.iterator();
			while(it.hasNext()) {
				InputPort port = (InputPort) it.next();
				if(port.getOwner() != null && (!filterDuplicates || !result.contains(port))) {
					result.add(port.getOwner());
				}
			}
			return (AbstractMapper[]) result.toArray(new AbstractMapper[result.size()]);
		} else {
			return null;
		}
	}

	public boolean isLinked() {
		return !link.isEmpty();
	}

}