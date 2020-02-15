package vj.web.action;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.repository.ProcessDefinition;
import org.hibernate.engine.query.ReturnMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import vj.service.IProcessDefintionService;

/**
 * 流程定义管理Action
 * @author vjames
 *
 */
@Controller
@Scope("prototype")
public class ProcessDefintionAction extends ActionSupport {
	@Resource
	private IProcessDefintionService processDefintionService;
	//属性驱动
	private String key;	
	private File resource; //用于接收zip文件
	private String pdId;//查看图片id
	
	
	public void setPdId(String pdId) {
		this.pdId = pdId;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 查询最新流程定义列表
	 */
	public String list() {	
		List<ProcessDefinition> list = processDefintionService.findLastList();
		//压栈
		ActionContext.getContext().put("pdList", list);
		return "list";
	}
	
	/**
	 * 根据key删除流程定义
	 */
	public String delByKey() {
		processDefintionService.delByKey(key);
		return "tolist"; //重定向 
	}
	
	/**
	 *zip部署流程定义
	 */
	public String deploy(){
		processDefintionService.deploy(resource);
		return "tolist";
	}
	
	/**
	 * 根据id查看图片
	 */
	public String showPng(){
		InputStream in=processDefintionService.findPngBypdId(pdId);
		ActionContext.getContext().put("pngStream", in);
		return "showPng";
		
	}
}
