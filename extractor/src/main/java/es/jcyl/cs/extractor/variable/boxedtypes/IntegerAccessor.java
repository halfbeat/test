package es.jcyl.cs.extractor.variable.boxedtypes;

class IntegerAccessor implements BoxedAccessor {

	public int setIntValue(BoxedValue bi, int value) {
		return ((BoxedInteger)bi).setIntValue(value);
	}
	
	public float setFloatValue(BoxedValue bi,float value) {
		return (float)setIntValue(bi, (int)value);
	}
	
	public double setDoubleValue(BoxedValue bi,double value) {
		return (double)setIntValue(bi, (int)value);
	}

	public String setStringValue(BoxedValue bi,String value) {
		setIntValue(bi, new Integer(value).intValue());
		return value;
	}

	public long setLongValue(BoxedValue bi, long value) {
		return (long)setIntValue(bi, (short)value);
	}

	public short setShortValue(BoxedValue bi, short value) {
		return (short)setIntValue(bi, (short)value);
	}
	
	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		throw new UnsupportedOperationException();
	}
}
