package es.jcyl.cs.extractor.variable.boxedtypes;

public class ShortAccessor implements BoxedAccessor {

	public double setDoubleValue(BoxedValue bi, double value) {
		return (double)((BoxedShort)bi).setShortValue((short)value);
	}

	public float setFloatValue(BoxedValue bi, float value) {
		return (float)((BoxedShort)bi).setShortValue((short)value);
	}

	public int setIntValue(BoxedValue bi, int value) {
		return (int)((BoxedShort)bi).setShortValue((short)value);
	}

	public String setStringValue(BoxedValue bi, String value) {
		((BoxedShort)bi).setShortValue(new Short(value).shortValue());
		return value;
	}

	public long setLongValue(BoxedValue bi, long value) {
		return (long)((BoxedShort)bi).setShortValue((short)value);
	}

	public short setShortValue(BoxedValue bi, short value) {
		return ((BoxedShort)bi).setShortValue(value);
	}
	
	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		throw new UnsupportedOperationException();
	}

}
