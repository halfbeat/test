package es.jcyl.cs.extractor.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import es.jcyl.cs.extractor.exception.ConnectorNotAttached;
import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

/**
 * Clase base del Mapper. Es el elemento mínimo de construcción de bloques de comunicación de
 * valores.
 * 
 * @author Angel de Jesús Vizcaíno [jesvizan@jcyl.es]
 * 
 */
public abstract class AbstractMapper extends Observable /* implements IMapper */{
	/** 
	 * Nombre del Mapper 
	 */
	private String name = "NO NAME";
	/**
	 * Puertos de entrada
	 */
	public transient final Ports inputs = new Ports();
	/**
	 * Puertos de salida
	 */
	public transient final Ports outputs = new Ports();
	/**
	 * Controlador asociado al Mapper.
	 * @see es.jcyl.cs.extractor.mapper.Controller
	 */
	private Controller controller = null;
	
	/**
	 * Estado de fallo del Mapper
	 */
	private transient boolean fail = false;

	private final static Logger logger = Logger.getLogger(AbstractMapper.class);

	/** 
	 * Constructor del Mapper.
	 * @throws ControllerException
	 */
	protected AbstractMapper() throws ControllerException {
		super();
		//ControllerFactory.createController(this);
	}

	/** 
	 * Permite asociar puertos de entrada nombrada al mapper.
	 * @param objectId Nombre único del mapper de entrada
	 * @param port	Puerto de entrada asociado al nombre
	 * @throws PortException
	 */
	protected void addInput(String objectId, Port port) throws PortException {
		port.setOwner(this);
		inputs.add(port, objectId);
	}

	/**
	 * Permite asociar puertos de entrada nombrada al mapper.
	 * @param objectId Nombre único del mapper de entrada
	 * @param varType Tipo de variable asociada al puerto 
	 * @see VariableType
	 * @throws PortException
	 */
	protected void addInput(String objectId, VariableType varType)
			throws PortException {
		Port port = new InputPort(varType, this);
		port.setOwner(this);
		inputs.add(port, objectId);
	}

	/**
	 * Permite asociar puertos de entrada al mapper. Cada puerto se cre de forma secuencial
	 * en posiciones 0, 1, 2 ...
	 * @param port	Puerto de entrada incializado
	 * @throws PortException
	 */
	protected void addInput(InputPort port) throws PortException {
		port.setOwner(this);
		inputs.add(port);
	}

	/**
	 * Adición del un puerto de entrada al mapper de tipo vtype. Cada puerto se cre de forma secuencial
	 * en posiciones 0, 1, 2 ...
	 * @param varType Tipo de variable asociada al puerto 
	 */
	protected void addInput(VariableType varType) {
		Port port = new InputPort(varType);
		port.setOwner(this);
		inputs.add(port);
	}

	/**
	 * Recupera el puerto de entrada de índice portnum
	 * @param portnum Ìndice del puerto
	 * @return Puerto de entrada asociado al índice
	 */
	protected InputPort getInput(int portnum) {
		return (InputPort) inputs.get(portnum);
	}

	/**
	 * Recupera el puerto asociado al nombre
	 * @param name Nombre del puerto
	 * @return Puerto de entrada asociado al nombre
	 */
	protected InputPort getInput(String name) {
		return (InputPort) inputs.get(name);
	}

	/**
	 * Adición de un puerto al conjunto de puertos de salida
	 * @param name Nombre único del puerto
	 * @param port Puerto de salida
	 * @throws PortException
	 */
	protected void addOutput(String name, OutputPort port) throws PortException {
		port.setOwner(this);
		outputs.add(port, name);
	}

	protected void addOutput(String name, VariableType varType)
			throws PortException {
		Port port = new OutputPort(varType, this);
		port.setOwner(this);
		outputs.add(port, name);
	}

	protected void addOutput(OutputPort port) throws PortException {
		port.setOwner(this);
		outputs.add(port);
	}

	protected void addOutput(VariableType vtye) {
		Port port = new OutputPort(vtye);
		port.setOwner(this);
		outputs.add(port);
	}

	protected OutputPort getOutput(int portnum) {
		return (OutputPort) outputs.get(portnum);
	}

	protected OutputPort getOutput(String objectId) {
		return (OutputPort) outputs.get(objectId);
	}

	protected void setError() {
		fail = true;
	}

	protected boolean isError() {
		return fail;
	}

	protected boolean internalExecute() throws MappingException {
		return internalExecute(this);
	}

	protected boolean internalExecute(AbstractMapper mapper)
			throws MappingException {
		boolean result = false;
		if (update()) {
			logger.debug("Mapper actualizado. Ejecutando payload:" + this.getClass() + ":execute()");
			result = execute();
			if (result) {
				// logger.debug("Mapper ejecutado con éxito [true]");
			} else {
				// logger.debug("Mapper ejecutado con error [true]");
			}
		} else {
			// logger.debug("Mapper no actualizado. Actualizamos hacia atrás");
			List alist = getInputMappers();
			Iterator iter = alist.iterator();
			while (iter.hasNext()) {
				result = ((AbstractMapper) iter.next()).internalExecute();
				if (!result) {
					break;
				}
			}
			if (result && !alist.isEmpty() && update()) {
				logger.debug("Mapper actualizado. Ejecutando payload:" + this.getClass() + ":execute()");
				result = execute();
				if (result) {
					// logger.debug("Mapper ejecutado con éxito [true]");
				} else {
					// logger.debug("Mapper ejecutado con error [true]");
				}
			}
		}
		return result;
	}

