package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;



public class ProcessStatusModel implements Serializable

{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String statusDescription;
	private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
    
	public ProcessStatusModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProcessStatusModel(String status2) {
	
		
	}
	


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusDescription() {
		return statusDescription;
	}


	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
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
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((statusDescription == null) ? 0 : statusDescription
						.hashCode());
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
		ProcessStatusModel other = (ProcessStatusModel) obj;
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
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusDescription == null) {
			if (other.statusDescription != null)
				return false;
		} else if (!statusDescription.equals(other.statusDescription))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ProcessStatusModel [status=" + status + ", statusDescription="
				+ statusDescription + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
	
	
    
    
    
    
    

	
}
