package es.jcyl.cs.extractor.variable.boxedtypes;


public class StringAccessor implements BoxedAccessor {

	public double setDoubleValue(BoxedValue bi, double value) {
		return new Double(((BoxedString)bi).setStringValue(String.valueOf(value))).doubleValue();
	}

	public float setFloatValue(BoxedValue bi, float value) {
		return new Float(((BoxedString)bi).setStringValue(String.valueOf(value))).floatValue();
	}

	public int setIntValue(BoxedValue bi, int value) {
		return new Integer(((BoxedString)bi).setStringValue(String.valueOf(value))).intValue();
	}

	public String setStringValue(BoxedValue bi, String value) {
		return ((BoxedString)bi).setStringValue(String.valueOf(value));
	}

	public long setLongValue(BoxedValue bi, long value) {
		return new Long(((BoxedString)bi).setStringValue(String.valueOf(value))).longValue();
	}

	public short setShortValue(BoxedValue bi, short value) {
		return new Short(((BoxedString)bi).setStringValue(String.valueOf(value))).shortValue();
	}
	
	public boolean setBooleanValue(BoxedValue bi, boolean data) {
		if(data) {
			((BoxedString)bi).setStringValue(String.valueOf("1"));
		} else {
			((BoxedString)bi).setStringValue(String.valueOf("0"));
		}
		return data;
	}

}
