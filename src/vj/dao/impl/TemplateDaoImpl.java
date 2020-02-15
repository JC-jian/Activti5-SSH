package vj.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import vj.dao.ITemplateDao;
import vj.domain.Template;

@Repository
public class TemplateDaoImpl extends HibernateDaoSupport implements ITemplateDao {
	//注入会话工厂对象
		//<property name="sessionFactory" ref="sessionFactory"></property>
		@Resource
		public void setSF(SessionFactory sessionFactory  ){
			super.setSessionFactory(sessionFactory);
		}
		
		/**
		 * 查询模板list
		 */
		public List<Template> findList() {
			String sql = "FROM Template";
			return this.getHibernateTemplate().find(sql);
		}
		
		/**
		 *保存模板对象
		 */
		public void save(Template template) {
			this.getHibernateTemplate().save(template);
			
		}
		
		
		
		/**
		 * 根据id查询记录
		 */
		public Template findById(Integer id) {
			return this.getHibernateTemplate().get(Template.class, id);
		}
		
		/**
		 * 删除对象记录
		 */
		public void del(Template template) {
			this.getHibernateTemplate().delete(template);
		}
		
		/**
		 * 更新数据
		 */
		public void updata(Template tp) {
			this.getHibernateTemplate().update(tp);
		}
}
