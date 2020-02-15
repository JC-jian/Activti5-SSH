package vj.dao;

import java.util.List;

import vj.domain.ApproveInfo;

public interface IApproveInfoDao {

	List<ApproveInfo> findByApplicationId(Integer applicationId);

	void save(ApproveInfo ai);

}
