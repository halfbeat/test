package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedDouble extends BoxedValue {
	double value;
	
	public BoxedDouble(double value) {
		this.value = value;
	}

	public double doubleValue() {
		return value;
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
		return Double.toString(value);
	}
	
	private static BoxedAccessor accessor = new DoubleAccessor();
	
	public BoxedValue _clone() {
		return new BoxedDouble(this.value);
	}

	public VariableType getType() {
		return VariableType.VT_DOUBLE;
	}
	
	public boolean booleanValue() {
		return value == 1d;
	}

	public short shortValue() {
		return (short) value;
	}
	
	public double setDoubleValue(double value) {
		this.value = value;
		return this.value;
	}
}
