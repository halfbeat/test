package es.jcyl.cs.extractor.variable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.UndefinedTypeException;

public class VariableOutputStream extends DataOutputStream {
	protected VariableGroupType vgroup;

	public VariableOutputStream(VariableGroupType vg, OutputStream out) {
		super(out);
		this.vgroup = vg;
		this.vgroup.first();
	}

	VariableType getCurrentType() throws IOException {
		return vgroup.actual();
	}

	public void writeNextInt(VariableValue value) throws IOException, MappingException {
		super.writeInt(value.asInteger());
	}

	public void writeNextUInt(VariableValue value) throws IOException, MappingException {
		super.writeInt(value.asInteger());
	}

	public void writeNextLong(VariableValue value) throws IOException, MappingException {
		super.writeLong(value.asLong());
	}

	public void writeNextULong(VariableValue value) throws IOException, MappingException {
		super.writeLong(value.asLong());
	}

	public void writeNextDouble(VariableValue value) throws IOException, MappingException {
		super.writeDouble(value.asDouble());
	}

	public void writeNextFloat(VariableValue value) throws IOException, MappingException {
		super.writeFloat(value.asFloat());
	}

	public void writeNextString(VariableValue value) throws IOException, MappingException {
		super.writeUTF(value.asString());
	}

	protected VariableGroupType getVGroup() {
		return vgroup;
	}

	protected void setVGroup(VariableGroupType vgroup) {
		this.vgroup = vgroup;
	}

	public void writeNextGroup(VariableGroupValue value) throws IOException, MappingException {
		for(int i = 0; i < value.size(); i++) {
			writeNext(value.getValue(i));
		}
	}

	public void writeNext(VariableValue value) throws IOException, MappingException {
		switch (value.getType().getType()) {
		case VariableType.DOUBLE:
			writeNextDouble(value);
			break;

		case VariableType.FLOAT:
			writeNextFloat(value);
			break;

		case VariableType.INT:
			writeNextInt(value);
			break;

		case VariableType.LONG:
			writeNextLong(value);
			break;

		case VariableType.STRING:
			writeNextString(value);
			break;

		case VariableType.GROUP:
			writeNextGroup((VariableGroupValue) value);
			break;

		default:
			throw new UndefinedTypeException();
		}
	}
}
