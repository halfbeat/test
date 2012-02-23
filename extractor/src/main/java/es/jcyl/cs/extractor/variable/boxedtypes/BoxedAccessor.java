package es.jcyl.cs.extractor.variable.boxedtypes;

public interface BoxedAccessor {
	public short setShortValue(BoxedValue bi, short value);
	public int setIntValue(BoxedValue bi, int value);
	public long setLongValue(BoxedValue bi, long value);
	public float setFloatValue(BoxedValue bi, float value);
	public double setDoubleValue(BoxedValue bi, double value);
	public String setStringValue(BoxedValue bi, String value);
	public boolean setBooleanValue(BoxedValue boxedValue, boolean data);
}
