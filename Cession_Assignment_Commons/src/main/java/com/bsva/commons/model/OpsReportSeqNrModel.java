package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @author DimakatsoN
 *
 */
public class OpsReportSeqNrModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Date processDate;
	private String memberNo;
	private String reportNo;
	private String lastSeqNo;
	private Date modifiedDate;
	private String modifiedBy;
	private Date createdDate;
	private String createdBy;
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) { 
		
		this.processDate = processDate;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getLastSeqNo() {
		return lastSeqNo;
	}
	public void setLastSeqNo(String lastSeqNo) {
		this.lastSeqNo = lastSeqNo;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((lastSeqNo == null) ? 0 : lastSeqNo.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((reportNo == null) ? 0 : reportNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpsReportSeqNrModel other = (OpsReportSeqNrModel) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (lastSeqNo == null) {
			if (other.lastSeqNo != null)
				return false;
		} else if (!lastSeqNo.equals(other.lastSeqNo))
			return false;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (reportNo == null) {
			if (other.reportNo != null)
				return false;
		} else if (!reportNo.equals(other.reportNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OpsReportSeqNrModel [processDate=" + processDate
				+ ", memberNo=" + memberNo + ", reportNo=" + reportNo
				+ ", lastSeqNo=" + lastSeqNo + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + "]";
	}
	
	
	
	

}
