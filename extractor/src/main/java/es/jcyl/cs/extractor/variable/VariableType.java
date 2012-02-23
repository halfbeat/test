package es.jcyl.cs.extractor.variable;

import java.io.Serializable;
import java.util.HashMap;

import es.jcyl.cs.extractor.variable.boxedtypes.BoxedBoolean;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedDouble;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedFloat;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedInteger;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedLong;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedString;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedValue;

public class VariableType implements Serializable {
	private static final long serialVersionUID = -1362389804517894395L;
	final static int UNDEFINED = 0;
	final static int LONG = 1;
	final static int STRING = 2;
	final static int SHORT = 3;
	final static int INT = 4;
	final static int FLOAT = 5;
	final static int DOUBLE = 6;
	final static int BOOLEAN = 7;
	final static int GROUP = 8;
	final static int ANY = 9;

	static HashMap cadenas = new HashMap() {
		private static final long serialVersionUID = 1L;
		{
			put(new Integer(UNDEFINED), "UNDEFINED");
			put(new Integer(LONG), "LONG");
			put(new Integer(STRING), "STRING");
			put(new Integer(INT), "INT");
			put(new Integer(FLOAT), "FLOAT");
			put(new Integer(DOUBLE), "DOUBLE");
			put(new Integer(BOOLEAN), "BOOLEAN");
			put(new Integer(GROUP), "GROUP");
			put(new Integer(ANY), "ANY");
			put(new Integer(SHORT), "SHORT");
		}
	};

	private int type = VariableType.UNDEFINED;

	protected VariableType(int t) {
		type = t;
	}

	protected void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static final VariableType VT_UNDEFINED = new VariableType(
		VariableType.UNDEFINED);
	public static final VariableType VT_LONG = new VariableType(
		VariableType.LONG);
	public static final VariableType VT_STRING = new VariableType(
		VariableType.STRING);
	public static final VariableType VT_INT = new VariableType(VariableType.INT);
	public static final VariableType VT_FLOAT = new VariableType(
		VariableType.FLOAT);
	public static final VariableType VT_DOUBLE = new VariableType(
		VariableType.DOUBLE);
	public static final VariableType VT_BOOLEAN = new VariableType(
		VariableType.BOOLEAN);
//	public static final VariableType VT_GROUP = new VariableType(
//		VariableType.GROUP);
	public static final VariableType VT_ANY = new VariableType(VariableType.ANY);
	public static final VariableType VT_SHORT = new VariableType(
		VariableType.SHORT);

	public boolean equals(Object obj) {
		if (obj instanceof VariableType) {
			return this.getType() == ((VariableType) obj).getType();
		}
		return false;
	}

	public String toString() {
		return (String) cadenas.get(new Integer(type));
	}

	public static BoxedValue getDefaultValue(int type) {
		BoxedValue result = null;
		switch (type) {
		case STRING:
			result = new BoxedString("UNDEFSTR");
			break;
		case LONG:
			result = new BoxedLong(0L);
			break;
		case INT:
			result = new BoxedInteger(0);
			break;
		case DOUBLE:
			result = new BoxedDouble(0d);
			break;
		case FLOAT:
			result = new BoxedFloat(0f);
			break;
		case BOOLEAN:
			result = new BoxedBoolean(false);
			break;
		// case GROUP:
		// result = new ValueWrapper(new VariableGroupValue());
		// break;
		}

		return result;
	}

	public BoxedValue getDefaultValue() {
		return VariableType.getDefaultValue(this.type);
	}

	public boolean isGroup() {
		return type == GROUP;
	}

	public boolean isCompatible(VariableType type) {
		return isCompatible(this, type);
	}

	protected static boolean isCompatible(VariableType t1, VariableType t2) {
		if (t1.type == ANY || t2.type == ANY) {
			return true;
		}

		if (t1.isGroup() || t2.isGroup()) {
			if (t1.isGroup() && t2.isGroup()) {
				VariableGroupType vg1 = (VariableGroupType) t1;
				VariableGroupType vg2 = (VariableGroupType) t2;
				return isGroupCompatible(vg1, vg2);
			} else {
				return false;
			}
		} else {
			return t1 == t2;
		}
	}

	private static boolean isGroupCompatible(VariableGroupType vg1,
		VariableGroupType vg2) {
		if (vg1.size() != vg2.size()) {
			return false;
		}
		for (int i = 0; i < vg1.size(); i++) {
			if (!isCompatible(vg1.get(i), vg2.get(i))) {
				return false;
			}
		}
		return true;
	}

	public VariableValue createValue() {
		return new VariableValue(this);
	}

	public VariableValue createValue(String name) {
		return new VariableValue(this, name);
	}
}
