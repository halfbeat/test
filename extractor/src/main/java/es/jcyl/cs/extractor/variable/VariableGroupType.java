package es.jcyl.cs.extractor.variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class VariableGroupType extends VariableType {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList variables = new ArrayList();
	private int actual = -1;
	private boolean rotated = true;

//	public VariableGroupType(VariableType var) {
//		super(VariableType.GROUP);
//		variables.add(var);
//	}

	public VariableGroupType(Object[] vars) {
		super(VariableType.GROUP);
		for (int i = 0; i < vars.length; i++) {
			variables.add(vars[i]);
		}
	}

	public VariableGroupType(List vars) {
		this(vars.toArray());
	}

	public VariableGroupType() {
		super(VariableType.GROUP);
	}

	public void add(VariableType v) {
		variables.add(v);
	}

	public void remove(VariableType v) {
		variables.remove(v);
	}

	public void remove(int index) {
		variables.remove(index);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("[");
		for (int i = 0; i < variables.size(); i++) {
			sb.append(variables.get(i).toString()).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}

	public Collection getVariables() {
		return variables;
	}

	protected void setVariables(ArrayList data) {
		this.variables = data;
		actual = -1;
	}

	public VariableType first() {
		if (variables == null || variables.size() == 0) {
			throw new IndexOutOfBoundsException();
		}
		actual = 0;
		rotated = true;
		return (VariableType) variables.get(actual);
	}

	public VariableType actual() {
		return (VariableType) variables.get(actual);
	}

	public VariableType next() {
		if (actual < 0 || variables == null || variables.size() == 0) {
			throw new IndexOutOfBoundsException();
		}
		rotated = actual == variables.size() - 1 ? true : false;
		actual = (actual + 1) % variables.size();
		return (VariableType) variables.get(actual);
	}

	public boolean rotated() {
		return rotated;
	}

	public int size() {
		return variables.size();
	}

	public VariableType get(int index) {
		return (VariableType) variables.get(index);
	}

	public int fullsize() {
		int count = 0;
		Iterator it = variables.iterator();
		while (it.hasNext()) {
			VariableType v = (VariableType) it.next();
			if (v.isGroup()) {
				count += ((VariableGroupType) v).fullsize();
			} else {
				count++;
			}
		}
		return count;
	}

	public VariableValue createValue() {
		return new VariableGroupValue(this);
	}

	public VariableValue createValue(String name) {
		return new VariableGroupValue(this, name);
	}

}
