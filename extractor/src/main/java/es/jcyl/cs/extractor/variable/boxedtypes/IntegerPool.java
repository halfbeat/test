package es.jcyl.cs.extractor.variable.boxedtypes;

public class IntegerPool {
	int[] buffer;
	
	private IntegerPool(int size) {
		allocate(size);
	}
	
	private synchronized void allocate(int size) {
		buffer = new int[size];
	}
}
