package vj.service.impl;


import javax.annotation.Resource;

import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vj.dao.IUserDao;
import vj.domain.User;
import vj.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Resource
	private IUserDao userDao;
	
	public User login(User user) {
		return userDao.findByLoginNameAndPassword(user);
	}

}
