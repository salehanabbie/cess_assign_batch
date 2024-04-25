package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;

public class StatusReasonCodesModel implements Serializable{
	
	private String statusReasonCode;
    private String statusReasonCodeDescription;
    private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
    
	public String getStatusReasonCode() {
		return statusReasonCode;
	}
	public void setStatusReasonCode(String statusReasonCode) {
		this.statusReasonCode = statusReasonCode;
	}
	public String getStatusReasonCodeDescription() {
		return statusReasonCodeDescription;
	}
	public void setStatusReasonCodeDescription(String statusReasonCodeDescription) {
		this.statusReasonCodeDescription = statusReasonCodeDescription;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime
				* result
				+ ((statusReasonCode == null) ? 0 : statusReasonCode.hashCode());
		result = prime
				* result
				+ ((statusReasonCodeDescription == null) ? 0
						: statusReasonCodeDescription.hashCode());
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
		StatusReasonCodesModel other = (StatusReasonCodesModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (statusReasonCode == null) {
			if (other.statusReasonCode != null)
				return false;
		} else if (!statusReasonCode.equals(other.statusReasonCode))
			return false;
		if (statusReasonCodeDescription == null) {
			if (other.statusReasonCodeDescription != null)
				return false;
		} else if (!statusReasonCodeDescription
				.equals(other.statusReasonCodeDescription))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "StatusReasonCodesModel [statusReasonCode=" + statusReasonCode
				+ ", statusReasonCodeDescription="
				+ statusReasonCodeDescription + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ "]";
	}
	
	
}
