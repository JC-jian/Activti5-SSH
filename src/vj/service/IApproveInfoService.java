package vj.service;

import java.util.List;

import vj.domain.ApproveInfo;

public interface IApproveInfoService {

	List<ApproveInfo> findByApplicationId(Integer applicationId);

}
