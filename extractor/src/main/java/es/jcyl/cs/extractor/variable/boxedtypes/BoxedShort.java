package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedShort extends BoxedValue {
	private short value = 0;
	
	public BoxedShort(short value) {
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
		return String.valueOf(value);
	}

	private BoxedAccessor accessor = new ShortAccessor();

	public short setShortValue(short value) {
		this.value = value;
		return this.value;
	}
	
	public BoxedValue _clone() {
		return new BoxedShort(this.value);
	}
	
	public VariableType getType() {
		return VariableType.VT_SHORT;
	}
	
	public boolean booleanValue() {
		return value == 1;
	}

	public short shortValue() {
		return value;
	}
}
