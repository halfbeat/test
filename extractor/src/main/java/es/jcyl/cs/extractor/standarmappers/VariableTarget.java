package es.jcyl.cs.extractor.standarmappers;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.mapper.AbstractSink;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public class VariableTarget extends AbstractSink {
	private VariableValue value;

	public VariableTarget(VariableType vtype) throws ControllerException {
		super();
		addInput(vtype);
	}
	
	public void setValue(VariableValue value) {
		this.value = value;
	}
	
	public VariableValue getValue() {
		return this.value;
	}

	public boolean execute() throws MappingException {
		value = getInput(0).read();
		return true;
	}
}
