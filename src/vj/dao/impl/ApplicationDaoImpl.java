package vj.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import vj.dao.IApplicationDao;
import vj.domain.Application;
import vj.domain.ApproveInfo;

@Repository
public class ApplicationDaoImpl extends HibernateDaoSupport implements IApplicationDao {

	//注入会话工厂对象
		//<property name="sessionFactory" ref="sessionFactory"></property>
		@Resource
		public void setSF(SessionFactory sessionFactory  ){
			super.setSessionFactory(sessionFactory);
		}

		public void save(Application application) {
				this.getHibernateTemplate().save(application);
		}

		public List<Application> findByCriteria(DetachedCriteria dc) {
			return this.getHibernateTemplate().findByCriteria(dc);
		}

		public Application findById(Integer applicationId) {
			return this.getHibernateTemplate().get(Application.class, applicationId);
		}

		
}
