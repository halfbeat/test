package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public abstract class BoxedValue {
	
	public abstract double doubleValue();
	public abstract float floatValue();
	public abstract int intValue();
	public abstract long longValue();
	public abstract String stringValue();
	public abstract boolean booleanValue();
	public abstract short shortValue();
	
	public abstract BoxedAccessor getAccessor();
	
	public short setShortValue(short value) {
		return getAccessor().setShortValue(this, value);
	}
	
	public int setIntValue(int value) {
		return getAccessor().setIntValue(this, value);
	}
	
	public long setLongValue(long data) {
		return getAccessor().setLongValue(this, data);
	}
	
	public boolean setBooleanValue(boolean data) {
		return getAccessor().setBooleanValue(this, data);
	}
	
	public float setFloatValue(float value) {
		return getAccessor().setFloatValue(this, value);
	}
	
	public double setDoubleValue(double value) {
		return getAccessor().setDoubleValue(this, value);
	}

	public String setStringValue(String value) {
		return getAccessor().setStringValue(this, value);
	}
	
	public abstract BoxedValue _clone();

	public String toString() {
		return stringValue();
	}
	
	public abstract VariableType getType();
}
