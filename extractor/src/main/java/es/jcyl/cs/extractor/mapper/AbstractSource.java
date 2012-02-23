package es.jcyl.cs.extractor.mapper;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;

public abstract class AbstractSource extends AbstractMapper {
	protected AbstractSource() throws ControllerException {
		super();
	}
	
	protected boolean readyForExecute() {
		return true;
	}

	public boolean execute() throws MappingException {
		return true;
	}

//	protected abstract boolean update() throws MappingException;
}
