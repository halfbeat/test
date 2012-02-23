package es.jcyl.cs.extractor.util;

import java.io.PrintStream;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.exception.PortNameInUseException;
import es.jcyl.cs.extractor.mapper.AbstractMapper;
import es.jcyl.cs.extractor.mapper.AbstractSink;
import es.jcyl.cs.extractor.mapper.Port;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;

/**
 * Clase Simple para mostrar por un PrintStream lo que le llega a este Mapper
 * 
 * @author Angel
 * @see AbstractMapper
 */
public class SimpleOutputWriter extends AbstractSink {
	/**
	 * PrintStream por el que se mostrará la salida
	 */
	PrintStream pstream = null;

	/**
	 * Constructor de la clase
	 * 
	 * @param out
	 *            PrintStream al que enviar la salida
	 * @throws ControllerException 
	 * @throws PortNameInUseException 
	 */
	public SimpleOutputWriter(PrintStream out) throws PortException, ControllerException {
		super();
		addInput("0", VariableType.VT_ANY);
		pstream = out;
	}

	/**
	 * @see AbstractMapper#execute()
	 */
	public boolean execute() throws MappingException {
		Port[] data = getAllInputs();
		for(int i = 0; i < data.length; i++) {
			VariableValue vv = data[i].read();
			pstream.println(vv);
			pstream.flush();
		}
		return true;
	}
}
