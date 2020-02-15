package vj.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import vj.domain.Application;
import vj.domain.ApproveInfo;

public interface IApplicationDao {

	void save(Application ai);

	List<Application> findByCriteria(DetachedCriteria dc);

	Application findById(Integer applicationId);

}
