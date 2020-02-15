package vj.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vj.domain.User;
import vj.service.IUserService;

/**
 * 用户操作action
 * 
 * @author vjames
 *
 */

@Controller
@Scope("prototype") // 多例
public class UserAction extends ActionSupport implements ModelDriven<User> {
	@Resource
	private IUserService userService;

	// 提供模型对象
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	/**
	 * 登录功能
	 */
	public String login() {
		User exUser = userService.login(user);
		if (exUser != null) { // 登录成功
			// 存入session
			ActionContext.getContext().getSession().put("loginUser", exUser);
			// 跳转页面
			return "home";
		} else { // 登录失败
			// 返回错误信息
			this.addActionError(this.getText("loginError"));
			// 跳转回首页
			return "login";
		}

	}
	
	/**
	 * 注销功能
	 */
	public String logout() {
		//清除session
		ActionContext.getContext().getSession().remove("loginUser");
		return "login";		
	}

}
