package es.jcyl.cs.extractor.variable.boxedtypes;

public class FloatAccessor implements BoxedAccessor {

	public double setDoubleValue(BoxedValue bi, double value) {
		return ((BoxedFloat)bi).setFloatValue((float) value);
	}

	public float setFloatValue(BoxedValue bi, float value) {
		return (float)((BoxedFloat)bi).setFloatValue(value);
	}

	public int setIntValue(BoxedValue bi, int value) {
		return (int)((BoxedFloat)bi).setFloatValue((float)value);
	}

	public long setLongValue(BoxedValue bi, long value) {
		return (long)((BoxedFloat)bi).setFloatValue((float)value);
	}

	public short setShortValue(BoxedValue bi, short value) {
		return (short)((BoxedFloat)bi).setFloatValue((float)value);
	}

	public String setStringValue(BoxedValue bi, String value) {
		setDoubleValue(bi, new Float(value).doubleValue());
		return value;
	}
	
	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		throw new UnsupportedOperationException();
	}

}
