package es.jcyl.cs.extractor.mapper;

import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.variable.VariableGroupType;
import es.jcyl.cs.extractor.variable.VariableGroupValue;


public interface IMapper {
	AbstractMapper getInput();
	AbstractMapper getOutput();
	AbstractMapper setInput(AbstractMapper input);
	AbstractMapper setOutput(AbstractMapper output);
	VariableGroupType getInputType();
	VariableGroupType getOutputType();
	VariableGroupType setInputType(VariableGroupType inputType);
	VariableGroupType setOutputType(VariableGroupType outputType);
	boolean update() throws MappingException;
	void execute() throws MappingException;
	VariableGroupValue readData() throws MappingException;
	boolean isError();
}
