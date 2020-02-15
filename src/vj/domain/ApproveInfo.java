package vj.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 审批实体类
 * @author vjames
 *
 */

public class ApproveInfo implements Serializable {
	private Integer id;
	private User approver; //审批人
	private Date approveDate; //审批时间
	private Boolean approval; //是否通过
	private String comment;  //审批意见
	private Application application; //当前实体对应的申请实体
	
	
	@Override
	public String toString() {
		return "ApproveInfo [id=" + id + ", approver=" + approver + ", approveDate=" + approveDate + ", approval="
				+ approval + ", comment=" + comment + ", application=" + application + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Boolean getApproval() {
		return approval;
	}
	public void setApproval(Boolean approval) {
		this.approval = approval;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
}
