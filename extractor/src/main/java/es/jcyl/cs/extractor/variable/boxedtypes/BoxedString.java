package es.jcyl.cs.extractor.variable.boxedtypes;

import es.jcyl.cs.extractor.variable.VariableType;

public class BoxedString extends BoxedValue {
	private String value;

	public BoxedString(String s) {
		value = s;
	}

	public BoxedString(char[] s) {
		value = String.copyValueOf(s);
	}

	public double doubleValue() {
		return new Double(value).doubleValue();
	}

	public float floatValue() {
		return new Float(value).floatValue();
	}

	public int intValue() {
		return new Integer(value).intValue();
	}

	public long longValue() {
		return new Long(value).longValue();
	}

	public String stringValue() {
		return value;
	}

	public String setStringValue(String value) {
		this.value = value;
		return this.value;
	}

	public static void main(String[] args) {
		try {
			BoxedValue v[] = new BoxedValue[2];
			v[0] = new BoxedString("Hola a todos");
			v[1] = new BoxedInteger(3);
			v[0].setDoubleValue(23.5d);
			v[1].setDoubleValue(23.5d);
			for (int i = 0; i < v.length; i++) {
				System.out.println(v[i].stringValue());
			}
			BoxedValue bv = v[1]._clone();
			bv.setIntValue(56);
			System.out.println(v[1]);
			System.out.println(bv);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public BoxedAccessor getAccessor() {
		return accessor;
	}

	private static BoxedAccessor accessor = new StringAccessor();
	
	public BoxedValue _clone() {
		return new BoxedString(this.value);
	}
	
	public VariableType getType() {
		return VariableType.VT_STRING;
	}
	
	public boolean booleanValue() {
		return "1".equals(value);
	}

	public short shortValue() {
		return new Short(value).shortValue();
	}
}
