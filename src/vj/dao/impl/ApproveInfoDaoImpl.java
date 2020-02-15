package vj.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import vj.dao.IApproveInfoDao;
import vj.domain.ApproveInfo;

@Repository
public class ApproveInfoDaoImpl extends HibernateDaoSupport implements IApproveInfoDao {
	//注入会话工厂对象
			//<property name="sessionFactory" ref="sessionFactory"></property>
			@Resource
			public void setSF(SessionFactory sessionFactory  ){
				super.setSessionFactory(sessionFactory);
			}
			
			/**
			 * 根据applicationId查询流转记录
			 */
			public List<ApproveInfo> findByApplicationId(Integer applicationId) {
				String hql = "FROM ApproveInfo ai WHERE ai.application.id = ? ORDER BY ai.approveDate";
				return this.getHibernateTemplate().find(hql, applicationId);
			}

			public void save(ApproveInfo ai) {
				this.getHibernateTemplate().save(ai);
			}
}
