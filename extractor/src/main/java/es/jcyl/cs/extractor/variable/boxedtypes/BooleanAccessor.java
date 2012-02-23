package es.jcyl.cs.extractor.variable.boxedtypes;

public class BooleanAccessor implements BoxedAccessor {

	public double setDoubleValue(BoxedValue bi, double value) {
		((BoxedBoolean)bi).setBooleanValue(value == 0 ? false : true);
		return value;
	}

	public float setFloatValue(BoxedValue bi, float value) {
		((BoxedBoolean)bi).setBooleanValue(value == 0 ? false : true);
		return value;
	}

	public int setIntValue(BoxedValue bi, int value) {
		((BoxedBoolean)bi).setBooleanValue(value == 0 ? false : true);
		return value;
	}

	public long setLongValue(BoxedValue bi, long value) {
		((BoxedBoolean)bi).setBooleanValue(value == 0 ? false : true);
		return value;
	}

	public short setShortValue(BoxedValue bi, short value) {
		((BoxedBoolean)bi).setBooleanValue(value == 0 ? false : true);
		return value;
	}

	public String setStringValue(BoxedValue bi, String value) {
		if("true".equals(value)) {
			((BoxedBoolean)bi).setBooleanValue(true);
		} else if("false".equals(value)) {
			((BoxedBoolean)bi).setBooleanValue(false);
		} else {
			throw new UnsupportedOperationException();
		}
		return value;
		
	}

	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		return ((BoxedBoolean)bi).setBooleanValue(data);
	}

}
