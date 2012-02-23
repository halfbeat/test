package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedBoolean extends BoxedValue {
	private boolean value = false;

	public BoxedBoolean(boolean value) {
		super();
		this.value = value;
	}
	
	public double doubleValue() {
		return value == true ? 1 : 0;
	}

	public float floatValue() {
		return value == true ? 1 : 0;
	}

	public BoxedAccessor getAccessor() {
		return accessor;
	}

	public int intValue() {
		return value == true ? 1 : 0;
	}

	public long longValue() {
		return value == true ? 1 : 0;
	}

	public String stringValue() {
		return value == true ? "true" : "false";
	}
	
	private static BoxedAccessor accessor = new BooleanAccessor();

	public boolean setBooleanValue(boolean value) {
		this.value = value;
		return this.value;
	}

	public BoxedValue _clone() {
		return new BoxedBoolean(this.value);
	}

	public VariableType getType() {
		return VariableType.VT_BOOLEAN;
	}

	public boolean booleanValue() {
		return value;
	}

	public short shortValue() {
		return (short) (value == true ? 1 : 0);
	}
}
