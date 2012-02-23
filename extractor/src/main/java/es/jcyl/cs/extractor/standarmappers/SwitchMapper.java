package es.jcyl.cs.extractor.standarmappers;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.mapper.AbstractMapper;
import es.jcyl.cs.extractor.mapper.InputPort;
import es.jcyl.cs.extractor.mapper.OutputPort;
import es.jcyl.cs.extractor.variable.VariableType;

public class SwitchMapper extends AbstractMapper {
	private InputPort selectedPort = null;
	private boolean updateAllInputs = false;

	public SwitchMapper(VariableType vtype, int count, int selected,
		boolean updateAllInputs) throws PortException, ControllerException {
		super();
		this.setUpdateAllInputs(updateAllInputs);
		for (int i = 0; i < count; i++) {
			InputPort port = new InputPort(vtype);
			if(!updateAllInputs) {
				port.setEnabled(false);
			}
			addInput(port);
		}
		switchInput(selected);
		OutputPort output = new OutputPort(vtype);
		addOutput(output);
	}

	public SwitchMapper(int count, int selected, boolean updateAllInputs)
		throws PortException, ControllerException {
		this(VariableType.VT_ANY, count, selected, updateAllInputs);
	}

	public SwitchMapper(VariableType vtype, int count, int selected)
		throws PortException, ControllerException {
		this(vtype, count, selected, false);
	}

	public SwitchMapper(int count, int selected) throws PortException,
		ControllerException {
		this(count, selected, false);
	}

	public boolean execute() throws MappingException {
		OutputPort oport = getOutput(0);
		oport.write(selectedPort.read());
		return true;
	}

	public final int getSwitchedInputNum() {
		return selectedPort == null ? -1 : inputs.getPortNum(selectedPort);
	}

	public final InputPort getSwitchedInput() {
		return selectedPort;
	}

	public final void switchInput(int selected) {
		if (selectedPort != null && !isUpdateAllInputs()) {
			selectedPort.setEnabled(false);
		}
		selectedPort = getInput(selected);
		if(!isUpdateAllInputs()) {
			selectedPort.setEnabled(true);
		}
	}

	protected boolean update() throws MappingException {
		 return isUpdateAllInputs() ? super.update() : selectedPort.hasDataAvailable()
			|| selectedPort.moveData();
	}

	public void setUpdateAllInputs(boolean updateAllInputs) {
		this.updateAllInputs = updateAllInputs;
	}

	public boolean isUpdateAllInputs() {
		return updateAllInputs;
	}
	
}
