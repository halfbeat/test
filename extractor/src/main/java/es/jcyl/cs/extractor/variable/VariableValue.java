package es.jcyl.cs.extractor.variable;

import java.io.Serializable;

import org.apache.log4j.Logger;

import es.jcyl.cs.extractor.exception.InvalidTypeException;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedValue;

public class VariableValue implements Cloneable, Serializable {
	private static final long serialVersionUID = -680921956997384244L;
	protected VariableType type;
	protected BoxedValue value;
	protected String name;
	public final static VariableValue NULL = new VariableValue(VariableType.VT_UNDEFINED);
	public final static Logger LOGGER = Logger.getLogger(VariableValue.class);
	
	public VariableValue(VariableType type, String name, BoxedValue val) {
		this.type = type;
		this.setName(name);
		this.value = val;
	}

	public VariableValue(VariableType type, String name) {
		this.type = type;
		value = type.getDefaultValue();
		setName(name);
	}
	
	public VariableValue(VariableType type, BoxedValue val) {
		this.type = type;
		this.setName(name);
		this.value = val;
	}

	public VariableValue(VariableType type) {
		this.type = type;
		value = type.getDefaultValue();
	}
	
	public VariableValue(VariableValue varValue) {
		this.type = varValue.type;
		this.name = varValue.name == null ? null : new String(varValue.name);
		this.value = varValue.value._clone();
	}

	public VariableType getType() {
		return type;
	}

	protected void setType(VariableType type) {
		this.type = type;
	}

	public BoxedValue getValue() {
		return value;
	}

	protected VariableValue setValue(final BoxedValue value) {
		this.value = value;
		return this;
	}
	
	public VariableValue setStringValue(String data) throws InvalidTypeException {
		value.setStringValue(data);
		return this;
	}
	
	public VariableValue setIntValue(int data) throws InvalidTypeException {
		value.setIntValue(data);
		return this;
	}
	
	public VariableValue setLongValue(long data) throws InvalidTypeException {
		value.setLongValue(data);
		return this;
	}
	
	public VariableValue setDoubleValue(double data) throws InvalidTypeException {
		value.setDoubleValue(data);
		return this;
	}
	
	public VariableValue setFloatValue(float data) throws InvalidTypeException {
		value.setFloatValue(data);
		return this;
	}
	
	public VariableValue setBooleanValue(boolean data) throws InvalidTypeException {
		value.setBooleanValue(data);
		return this;
	}

	public String toString() {
		return value == null ? "null" :  value.toString();
	}

	public String asString() throws InvalidTypeException {
		return value.stringValue();
	}

	public int asInteger() throws InvalidTypeException {
		return value.intValue();
	}

	public long asLong() throws InvalidTypeException {
		return value.longValue();
	}

	public float asFloat() throws InvalidTypeException {
		return value.floatValue();
	}

	public double asDouble() throws InvalidTypeException {
		return value.doubleValue();
	}
	
	public boolean asBoolean() throws InvalidTypeException {
		return value.booleanValue();
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public VariableValue _clone() {
		return new VariableValue(type, name, value);
	}

	public VariableValue set(VariableValue value) {
		this.type = value.type;
		this.name = value.name == null ? null : new String(value.name);
		this.value = value.value._clone();
		return this;
	}
	
}
