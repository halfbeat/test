package es.jcyl.cs.extractor.mapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org._3pq.jgrapht.graph.SimpleDirectedGraph;


import es.jcyl.cs.extractor.exception.ConnectorAlreadyAttached;
import es.jcyl.cs.extractor.exception.ConnectorNotAttached;
import es.jcyl.cs.extractor.exception.ControllerAlreadySetException;
import es.jcyl.cs.extractor.exception.FlowCycleException;
import es.jcyl.cs.extractor.exception.IncompatibleMappers;
import es.jcyl.cs.extractor.exception.MappingException;

public class Controller {
	private SimpleDirectedGraph graph = new SimpleDirectedGraph();
	private Set sources = new HashSet();
	private Set targets = new HashSet();

	public Controller() {
		super();
	}

	public void addMapper(AbstractMapper mapper) throws FlowCycleException,
			ControllerAlreadySetException {
		if (mapper.getController() == this) {
			throw new ControllerAlreadySetException();
		}
		mapper.setController(this);
		updateMapper(mapper);
	}

	public void updateMapper(AbstractMapper mapper) throws FlowCycleException {
		SimpleDirectedGraph g = (SimpleDirectedGraph) graph.clone();
		try {
			graph.addVertex(mapper);
			AbstractMapper[] inputs = mapper.getAllInputMappers(true);
			AbstractMapper[] outputs = mapper.getAllOutputMappers(true);
			int i;
			for (i = 0; i < inputs.length; i++) {
				graph.addVertex(inputs[i]);
				graph.addEdge(mapper, inputs[i]);
			}
			for (i = 0; i < outputs.length; i++) {
				graph.addVertex(outputs[i]);
				graph.addEdge(mapper, outputs[i]);
			}
			if (mapper instanceof AbstractSource) {
				sources.add(mapper);
			}
			if (mapper instanceof AbstractSink) {
				targets.add(mapper);
			}
		} catch (Exception ex) {
			graph = (SimpleDirectedGraph) g;
			throw new FlowCycleException(
					"Se han creado ciclos en el controlador");
		}

	}

	public boolean run() throws MappingException {
		return ((AbstractMapper) targets.iterator().next()).run();
	}

	public int runAll(int maxIterations) throws MappingException {
		Iterator it = targets.iterator();
		int count = 0;
		while(it.hasNext()) {
			AbstractSink sink = (AbstractSink) it.next();
			count += sink.runAll(maxIterations);
		}
		return count;
	}

	public int runAll() throws MappingException {
		Iterator it = targets.iterator();
		int count = 0;
		while(it.hasNext()) {
			AbstractSink sink = (AbstractSink) it.next();
			count += sink.runAll();
		}
		return count;
	}

	public void link(AbstractMapper srcMapper, int srcPortNum,
			AbstractMapper dstMapper, int dstPortNum)
			throws IncompatibleMappers, ConnectorAlreadyAttached,
			FlowCycleException, ControllerAlreadySetException {
		Port source = srcMapper.getOutput(srcPortNum);
		Port destination = dstMapper.getInput(dstPortNum);
		if (source == null || destination == null ) {
			throw new IncompatibleMappers(
					"No existen los puertos especificados");
		} else {			
			source.linkTo(destination);
			updateMapper(srcMapper);
			updateMapper(dstMapper);
		}
	}

	public void link(AbstractMapper srcMapper, String srcPortId,
			AbstractMapper dstMapper, String dstPortId)
			throws IncompatibleMappers, ConnectorAlreadyAttached,
			FlowCycleException, ControllerAlreadySetException {
		Port source = srcMapper.getOutput(srcPortId);
		Port destination = (dstMapper.getInput(dstPortId));
		if (source == null || destination == null) {
			throw new IncompatibleMappers(
					"No existen los puertos especificados");
		} else {
			source.linkTo(destination);
			updateMapper(srcMapper);
			updateMapper(dstMapper);
		}
	}

	public void link(AbstractMapper s1, AbstractMapper s2)
			throws IncompatibleMappers, FlowCycleException,
			ControllerAlreadySetException, ConnectorAlreadyAttached {
		int firstInputFreeNumPort = s2.inputs.getFirstFreePort();
		int firstOutputFreeNumPort = s1.outputs.getFirstFreePort();
		if (firstInputFreeNumPort == -1 || firstOutputFreeNumPort == -1) {
			throw new IncompatibleMappers("No existen puertos disponibles");
		}
		link(s1, firstOutputFreeNumPort, s2, firstInputFreeNumPort);
	}

	public void unlinkInput(AbstractMapper mapper, int numPort) throws ConnectorNotAttached {
		InputPort port = mapper.getInput(numPort);
		if(port != null) {
			port.unlink();
		}
	}
	
	public void unlinkOutput(AbstractMapper mapper, int numPort) throws ConnectorNotAttached {
		OutputPort port = mapper.getOutput(numPort);
		if(port != null) {
			port.unlink();
		}
	}

}
