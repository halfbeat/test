package es.jcyl.cs.extractor.standarmappers;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.mapper.AbstractSource;
import es.jcyl.cs.extractor.mapper.OutputPort;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public class IntegerSource extends AbstractSource {
	int value = 0;
	int increment = 1;

	public IntegerSource(int initial, int incr) throws PortException, ControllerException {
		super();
		OutputPort a = new OutputPort(VariableType.VT_INT);
		addOutput(a);
		value = initial;
		increment = incr;
	}

	public boolean update() throws MappingException {
		OutputPort p = getOutput(0);
		VariableValue v = new VariableValue(VariableType.VT_INT);
		v.setIntValue(value);
		p.write(v);
		value += increment;
		return super.update();
	}

	public boolean execute() throws MappingException {
		return true;
	}
	
}
