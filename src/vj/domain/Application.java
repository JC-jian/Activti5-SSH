package vj.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 申请实体类
 * @author vjames
 *
 */

public class Application implements Serializable {
	private Integer id;
	private String title;  //申请标题 格式{模板名称}_{申请人}_{申请日期}
	private User applicant; //申请人
	private Date applyDate; //申请日期
	private String status;   //申请状态
	private String docFilePath; //附件申请单地址
	private Set<ApproveInfo> approveInfos; //对应的审批实体
	private Template template; //当前使用的模板对象
	
	public static final String STATUS_APPROVING="审批中";
	public static final String STATUS_UNAPPROVED="未通过";
	public static final String STATUS_APPROVED="已通过";
	
	@Override
	public String toString() {
		return "Application [id=" + id + ", title=" + title + ", applicant=" + applicant + ", applyDate=" + applyDate
				+ ", status=" + status + ", docFilePath=" + docFilePath + ", approveInfos=" + approveInfos
				+ ", template=" + template + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocFilePath() {
		return docFilePath;
	}
	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
	}
	public Set<ApproveInfo> getApproveInfos() {
		return approveInfos;
	}
	public void setApproveInfos(Set<ApproveInfo> approveInfos) {
		this.approveInfos = approveInfos;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public static String getStatusApproving() {
		return STATUS_APPROVING;
	}
	public static String getStatusUnapproved() {
		return STATUS_UNAPPROVED;
	}
	public static String getStatusApproved() {
		return STATUS_APPROVED;
	}

}
