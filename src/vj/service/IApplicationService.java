package vj.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import vj.domain.Application;

public interface IApplicationService {

	List<Application> findByCriteria(DetachedCriteria dc);

	Application findById(Integer applicationId);

}
