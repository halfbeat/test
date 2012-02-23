package es.jcyl.cs.extractor.standarmappers;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.mapper.AbstractSource;
import es.jcyl.cs.extractor.variable.VariableValue;

public class VariableSource extends AbstractSource {
	private VariableValue value;

	public VariableSource(VariableValue val) throws ControllerException {
		super();
		this.value = val;
		addOutput(value.getType());
	}
	
	public void setValue(VariableValue value) {
		this.value = value;
	}
	
	public VariableValue getValue() {
		return this.value;
	}

	protected boolean update() throws MappingException {
		getOutput(0).write(value);
		return super.update();
	}
}
