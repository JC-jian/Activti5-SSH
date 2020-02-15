package test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;


public class test {
	
	@Test
	public void test1(){
		//创建一个流程引擎配置对象
		ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//设置数据源信息
		conf.setJdbcDriver("com.mysql.jdbc.Driver");
		conf.setJdbcUrl("jdbc:mysql:///activti_0204");
		conf.setJdbcUsername("vjames");
		conf.setJdbcPassword("123");
		
		//设置自动建表
		conf.setDatabaseSchemaUpdate("true");
		
		//创建一个流程引擎对象
		ProcessEngine processEngine = conf.buildProcessEngine();
		
		
		
		/**
		 * 部署流程定义
		 */
		//获得一个部署构建对象
		DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
		//加载指定流程文件
		builder.addClasspathResource("test\\MyProcess.bpmn");
		builder.addClasspathResource("test\\MyProcess.png");
		//部署流程定义
		Deployment deployment = builder.deploy();
		System.out.println(deployment);
		
	}
	
	
	

}
