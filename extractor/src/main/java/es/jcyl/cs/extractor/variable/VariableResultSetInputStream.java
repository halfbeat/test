package es.jcyl.cs.extractor.variable;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import es.jcyl.cs.extractor.exception.UnalignedVariableException;
import es.jcyl.cs.extractor.exception.UndefinedTypeException;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedDouble;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedFloat;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedInteger;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedLong;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedString;

public class VariableResultSetInputStream {

	protected VariableGroupType vgroup;
	protected ResultSet rset;
	protected int actualColumn;
	private boolean hasNext = false;
	public static Logger logger = Logger
		.getLogger(VariableResultSetInputStream.class);

	public VariableResultSetInputStream(VariableGroupType vg, ResultSet in)
		throws SQLException, UnalignedVariableException {
		this.vgroup = vg;
		this.rset = in;
		actualColumn = 1;
		hasNext = rset.next();
		if (rset.getMetaData().getColumnCount() != vgroup.fullsize()) {
			throw new UnalignedVariableException(
				"El numero de columnas devueltas no coincide con el numero de campos del grupo");
		}
	}

	public int readNextInt(VariableGroupType vg) throws IOException,
		SQLException {
		if (!vg.actual().equals(VariableType.VT_INT)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		int r = rset.getInt(actualColumn++);
		vg.next();
		if (vg.rotated()) {
			actualColumn = 1;
			hasNext = rset.next();
		}
		return r;
	}

	public int readNextUInt(VariableGroupType vg) throws IOException,
		SQLException {
		if (!vg.actual().equals(VariableType.VT_INT)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		int r = rset.getInt(actualColumn++);
		vg.next();
		if (vg.rotated()) {
			actualColumn = 1;
			hasNext = rset.next();
		}
		return r;
	}

	public long readNextLong(VariableGroupType vg) throws IOException,
		SQLException {
		if (!vg.actual().equals(VariableType.VT_LONG)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		long r = rset.getLong(actualColumn++);
		vg.next();
		if (vg.rotated()) {
			actualColumn = 1;
			hasNext = rset.next();
		}
		return r;
	}

	public long readNextULong(VariableGroupType vg) throws IOException,
		SQLException {
		return readNextLong(vg);
	}

	public double readNextDouble(VariableGroupType vg) throws IOException,
		SQLException {
		if (!vg.actual().equals(VariableType.VT_DOUBLE)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		double r = rset.getDouble(actualColumn++);
		vg.next();
		if (vg.rotated()) {
			actualColumn = 1;
			hasNext = rset.next();
		}
		return r;
	}

	public float readNextFloat(VariableGroupType vg) throws IOException,
		SQLException {
		if (!vg.actual().equals(VariableType.VT_FLOAT)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		float r = rset.getFloat(actualColumn++);
		vg.next();
		if (vg.rotated()) {
			actualColumn = 1;
			hasNext = rset.next();
		}
		return r;
	}

	public String readNextString(VariableGroupType vg) throws IOException,
		SQLException {
		if (!vg.actual().equals(VariableType.VT_STRING)) {
			throw new UndefinedTypeException("El tipo no coincide.");
		}
		String r = rset.getString(actualColumn++);
		vg.next();
		if (vg.rotated()) {
			actualColumn = 1;
			hasNext = rset.next();
		}
		return r;
	}

	protected VariableGroupType getVGroup() {
		return vgroup;
	}

	protected void setVGroup(VariableGroupType vgroup) {
		this.vgroup = vgroup;
	}

	public VariableValue[] readNextGroupAsArray(VariableGroupType vg)
		throws IOException, SQLException {
		// if(rset.cou)
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

	public VariableGroupValue readNextGroup() throws IOException, SQLException {
		return new VariableGroupValue(DefaultVariableGroupType.type,
			readNextGroupAsArray(vgroup));
	}

	private VariableGroupValue readNextGroup(VariableGroupType vg)
		throws IOException, SQLException {
		return new VariableGroupValue(DefaultVariableGroupType.type,
			readNextGroupAsArray(vg));
	}

	public VariableValue readNext(VariableGroupType vg) throws IOException,
		SQLException {
		VariableValue value = null;
		switch (vg.actual().getType()) {
		case VariableType.DOUBLE:
			value = new VariableValue(VariableType.VT_DOUBLE, new BoxedDouble(
				readNextDouble(vg)));
			break;

		case VariableType.FLOAT:
			value = new VariableValue(VariableType.VT_FLOAT, new BoxedFloat(
				readNextFloat(vg)));
			break;

		case VariableType.INT:
			value = new VariableValue(VariableType.VT_INT, new BoxedInteger(
				readNextInt(vg)));
			break;

		case VariableType.LONG:
			value = new VariableValue(VariableType.VT_LONG, new BoxedLong(
				readNextLong(vg)));
			break;

		case VariableType.STRING:
			value = new VariableValue(VariableType.VT_STRING, new BoxedString(
				readNextString(vg)));
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

	public boolean available() {
		return hasNext;
	}

	public void reset() throws SQLException {
		rset.beforeFirst();
		hasNext = rset.next();
	}

	public void dispose() {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}
}
