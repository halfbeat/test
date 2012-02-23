package es.jcyl.cs.extractor.variable.boxedtypes;

import java.util.Collection;
import java.util.Iterator;

public abstract class GroupValue extends Value {
	public abstract Collection getValues();
	public abstract void addValue(Value v);
	public abstract void removeValue(Value v);
	public abstract Iterator iterator();
}
