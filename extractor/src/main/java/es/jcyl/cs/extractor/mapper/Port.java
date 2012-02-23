package es.jcyl.cs.extractor.mapper;

import es.jcyl.cs.extractor.exception.ConnectorAlreadyAttached;
import es.jcyl.cs.extractor.exception.ConnectorNotAttached;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public abstract class Port {
	public final static int PORT_OUT = 1;
	public final static int PORT_IN = 2;
	
	public final static int PORT_STATUS_UNMAPPED = 0;
	public final static int PORT_STATUS_UNLINKED = 1;
	public final static int PORT_STATUS_READY = 2;
	public final static int PORT_STATUS_ERROR = 4;

	protected VariableType varType;
	protected int portType;
	protected AbstractMapper owner = null;
	protected boolean hasDataAvailable = false;
	protected VariableValue data;
	protected boolean enabled = true;

	public Port(int portType, VariableType type, AbstractMapper owner) {
		setportType(portType);
		setVarType(type);
		setOwner(owner);
		data = new VariableValue(type);
	}

	public Port(int portType, VariableType type) {
		setportType(portType);
		setVarType(type);
		setOwner(null);
		data = new VariableValue(type);
	}

	public final VariableType getVarType() {
		return varType;
	}

	public final void setVarType(VariableType type) {
		this.varType = type;
	}

	public final int getportType() {
		return portType;
	}

	public final void setportType(int portType) {
		this.portType = portType;
	}

	public abstract boolean isLinked(Port p);

	public abstract void linkTo(Port c) throws ConnectorAlreadyAttached;

	public void unlink(Port port) throws ConnectorNotAttached {
		if (isLinked(port)) {
			
		} else {
			throw new ConnectorNotAttached();
		}
	}
	
	public abstract void unlink() throws ConnectorNotAttached;

	public abstract boolean moveData() throws ConnectorNotAttached;

	public final AbstractMapper getOwner() {
		return owner;
	}

	public final void setOwner(AbstractMapper owner) {
		this.owner = owner;
	}

	public final boolean hasDataAvailable() {
		return hasDataAvailable && enabled;
	}

	public void write(VariableValue data) {
		// TODO: Revisar esto ...
		this.data = (VariableValue) data._clone();
		hasDataAvailable = true;
	}

	public VariableValue read() {
		VariableValue value = null;
		if (hasDataAvailable()) {
			value = this.data;
			hasDataAvailable = false;
		}
		return value;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public abstract AbstractMapper[] getRemoteMappers(boolean filterDuplicates);

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public abstract boolean isLinked();

	public abstract Port getRemotePort();
}
