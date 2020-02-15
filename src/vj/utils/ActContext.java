package vj.utils;

import org.apache.struts2.ServletActionContext;

import vj.domain.User;

public class ActContext {
	/**
	 * 获取当前登录用户方法
	 */
	public static User getLoginUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
