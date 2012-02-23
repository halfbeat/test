package es.jcyl.cs.extractor.standarmappers;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.mapper.AbstractMapper;
import es.jcyl.cs.extractor.mapper.InputPort;
import es.jcyl.cs.extractor.mapper.OutputPort;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public class OneToMany extends AbstractMapper {

	public OneToMany(int numOutputs) throws ControllerException, PortException {
		super();
		for(int i = 0; i < numOutputs; i++) {
			addOutput(VariableType.VT_ANY);
		}
		InputPort iport = new InputPort(VariableType.VT_ANY);
		addInput(iport);
	}

	public boolean execute() throws MappingException {
		InputPort input = getInput(0);
		OutputPort[] output = getAllOutputs();
		VariableValue vv = input.read();
		for(int i = 0; i < output.length; i++) {
			output[i].write(vv);
		}
		return true;
	}

}
