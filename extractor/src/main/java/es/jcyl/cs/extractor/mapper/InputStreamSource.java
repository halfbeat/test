package es.jcyl.cs.extractor.mapper;

import java.io.IOException;
import java.io.InputStream;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.variable.VariableGroupType;
import es.jcyl.cs.extractor.variable.VariableGroupValue;
import es.jcyl.cs.extractor.variable.VariableInputStream;
import es.jcyl.cs.extractor.variable.VariableValue;

public class InputStreamSource extends AbstractSource {
	private VariableInputStream istream;
	VariableGroupValue data;

	public InputStreamSource(VariableGroupType vgtype, InputStream source) throws PortException, ControllerException {
		super();
		istream = new VariableInputStream(vgtype, source);
		addOutput("0", vgtype);
	}
	
	public boolean execute() throws MappingException {
		try {
			VariableValue value = istream.readNextGroup();
			getOutput("0").write(value);
		} catch (Exception ex) {
			throw new MappingException(ex.getMessage(), ex.getCause());
		}
		return true;
	}

	public boolean available() throws MappingException {
		try {
			return istream.available() > 0;
		} catch (IOException ex) {
			throw new MappingException(ex);
		}
	}
	
	protected boolean update() throws MappingException {
		return available();
	}

}
