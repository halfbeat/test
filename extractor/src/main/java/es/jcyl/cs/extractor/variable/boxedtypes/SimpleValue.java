package es.jcyl.cs.extractor.variable.boxedtypes;

public class SimpleValue extends Value {
	protected BoxedValue value;
	
	public SimpleValue(BoxedValue value) {
		super();
		this.value = value;
	}
	
	public BoxedValue getValue() {
		return value;
	}
}
