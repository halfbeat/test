package es.jcyl.cs.extractor.variable;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import es.jcyl.cs.extractor.exception.UnalignedVariableException;
import es.jcyl.cs.extractor.exception.UndefinedTypeException;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedDouble;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedFloat;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedInteger;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedLong;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedString;

public class VariableInputStream extends DataInputStream {
	protected VariableGroupType vgroup;

	public VariableInputStream(VariableGroupType vg, InputStream in) {
		super(in);
		this.vgroup = vg;
		this.vgroup.first();
	}

	VariableType getCurrentType() throws IOException {
		return vgroup.actual();
	}

	public int readNextInt(VariableGroupType vg) throws IOException {
		if (!vg.actual().equals(VariableType.VT_INT)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		int r = super.readInt();
		vg.next();
		return r;
	}

	public int readNextUInt(VariableGroupType vg) throws IOException {
		if (!vg.actual().equals(VariableType.VT_INT)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		int r = super.readInt();
		vg.next();
		return r;
	}

	public long readNextLong(VariableGroupType vg) throws IOException {
		if (!vg.actual().equals(VariableType.VT_LONG)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		long r = super.readLong();
		vg.next();
		return r;
	}

	public long readNextULong(VariableGroupType vg) throws IOException {
		return readNextLong(vg);
	}

	public double readNextDouble(VariableGroupType vg) throws IOException {
		if (!vg.actual().equals(VariableType.VT_DOUBLE)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		double r = super.readDouble();
		vg.next();
		return r;
	}

	public float readNextFloat(VariableGroupType vg) throws IOException {
		if (!vg.actual().equals(VariableType.VT_FLOAT)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		float r = super.readFloat();
		vg.next();
		return r;
	}

	public String readNextString(VariableGroupType vg) throws IOException {
		if (!vg.actual().equals(VariableType.VT_STRING)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		String r = super.readUTF();
		vg.next();
		return r;
	}

	protected VariableGroupType getVGroup() {
		return vgroup;
	}

	protected void setVGroup(VariableGroupType vgroup) {
		this.vgroup = vgroup;
	}

	public VariableValue[] readNextGroupAsArray(VariableGroupType vg) throws IOException {
		vg.first();
		if (vg.rotated() == false) {
			throw new UnalignedVariableException();
		}
		VariableValue[] res = new VariableValue[vg.size()];
		for (int i = 0; i < vg.size(); i++) {
			res[i] = readNext(vg);
		}
		if (vg.rotated() == false) {
			throw new UnalignedVariableException();
		}
		return res;
	}

	public VariableGroupValue readNextGroup() throws IOException {
		return new VariableGroupValue(DefaultVariableGroupType.type, readNextGroupAsArray(vgroup));
	}

	private VariableGroupValue readNextGroup(VariableGroupType vg) throws IOException {
		return new VariableGroupValue(DefaultVariableGroupType.type, readNextGroupAsArray(vg));
	}

	public VariableValue readNext(VariableGroupType vg) throws IOException {
		VariableValue value = null;
		switch (vg.actual().getType()) {
		case VariableType.DOUBLE:
			value = new VariableValue(VariableType.VT_DOUBLE, new BoxedDouble(readNextDouble(vg)));
			break;

		case VariableType.FLOAT:
			value = new VariableValue(VariableType.VT_FLOAT, new BoxedFloat(readNextFloat(vg)));
			break;

		case VariableType.INT:
			value = new VariableValue(VariableType.VT_INT, new BoxedInteger(readNextInt(vg)));
			break;

		case VariableType.LONG:
			value = new VariableValue(VariableType.VT_LONG, new BoxedLong(readNextLong(vg)));
			break;

		case VariableType.STRING:
			value = new VariableValue(VariableType.VT_STRING, new BoxedString(readNextString(vg)));
			break;

		case VariableType.GROUP:
			value = readNextGroup((VariableGroupType) vg.actual());
			vg.next();
			break;

		default:
			throw new UndefinedTypeException();
		}
		return value;
	}
}
