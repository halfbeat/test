package es.jcyl.cs.extractor.variable.boxedtypes;

public class DoubleAccessor implements BoxedAccessor {

	public double setDoubleValue(BoxedValue bi, double value) {
		return ((BoxedDouble)bi).setDoubleValue(value);
	}

	public float setFloatValue(BoxedValue bi, float value) {
		return (float)((BoxedDouble)bi).setDoubleValue((double)value);
	}

	public int setIntValue(BoxedValue bi, int value) {
		return (int)((BoxedDouble)bi).setDoubleValue((double)value);
	}

	public long setLongValue(BoxedValue bi, long value) {
		return (long)((BoxedDouble)bi).setDoubleValue((double)value);
	}

	public short setShortValue(BoxedValue bi, short value) {
		return (short)((BoxedDouble)bi).setDoubleValue((double)value);
	}

	public String setStringValue(BoxedValue bi, String value) {
		setDoubleValue(bi, new Double(value).doubleValue());
		return value;
	}
	
	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		throw new UnsupportedOperationException();
	}

}
