package vj.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vj.dao.IApplicationDao;
import vj.dao.IApproveInfoDao;
import vj.domain.Application;
import vj.domain.ApproveInfo;
import vj.domain.TaskView;
import vj.domain.User;
import vj.service.IFlowService;

@Service
@Transactional
public class flowServiceImpl implements IFlowService {
	
	@Resource
	IApplicationDao applicationDao;
	
	@Resource
	ProcessEngine processEngine;
	
	@Resource
	IApproveInfoDao approveInfoDao;

	/**
	 * 提交申请
	 */
	public void submit(Application application) {
		//2、保存申请实体对象
		applicationDao.save(application);
		
		//3、启动一个流程实例
		String key= application.getTemplate().getPdKey();
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("application", application);
		variables.put("applicationId", application.getId());
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(key, variables);
		
		//4、办理自己的流程
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee(application.getApplicant().getLoginName());
		query.processInstanceId(pi.getId());
		Task task = query.singleResult();
		processEngine.getTaskService().complete(task.getId());
	}
	
	/**
	 * 返回图片流对象
	 */
	public InputStream getPngStream(String deploymentId, String imageName) {
		return processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageName);
	}

	/**
	 * 根据流程变量id查询任务
	 */
	public Task findTaskByApplicationId(Integer applicationId) {
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		//根据设置的流程变量获取
		query.processVariableValueEquals("applicationId", applicationId);
		return query.singleResult();
	}

	/**
	 * 根据任务查询流程定义
	 */
	public ProcessDefinition findPDByTask(Task task) {
		String id = task.getProcessDefinitionId();
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.processDefinitionId(id);
		return query.singleResult();
	}
	
	//根据任务查询当前任务节点坐标 acs.x acs.y  acs.width  acs.height
	public Map<String, Object> findCoordingByTask(Task task) {
		//获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		
		ProcessInstanceQuery query = processEngine.getRuntimeService().createProcessInstanceQuery();
		//根据流程实例id过滤
		query.processInstanceId(processInstanceId);
		ProcessInstance prInstance = query.singleResult();
		
		//获取当前节点
		String activityId = prInstance.getActivityId();
		
		//获取流程定义id
		String processDefinitionId = task.getProcessDefinitionId();

		//返回流程定义对象中包含的坐标信息
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition(processDefinitionId);
		
		ActivityImpl activityImpl = pd.findActivity(activityId);
		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int width = activityImpl.getWidth();
		int height = activityImpl.getHeight();
		
		Map<String, Object> map = new HashMap<>();
		map.put("x", x);
		map.put("y", y);
		map.put("width", width);
		map.put("height", height);
		return map;
	}

	/**
	 * 根据当前登录人查询对应的任务列表，包装成 List<TaskView>返回
	 */
	public List<TaskView> findList(User loginUser) {
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee(loginUser.getLoginName()); //添加过滤条件
		query.orderByTaskCreateTime().desc(); //添加排序条件
		List<Task> taskList = query.list();
		
		List<TaskView> list = new ArrayList<TaskView>();
		for (Task task : taskList) {
			String id = task.getId();
			Application application =(Application) processEngine.getTaskService().getVariable(id, "application");
			TaskView tView = new TaskView(task, application);
			list.add(tView);
		}
		return list;
	}
	
	/**
	 * 审批处理
	 */
	public void approve(ApproveInfo ai, String taskId) {
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		//获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		
		//1.保存ai
		approveInfoDao.save(ai); //持久对象
		//2.办理当前任务
		processEngine.getTaskService().complete(taskId);
		
		Application application = ai.getApplication(); //持久对象
		
		//查询流程实例是否存在
		 ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		
		if(ai.getApproval()){//3.审批处理为通过
			//如果办理人是最后一个节点，则修改流程状态为已通过
			if(pi == null){
				//流程已经结束改流程状态为已通过
				application.setStatus(Application.STATUS_APPROVED);
			}
			
		}else{//  审批处理为不通过
		
					//将流程状态修改为不通过
			application.setStatus(Application.STATUS_UNAPPROVED);
					//如果办理人不是最后一个节点，则手动结束任务
			if(pi !=null){
				processEngine.getRuntimeService().deleteProcessInstance(processInstanceId, Application.STATUS_UNAPPROVED);
			}
		}
		
		
		
	}

}
