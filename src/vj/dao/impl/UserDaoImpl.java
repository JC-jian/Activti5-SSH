package vj.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import vj.dao.IUserDao;
import vj.domain.User;

/**
 * 用户操作Dao
 * @author vjames
 *
 */
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {
	//注入会话工厂对象
	//<property name="sessionFactory" ref="sessionFactory"></property>
	@Resource
	public void setSF(SessionFactory sessionFactory  ){
		super.setSessionFactory(sessionFactory);
	}
			
	
	/**
	 * 登录名和密码查询用户
	 */
	public User findByLoginNameAndPassword(User user) {
		String sql ="FROM User WHERE loginName = ? AND password = ?" ;
		List<User> exUser = this.getHibernateTemplate().find(sql,user.getLoginName(),user.getPassword());
		if(exUser !=null && exUser.size() > 0){
			return exUser.get(0);
		}else{
			return null;
		}
	}
	
}
