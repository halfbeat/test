package es.jcyl.cs.extractor.variable.boxedtypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Sequence extends GroupValue {

	protected List values = new ArrayList();
	
	public void addValue(Value value) {
		values.add(value);
	}

	public Collection getValues() {
		return values;
	}

	public void removeValue(Value value) {
		values.remove(value);
	}

	public Iterator iterator() {
		return values.iterator();
	}

}
