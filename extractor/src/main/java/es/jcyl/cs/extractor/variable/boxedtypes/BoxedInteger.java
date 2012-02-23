package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedInteger extends BoxedValue {
	private static final long serialVersionUID = 1L;
	private int value;

	public BoxedInteger(int value) {
		this.value = value;
	}

	public double doubleValue() {
		return (double)value;
	}

	public float floatValue() {
		return (float)value;
	}

	public int intValue() {
		return value;
	}

	public long longValue() {
		return (long)value;
	}

	public int setIntValue(int value) {
		this.value = value;
		return this.value;
	}

	public String stringValue() {
		return String.valueOf(value);
	}

	public BoxedAccessor getAccessor() {
		return accessor;
	}
	
	private static BoxedAccessor accessor = new IntegerAccessor();
	
	public BoxedValue _clone() {
		return new BoxedInteger(this.value);
	}

	public VariableType getType() {
		return VariableType.VT_INT;
	}
	
	public boolean booleanValue() {
		return value == 1;
	}

	public short shortValue() {
		return (short) value;
	}
}
