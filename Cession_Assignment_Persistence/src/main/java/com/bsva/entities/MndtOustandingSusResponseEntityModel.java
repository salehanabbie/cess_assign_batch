package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */
public class MndtOustandingSusResponseEntityModel implements Serializable{
	
	private String drMemberName;
	private String memberNo;
	private String assignmentId;
	private String mandateRefNr;
	private BigDecimal nrDaysOutstanding;
	private String  mandateReqTransId;
	private String suspensionId;
	private Date createdDate;
	
	
	public MndtOustandingSusResponseEntityModel() {

	}


	public String getDrMemberName() {
		return drMemberName;
	}


	public void setDrMemberName(String drMemberName) {
		this.drMemberName = drMemberName;
	}


	public String getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}


	public String getAssignmentId() {
		return assignmentId;
	}


	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}


	public String getMandateRefNr() {
		return mandateRefNr;
	}


	public void setMandateRefNr(String mandateRefNr) {
		this.mandateRefNr = mandateRefNr;
	}


	public BigDecimal getNrDaysOutstanding() {
		return nrDaysOutstanding;
	}


	public void setNrDaysOutstanding(BigDecimal nrDaysOutstanding) {
		this.nrDaysOutstanding = nrDaysOutstanding;
	}


	public String getMandateReqTransId() {
		return mandateReqTransId;
	}


	public void setMandateReqTransId(String mandateReqTransId) {
		this.mandateReqTransId = mandateReqTransId;
	}


	public String getSuspensionId() {
		return suspensionId;
	}


	public void setSuspensionId(String suspensionId) {
		this.suspensionId = suspensionId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignmentId == null) ? 0 : assignmentId.hashCode());
		result = prime * result + ((drMemberName == null) ? 0 : drMemberName.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((mandateRefNr == null) ? 0 : mandateRefNr.hashCode());
		result = prime * result + ((mandateReqTransId == null) ? 0 : mandateReqTransId.hashCode());
		result = prime * result + ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result + ((nrDaysOutstanding == null) ? 0 : nrDaysOutstanding.hashCode());
		result = prime * result + ((suspensionId == null) ? 0 : suspensionId.hashCode());
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
		MndtOustandingSusResponseEntityModel other = (MndtOustandingSusResponseEntityModel) obj;
		if (assignmentId == null) {
			if (other.assignmentId != null)
				return false;
		} else if (!assignmentId.equals(other.assignmentId))
			return false;
		if (drMemberName == null) {
			if (other.drMemberName != null)
				return false;
		} else if (!drMemberName.equals(other.drMemberName))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (mandateRefNr == null) {
			if (other.mandateRefNr != null)
				return false;
		} else if (!mandateRefNr.equals(other.mandateRefNr))
			return false;
		if (mandateReqTransId == null) {
			if (other.mandateReqTransId != null)
				return false;
		} else if (!mandateReqTransId.equals(other.mandateReqTransId))
			return false;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		if (nrDaysOutstanding == null) {
			if (other.nrDaysOutstanding != null)
				return false;
		} else if (!nrDaysOutstanding.equals(other.nrDaysOutstanding))
			return false;
		if (suspensionId == null) {
			if (other.suspensionId != null)
				return false;
		} else if (!suspensionId.equals(other.suspensionId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MndtOustandingSusResponseEntityModel [drMemberName=" + drMemberName + ", memberNo=" + memberNo
				+ ", assignmentId=" + assignmentId + ", mandateRefNr=" + mandateRefNr + ", nrDaysOutstanding="
				+ nrDaysOutstanding + ", mandateReqTransId=" + mandateReqTransId + ", suspensionId=" + suspensionId
				+ ", createdDate=" + createdDate + "]";
	}
	
	
	

}
