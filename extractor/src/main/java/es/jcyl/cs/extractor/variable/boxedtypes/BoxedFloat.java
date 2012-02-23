package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedFloat extends BoxedValue {
	private float value = 0f;
	
	public BoxedFloat(float value) {
		this.value = value;
	}
	
	public float setFloatValue(float value) {
		this.value = value;
		return this.value;
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
		return Float.toString(value);
	}
	
	private static BoxedAccessor accessor = new FloatAccessor();
	
	public BoxedValue _clone() {
		return new BoxedFloat(this.value);
	}

	public VariableType getType() {
		return VariableType.VT_FLOAT;
	}
	
	public boolean booleanValue() {
		return value == 1f;
	}

	public short shortValue() {
		return (short) value;
	}
}
