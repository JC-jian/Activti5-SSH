package vj.web.action;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.bpmn.converter.BaseBpmnXMLConverter;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.struts2.util.DateFormatter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.bind.v2.model.core.ID;

import vj.domain.Application;
import vj.domain.ApproveInfo;
import vj.domain.TaskView;
import vj.domain.Template;
import vj.service.IApplicationService;
import vj.service.IApproveInfoService;
import vj.service.IFlowService;
import vj.service.ITemplateService;
import vj.utils.ActContext;
import vj.utils.UploadFileUtils;

/**
 * 流程控制action
 * @author vjames
 *
 */
@Controller
@Scope("prototype")
public class FlowAction extends ActionSupport{
	@Resource
	private ITemplateService templateService;
	@Resource
	private IFlowService flowService;
	@Resource
	private IApplicationService applicationService;
	@Resource
	private IApproveInfoService approveInfoService;
	
	private Integer applicationId; //申请id
	private String taskId; //任务id
	private String comment; //审批意见
	private Boolean approval;//是否通过
	
	public Boolean getApproval() {
		return approval;
	}

	public void setApproval(Boolean approval) {
		this.approval = approval;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	private String deploymentId; //返回图片流 接收的id
	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	private String  imageName; //返回图片流 接收的图片name
	
	public IFlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(IFlowService flowService) {
		this.flowService = flowService;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	private String status; //接收申请状态
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Integer templateId; //跳转到申请页面 模板id
	
	private File resource;//接收上传的申请信息doc

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	/**
	 * 查询所有流程模板
	 */
	public String templateList(){
		List<Template> list = templateService.findList();
		ActionContext.getContext().put("templateList", list);
		return "templateList";
	}
	
	/**
	 * 跳转到提交申请页面
	 */
	public String submitUI() {
		return "submitUI";
	}
	
	/**
	 * 提交申请
	 */
	public String submit(){
		Template template = templateService.findById(templateId);
		//1、保存上传的文件
		String filePath = UploadFileUtils.copy(resource);
		
		
		
		//2、保存申请实体信息
		Application application =new Application();
		application.setApplicant(ActContext.getLoginUser()); //申请人
		application.setApplyDate(new Date()); //申请时间
		application.setDocFilePath(filePath); //上传文件地址
		application.setStatus(Application.STATUS_APPROVING); //设置审核中
		application.setTemplate(template); //使用的模板对象
		String datestr = new SimpleDateFormat("yyyy-MM-dd").format(application.getApplyDate());
		String title = template.getName()+"_"+ActContext.getLoginUser().getName()+"_"+datestr; 
		application.setTitle(title);// 标题
		
		flowService.submit(application);

		
		return "toTemplateList"; //跳到我的申请查询
	}
	
	/**
	 * 当前登录人的申请列表
	 */
	public String myApplicationList(){
		//根据登录人或者状态查询
		DetachedCriteria dc = DetachedCriteria.forClass(Application.class);
		
		//添加排序条件
		dc.addOrder(Order.desc("applyDate"));
		
		dc.add(Restrictions.eq("applicant", ActContext.getLoginUser()));
		if(status != null && status.trim().length() >0){
			//选择了申请状态
			dc.add(Restrictions.eq("status", status));
		}
		
		List<Application> list =  applicationService.findByCriteria(dc);
		ActionContext.getContext().put("applicationList", list);
		return "myApplicationList";
	}
	
	/**
	 * 显示流程图片 参数 applicationId
	 */
	public String showPng(){
		//通过applicationId
		//需要准备的数据：deploymentId imageName acs.x acs.y  acs.width  acs.height
		
		Task task =flowService.findTaskByApplicationId(applicationId);
		
		//根据当前任务查询流程定义对象
		ProcessDefinition pd = flowService.findPDByTask(task);
		
		ActionContext.getContext().put("deploymentId", pd.getDeploymentId());
		ActionContext.getContext().put("imageName", pd.getDiagramResourceName());
		Map<String, Object> map = flowService.findCoordingByTask(task);
		ActionContext.getContext().put("acs", map);
		return "showPng";
	}
	
	/**
	 * 查询png流
	 * 参数deploymentId  imageName
	 */
	public String viewImage(){
		InputStream in = flowService.getPngStream(deploymentId,imageName);
		ActionContext.getContext().put("imageStream", in);
		return "viewImage";
	}
	
	
	/**
	 * 查询审批流程记录
	 * 参数 applicationId
	 */
	public String approveInfoList(){
	List<ApproveInfo> list = approveInfoService.findByApplicationId(applicationId);
	ActionContext.getContext().put("list", list);
		return "approveInfoList";
	}
	
	/**
	 * 查询申请+任务列表
	 */
	public String myTaskList(){
		List<TaskView> list = flowService.findList(ActContext.getLoginUser());
		ActionContext.getContext().put("list", list);
		return "myTaskList";
	}
	
	/**
	 * 跳转到审批处理页面
	 */
	public String approveUI(){
		return "approveUI";
	}
	
	/**
	 * 审批处理
	 */
	public String approve(){
		Application application = applicationService.findById(applicationId);
		//1.包装一个审批实体并保存
		ApproveInfo ai  =new ApproveInfo();
		ai.setApplication(application);//审批对应的申请实体
		ai.setApproval(approval);//是否通过
		ai.setApproveDate(new Date()); //审批时间
		ai.setApprover(ActContext.getLoginUser()); //审批人
		ai.setComment(comment); //审批信息
		
		
		flowService.approve(ai,taskId);
		
		return "toMyTaskList";
	}
}
