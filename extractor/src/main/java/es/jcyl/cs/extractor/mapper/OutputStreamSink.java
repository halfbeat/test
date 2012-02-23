package es.jcyl.cs.extractor.mapper;

import java.io.IOException;
import java.io.OutputStream;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.variable.VariableGroupType;
import es.jcyl.cs.extractor.variable.VariableOutputStream;
import es.jcyl.cs.extractor.variable.VariableValue;

public class OutputStreamSink extends AbstractSink {
	private VariableOutputStream ostream;

	public OutputStreamSink(VariableGroupType vgtype, OutputStream target) throws PortException, ControllerException {
		super();
		ostream = new VariableOutputStream(vgtype, target);
		addInput("0", vgtype);
	}

	public boolean execute() throws MappingException {
		VariableValue data = getInput("0").read();
		if (data != null) {
			try {
				ostream.writeNext(data);
				return true;
			} catch (IOException ex) {
				throw new MappingException("Error al escribir", ex);
			}
		} else {
			return false;
		}
	}

}
