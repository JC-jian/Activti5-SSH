package vj.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import vj.domain.Application;
import vj.domain.ApproveInfo;
import vj.domain.TaskView;
import vj.domain.User;

public interface IFlowService {

	void submit(Application application);

	InputStream getPngStream(String deploymentId, String imageName);

	Task findTaskByApplicationId(Integer applicationId);

	ProcessDefinition findPDByTask(Task task);

	Map<String, Object> findCoordingByTask(Task task);

	List<TaskView> findList(User loginUser);

	void approve(ApproveInfo ai, String taskId);

}
