package vj.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vj.service.IProcessDefintionService;

/**
 * 流程定义管理Service
 * 
 * @author vjames
 *
 */
@Service
@Transactional
public class ProcessDefintionServiceImpl implements IProcessDefintionService {
	@Resource
	ProcessEngine processEngine;

	/**
	 * 查询最新版本流程定义
	 */
	public List<ProcessDefinition> findLastList() {
		// 流程定义查询
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.orderByProcessDefinitionVersion().asc();
		List<ProcessDefinition> list = query.list();

		// 只留最新版本
		Map<String, ProcessDefinition> map = new HashMap<>();
		for (ProcessDefinition pd : list) {
			map.put(pd.getKey(), pd);

		}
		return new ArrayList<ProcessDefinition>(map.values());
	}

	/**
	 * 根据key删除流程定义
	 */
	public void delByKey(String key) {
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.processDefinitionKey(key);
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
		}
	}

	/**
	 * zip部署流程定义
	 */
	public void deploy(File resource) {
		DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
		ZipInputStream zipInputStream=null;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(resource));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		builder.addZipInputStream(zipInputStream);
		builder.deploy();
	}

	/**
	 * 根据流程id返回图片的流
	 */
	public InputStream findPngBypdId(String pdId) {
		
		return processEngine.getRepositoryService().getProcessDiagram(pdId);
	}

}
