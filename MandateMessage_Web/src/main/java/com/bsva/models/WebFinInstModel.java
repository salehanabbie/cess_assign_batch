package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author salehas
 *
 */
public class WebFinInstModel implements Serializable{

	private String mandateReqId;
	private String finInstTypeId;
	private String fiName;
	private String branchId;
	private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
    
	public String getMandateReqId() {
		return mandateReqId;
	}
	public void setMandateReqId(String mandateReqId) {
		this.mandateReqId = mandateReqId;
	}
	public String getFinInstTypeId() {
		return finInstTypeId;
	}
	public void setFinInstTypeId(String finInstTypeId) {
		this.finInstTypeId = finInstTypeId;
	}
	public String getFiName() {
		return fiName;
	}
	public void setFiName(String fiName) {
		this.fiName = fiName;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
				+ ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((fiName == null) ? 0 : fiName.hashCode());
		result = prime * result
				+ ((finInstTypeId == null) ? 0 : finInstTypeId.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
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
		WebFinInstModel other = (WebFinInstModel) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
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
		if (fiName == null) {
			if (other.fiName != null)
				return false;
		} else if (!fiName.equals(other.fiName))
			return false;
		if (finInstTypeId == null) {
			if (other.finInstTypeId != null)
				return false;
		} else if (!finInstTypeId.equals(other.finInstTypeId))
			return false;
		if (mandateReqId == null) {
			if (other.mandateReqId != null)
				return false;
		} else if (!mandateReqId.equals(other.mandateReqId))
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
		return true;
	}
	@Override
	public String toString() {
		return "WebFinInstModel [mandateReqId=" + mandateReqId
				+ ", finInstTypeId=" + finInstTypeId + ", fiName=" + fiName
				+ ", branchId=" + branchId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
    
    
    
	
}
