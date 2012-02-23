package es.jcyl.cs.extractor.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.exception.PortNameInUseException;
import es.jcyl.cs.extractor.exception.UnalignedVariableException;
import es.jcyl.cs.extractor.variable.VariableGroupType;
import es.jcyl.cs.extractor.variable.VariableResultSetInputStream;
import es.jcyl.cs.extractor.variable.VariableValue;

public class ResultSetInputStreamSource extends AbstractSource {
	private VariableResultSetInputStream istream;

	public ResultSetInputStreamSource(VariableGroupType vgtype) throws PortException, ControllerException {
		super();
		istream = null;
		addOutput("0", vgtype);
	}

	public ResultSetInputStreamSource(VariableGroupType vgtype, ResultSet source) throws PortException, SQLException, UnalignedVariableException, PortNameInUseException, ControllerException {
		this(vgtype);
		
		istream = new VariableResultSetInputStream(vgtype, source);
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
		return istream.available();
	}

	public void reset() throws MappingException {
		try {
			istream.reset();
		} catch (SQLException ex) {
			throw new MappingException(ex);
		}
	}

	public void dispose() {
		if (istream != null) {
			istream.dispose();
		}
	}

	public void setResultSet(ResultSet rs) throws UnalignedVariableException, SQLException {
		this.istream = new VariableResultSetInputStream((VariableGroupType) getOutput("0").getVarType(), rs);
	}

	protected boolean update() throws MappingException {
		return available();
	}
}
