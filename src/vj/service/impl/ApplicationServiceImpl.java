package vj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vj.dao.IApplicationDao;
import vj.domain.Application;
import vj.service.IApplicationService;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {
	@Resource
	private IApplicationDao applicationDao;

	public List<Application> findByCriteria(DetachedCriteria dc) {
		return applicationDao.findByCriteria(dc);
	}

	public Application findById(Integer applicationId) {
		return applicationDao.findById(applicationId);
	}

}