	private boolean moveData() throws ConnectorNotAttached {
		boolean result = false;
		if (inputs.isEmpty()) {
			result = false;
		} else {
			result = true;
			Iterator iter = inputs.getIterator();
			while (iter.hasNext()) {
				Port port = (Port) iter.next();
				if (/*!port.isLinked() || */!port.moveData()) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	private List getInputMappers() throws MappingException {
		Iterator iter = inputs.getIterator();
		ArrayList alist = new ArrayList();
		while (iter.hasNext()) {
			InputPort iPort = (InputPort) iter.next();
			OutputPort oPort = iPort.getLinkedPort();
			if (oPort != null && iPort.isEnabled() && oPort.isEnabled()
					&& oPort.getOwner() != null
					&& !alist.contains(oPort.getOwner())) {
				alist.add(oPort.getOwner());
			}
		}
		return alist;
	}

	// protected boolean internalUpdate() throws MappingException {
	// boolean result = false;
	//
	// ArrayList alist = new ArrayList();
	// Iterator iter = inputs.values().iterator();
	// while (iter.hasNext()) {
	// Port input = (Port) iter.next();
	// if (!input.hasDataAvailable()) {
	// /*
	// * if (!ar.contains(input.link.owner)) {
	// * ar.add(input.link.owner); }
	// */
	// }
	// }
	// iter = alist.iterator();
	// while (iter.hasNext()) {
	// AbstractMapper mapper = (AbstractMapper) iter.next();
	// result = mapper.internalUpdate() && result;
	// }
	// return result;
	// }

	protected boolean readyForExecute() {
		return getAllInputData().length != 0;
	}

	public abstract boolean execute() throws MappingException;

	public boolean available() throws MappingException {
		boolean result = false;
		Iterator iter = inputs.getIterator();
		while (iter.hasNext()) {
			Port port = (Port) iter.next();
			if (port.hasDataAvailable()) {
				result = true;
				break;
			}
		}
		return result;
	}

	final boolean run() throws MappingException {
		// logger.debug("Inicio de la ejecución del AbstractMapper");
		return internalExecute();
	}

	final int runAll(int maxIterations) throws MappingException {
		int iteration = 0;
		while (iteration < maxIterations && run()) {
			iteration++;
		}
		return iteration;
	}

	final int runAll() throws MappingException {
		int iteration = 0;
		while (run()) {
			iteration++;
		}
		return iteration;
	}

	public void subscribe(Observer observer) {
		addObserver(observer);
	}

	public void unsubscribe(Observer observer) {
		deleteObserver(observer);
	}

	public void dispose() {
	}

	protected InputPort[] getAllInputs() {
		return (InputPort[]) inputs.toArray(new InputPort[inputs.size()]);
	}

	protected OutputPort[] getAllOutputs() {
		return (OutputPort[]) outputs.toArray(new OutputPort[outputs.size()]);
	}

	// private void updateDataInputReference() {
	// Port[] pins = getAllInputs();
	// int count = pins.length;
	// dInputRef = new VariableValue[count];
	// for (int i = 0; i < count; i++) {
	// dInputRef[i] = pins[i].data;
	// }
	// }

	protected VariableValue[] getAllInputData() {
		VariableValue[] result;
		if (inputs.isEmpty()) {
			result = new VariableValue[0];
		} else {
			Port[] pins = getAllInputs();
			VariableValue[] dInputRef = new VariableValue[pins.length];
			result = new VariableValue[0];
			int index;
			for (index = 0; index < pins.length; index++) {
				if (!pins[index].hasDataAvailable()) {
					break;
				} else {
					dInputRef[index] = pins[index].data;
				}
			}
			if (index == pins.length) {
				result = dInputRef;
			}
		}
		return result;
	}

	protected VariableValue getInputData(String inputId) {
		Port port = inputs.get(inputId);
		return port.hasDataAvailable() ? port.read() : VariableValue.NULL;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// public String toString() {
	// StringBuffer sbuffer = new StringBuffer(100);
	// sbuffer.append("Mapper: " + getName() + "\nInputs: \n");
	// Iterator iter = inputs.getIterator();
	// Port port;
	// String key;
	// while (iter.hasNext()) {
	// Port p = (Port) iter.next();
	// key = inputs.getName(p);
	// port = (Port) inputs.get(key);
	// sbuffer.append("\t" + key + ": " + port + "\n");
	// }
	// sbuffer.append("Outputs:\n");
	// iter = outputs.getIterator();
	// while (iter.hasNext()) {
	// Port p = (Port) iter.next();
	// key = outputs.getName(p);
	// port = (Port) outputs.get(key);
	// sbuffer.append("\t" + key + ": " + port + "\n");
	// }
	// return sbuffer.toString();
	// }

	protected boolean update() throws MappingException {
		return readyForExecute() || moveData();
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	AbstractMapper[] getAllInputMappers(boolean filterDuplicates) {
		return inputs.getMappers(filterDuplicates);
	}

	AbstractMapper[] getAllOutputMappers(boolean filterDuplicates) {
		return outputs.getMappers(filterDuplicates);
	}
}
