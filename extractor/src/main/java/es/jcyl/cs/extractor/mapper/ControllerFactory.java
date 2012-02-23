package es.jcyl.cs.extractor.mapper;

import es.jcyl.cs.extractor.exception.ControllerAlreadySetException;
import es.jcyl.cs.extractor.exception.FlowCycleException;

public class ControllerFactory {

	public static Controller createController(AbstractMapper mapper) throws FlowCycleException, ControllerAlreadySetException {
		Controller c = new Controller();
		c.addMapper(mapper);
		return c;
	}

}
