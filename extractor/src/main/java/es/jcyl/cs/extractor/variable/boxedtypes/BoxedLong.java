package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedLong extends BoxedValue {
	private long value;

	public BoxedLong(long value) {
		this.value = value;
	}
	
	public double doubleValue() {
		return (double)value;
	}

	public float floatValue() {
		return (float)value;
	}

	public BoxedAccessor getAccessor() {
		return accessor;
	}

	public int intValue() {
		return (int)value;
	}

	public long longValue() {
		return (long)value;
	}

	public String stringValue() {
		return Long.toString(value);
	}
	
	private static BoxedAccessor accessor = new LongAccessor();

	public long setLongValue(long value) {
		this.value = value;
		return this.value;
	}

	public BoxedValue _clone() {
		return new BoxedLong(this.value);
	}
	
	public VariableType getType() {
		return VariableType.VT_LONG;
	}
	
	public boolean booleanValue() {
		return value == 1L;
	}

	public short shortValue() {
		return (short) value;
	}
}
