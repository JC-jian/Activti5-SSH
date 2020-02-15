package vj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vj.dao.IApproveInfoDao;
import vj.domain.ApproveInfo;
import vj.service.IApproveInfoService;

@Service
@Transactional
public class ApproverInfoServiceImpl implements IApproveInfoService {
	@Resource
	private IApproveInfoDao approveInfoDao;

	public List<ApproveInfo> findByApplicationId(Integer applicationId) {
		return approveInfoDao.findByApplicationId(applicationId);
	}

}
