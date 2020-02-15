package vj.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

public interface IProcessDefintionService {

	List<ProcessDefinition> findLastList();

	public void delByKey(String key);

	public void deploy(File resource);

	InputStream findPngBypdId(String pdId);

}
