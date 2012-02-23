package es.jcyl.cs.extractor.standarmappers;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.mapper.AbstractMapper;
import es.jcyl.cs.extractor.mapper.InputPort;
import es.jcyl.cs.extractor.mapper.OutputPort;
import es.jcyl.cs.extractor.variable.DefaultVariableGroupType;
import es.jcyl.cs.extractor.variable.VariableGroupValue;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

public class MuxMapper extends AbstractMapper {
	public MuxMapper(int numInputs) throws PortException, ControllerException {
		super();
		for(int i = 0; i < numInputs; i++) {
			addInput(VariableType.VT_ANY);
		}
		OutputPort oport = new OutputPort(VariableType.VT_ANY);
		addOutput(oport);
	}

	public boolean execute() throws MappingException {
		InputPort[] input = (InputPort[]) getAllInputs();
		VariableValue[] values = new VariableValue[input.length];
		for(int i = 0; i < input.length; i++) {
			values[i] = input[i].read();
		}
		VariableGroupValue vgv = new VariableGroupValue(DefaultVariableGroupType.type, values);
		getOutput(0).write(vgv);
		return true;
	}

}
