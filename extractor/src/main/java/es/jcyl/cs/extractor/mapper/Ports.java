package es.jcyl.cs.extractor.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import es.jcyl.cs.extractor.exception.PortNameInUseException;

public class Ports {
	private Map portMap;
	private List portList;

	public Ports() {
		portMap = new HashMap();
		portList = new ArrayList();
	}

	public Ports(int size) {
		portMap = new HashMap(size);
		portList = new ArrayList(size);
	}

	public void add(Port port) {
		portList.add(port);
	}

	public void add(Port port, int position) {
		portList.add(position, port);
	}

	public void add(Port port, int position, String name)
			throws PortNameInUseException {
		if (portMap.get(name) != null) {
			throw new PortNameInUseException();
		}
		portList.add(position, port);
		portMap.put(name, portList.get(position));
	}

	public void add(Port port, String name) throws PortNameInUseException {
		if (portMap.get(name) != null) {
			throw new PortNameInUseException();
		}
		portList.add(port);
		portMap.put(name, port);
	}

	public void setPortName(int position, String name)
			throws PortNameInUseException {
		if (portMap.get(name) != null) {
			throw new PortNameInUseException();
		}
		portMap.put(name, portList.get(position));
	}

	public Iterator getIterator() {
		return portList.iterator();
	}

	public Port[] toArray(Port[] ports) {
		return (Port[]) portList.toArray(ports);
	}

	public Port[] toArray() {
		return (Port[]) portList.toArray();
	}

	public int size() {
		return portList.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Port get(String name) {
		return (Port) portMap.get(name);
	}

	public String getName(Port p) {
		if (portMap.containsValue(p)) {
			Iterator it = portMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (p.equals(portMap.get(key))) {
					return key;
				}
			}
		} else {
			return null;
		}
		return null;
	}

	public Port get(int portnum) {
		return (Port) portList.get(portnum);
	}

	AbstractMapper[] getMappers(boolean filterDuplicates) {
		AbstractMapper[] result = new AbstractMapper[0];
		Iterator it = portList.iterator();
		while(it.hasNext()) {
			Port p = (Port)it.next();
			result = (AbstractMapper[]) ArrayUtils.addAll(result, p.getRemoteMappers(filterDuplicates));
		}

		return result;
	}

	public int getFirstFreePort() {
		int result = -1;
		Iterator it = getIterator();
		while(it.hasNext()) {
			result++;
			Port p = (Port) it.next();
			if(!p.isLinked()) {
				return result;
			}
		}
		return -1;
	}

	public int getPortNum(Port port) {
		int result = -1;
		Iterator it = getIterator();
		while(it.hasNext()) {
			result++;
			Port p = (Port) it.next();
			if(p == port) {
				return result;
			}
		}
		return -1;
	}
}