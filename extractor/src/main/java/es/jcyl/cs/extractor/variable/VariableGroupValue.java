package es.jcyl.cs.extractor.variable;

import java.util.ArrayList;

public class VariableGroupValue extends VariableValue {
	private static final long serialVersionUID = 1L;

//	public static final VariableGroupValue DEFAULT = new VariableGroupValue();

	private VariableValue[] values = null;
	private VariableGroupType vgroup = null;

//	public VariableGroupValue() {
//		super(VariableType.VT_GROUP);
//	}

	public VariableGroupValue(VariableGroupType vgt) {
		super(vgt);
		fill(vgt);
	}

//	public VariableGroupValue(String name) {
//		super(VariableType.VT_GROUP, name);
//	}

	public VariableGroupValue(VariableGroupType vgt, String name) {
		super(vgt, name);
		fill(vgt);
	}

	public VariableGroupValue(VariableGroupType vgt, String name,
			VariableValue[] values) {
		super(vgt, name);
		this.values = values;
	}

	private void fill(VariableGroupType vgt) {
		values = new VariableValue[vgt.size()];
		for (int i = 0; i < vgt.size(); i++) {
			if (vgt.get(i).getType() != VariableType.GROUP) {
				values[i] = new VariableValue(vgt.get(i));
			} else {
				values[i] = new VariableGroupValue((VariableGroupType) vgt.get(i));
//				((VariableGroupValue) (values[i]))
//						.fill((VariableGroupType) (vgt.get(i)));
			}
		}
	}

	public VariableGroupValue(VariableGroupType type, VariableValue[] val) {
		super(type);
		setValues(val);
	}

	public VariableGroupValue(VariableGroupValue val) {
		super(val.getType());
		setValues(val.values);
	}

	protected void setValues(VariableValue[] values) {
		// this.values = new VariableValue[values.length];
		this.values = values;
		ArrayList ar = new ArrayList(values.length);
		for (int i = 0; i < values.length; i++) {
			// this.values[i] = (VariableValue) values[i].clone();
			ar.add(this.values[i].getType());
		}
		super.setType(new VariableGroupType(ar));
		// super.setValue(this);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]).append(" ");
		}
		return sb.toString();
	}

	public VariableGroupType getVariableGroupType() {
		return vgroup;
	}

	public VariableValue getValue(int index) {
		return values[index];
	}

	public VariableType getValueType(int index) {
		return vgroup.get(index);
	}

	public int size() {
		return values.length;
	}

	public VariableValue getVariableValue(int i) {
		return getValue(i);
	}

	public void setValue(int i, VariableValue object) {
		values[i] = object;
	}

	public VariableValue set(VariableValue value) {
		// TODO: Falta de implementar
		throw new Error("Por implementar;");
	}

	public VariableValue _clone() {
		VariableValue[] copy = new VariableValue[values.length];
		for (int i = 0; i < values.length; i++) {
			copy[i] = values[i]._clone();
		}
		return new VariableGroupValue((VariableGroupType) getType(), getName(),
				copy);
	}

	public static void main(String[] args) {
		try {
			VariableGroupType vgt = new VariableGroupType();
			vgt.add(VariableType.VT_INT);
			vgt.add(VariableType.VT_DOUBLE);

			VariableGroupType vgt2 = new VariableGroupType();
			vgt2.add(VariableType.VT_INT);
			vgt2.add(VariableType.VT_INT);

			vgt.add(vgt2);
			System.out.println(vgt);

			VariableGroupValue vgv = new VariableGroupValue(vgt);
			System.out.println(vgv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VariableGroupValue getGroupValue(int i) {
		return (VariableGroupValue)getValue(i);
	}
}
