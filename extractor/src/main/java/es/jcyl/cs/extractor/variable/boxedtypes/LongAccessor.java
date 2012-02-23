package es.jcyl.cs.extractor.variable.boxedtypes;

public class LongAccessor implements BoxedAccessor {

	public double setDoubleValue(BoxedValue bi, double value) {
		return ((BoxedLong)bi).setLongValue((long) value);
	}

	public float setFloatValue(BoxedValue bi, float value) {
		return (float)((BoxedLong)bi).setLongValue((long) value);
	}

	public int setIntValue(BoxedValue bi, int value) {
		return (int)((BoxedLong)bi).setLongValue((int) value);
	}

	public long setLongValue(BoxedValue bi, long value) {
		return ((BoxedLong)bi).setLongValue(value);
	}

	public short setShortValue(BoxedValue bi, short value) {
		return (short)((BoxedLong)bi).setLongValue((short) value);
	}

	public String setStringValue(BoxedValue bi, String value) {
		((BoxedLong)bi).setLongValue(new Long(value).longValue());
		return value;
	}
	
	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		throw new UnsupportedOperationException();
	}

}
