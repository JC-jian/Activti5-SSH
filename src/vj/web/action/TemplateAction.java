package vj.web.action;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vj.domain.Template;
import vj.service.IProcessDefintionService;
import vj.service.ITemplateService;
import vj.utils.Encode;
import vj.utils.UploadFileUtils;

/**
 *表单模板action
 * @author vjames
 *
 */
@Controller
@Scope("prototype")
public class TemplateAction extends ActionSupport implements ModelDriven<Template> {
	private File resource;//用于接收上传的临时文件 
	
	public void setResource(File resource) {
		this.resource = resource;
	}

	@Resource
	private ITemplateService templateService;
	@Resource
	private IProcessDefintionService processDefintionService;

	private Template template = new Template();

	public Template getModel() {
		return template;
	}
	
	/**
	 *查询表单模板list
	 */
	public String list() {
		List<Template> list = templateService.findList();
		ActionContext.getContext().put("list", list);
		return "list";
	}
	
	/**
	 * 添加表单所属模板下拉数据
	 */
	public String addUI() {
		List<ProcessDefinition> lastList = processDefintionService.findLastList();
		ActionContext.getContext().put("lastList", lastList);
		return "addUI";
	}
	
	/**
	 * 上传模板文件
	 */
	public String addTemplate(){
		//将临时文件保存到指定的目录中并返回地址
		String path = UploadFileUtils.copy(resource);
		template.setDocFilePath(path);
		
		templateService.save(template);
		return "tolist";
	}
	
	/**
	 * 根据id删除模板
	 */
	public String delById(){
		templateService.delById(template.getId());
		return "tolist";
	}
	
	/**
	 * 跳转到修改模板页面
	 */
	public String editUI(){
		//根据id查询模板数据，并回显页面
		Template t =  templateService.findById(template.getId());
		ActionContext.getContext().getValueStack().push(t);
		
		//模板下拉数据
		List<ProcessDefinition> lastList = processDefintionService.findLastList();
		ActionContext.getContext().put("lastList", lastList);
		
		return "editUI";
	}
	
	/**
	 * 修改模板
	 */
	public String editTemplate(){
		templateService.editTemplate(resource,template);
		return "tolist";
	}
	
	/**
	 * 下载模板doc文件
	 * @throws IOException 
	 */
	public String download() throws IOException{
		InputStream in = templateService.findDocById(template.getId());
		ActionContext.getContext().put("docStream", in);
		Template tp = templateService.findById(template.getId());
		String docName = tp.getName() + ".doc";
		String agent = ServletActionContext.getRequest().getHeader("user-agent");
		docName = Encode.encodeDownloadFilename(docName, agent);
		ActionContext.getContext().put("docName", docName);
		return "download";
	}
}
