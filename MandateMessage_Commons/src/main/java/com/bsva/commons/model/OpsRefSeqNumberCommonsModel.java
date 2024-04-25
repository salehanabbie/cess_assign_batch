package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;

public class OpsRefSeqNumberCommonsModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String lastSeqNr;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String lastFileNr;
	  private String serviceId;
	  private String memberNo;
	  
	public OpsRefSeqNumberCommonsModel()
	{
		super();
	}

	public String getLastSeqNr() {
		return lastSeqNr;
	}

	public void setLastSeqNr(String lastSeqNr) {
		this.lastSeqNr = lastSeqNr;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getLastFileNr() {
		return lastFileNr;
	}

	public void setLastFileNr(String lastFileNr) {
		this.lastFileNr = lastFileNr;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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
				+ ((lastFileNr == null) ? 0 : lastFileNr.hashCode());
		result = prime * result
				+ ((lastSeqNr == null) ? 0 : lastSeqNr.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
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
		OpsRefSeqNumberCommonsModel other = (OpsRefSeqNumberCommonsModel) obj;
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
		if (lastFileNr == null) {
			if (other.lastFileNr != null)
				return false;
		} else if (!lastFileNr.equals(other.lastFileNr))
			return false;
		if (lastSeqNr == null) {
			if (other.lastSeqNr != null)
				return false;
		} else if (!lastSeqNr.equals(other.lastSeqNr))
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
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpsRefSeqNumberCommonsModel [lastSeqNr=" + lastSeqNr
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", lastFileNr=" + lastFileNr + ", serviceId="
				+ serviceId + ", memberNo=" + memberNo + "]";
	}

  
}
