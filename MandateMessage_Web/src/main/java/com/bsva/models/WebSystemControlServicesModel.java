package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class WebSystemControlServicesModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serviceIdIn;
    private String serviceIdInDesc;
    private String serviceIdOut;
    private String serviceIdOutDesc;
    private BigDecimal recordId;
    private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
    public WebSystemControlServicesModel (String serviceIdIn)
    {
    	this.serviceIdIn = serviceIdIn;
    }
    
    public WebSystemControlServicesModel()
    {
    	
    }

	public String getServiceIdIn() {
		return serviceIdIn;
	}

	public void setServiceIdIn(String serviceIdIn) {
		this.serviceIdIn = serviceIdIn;
	}

	public String getServiceIdInDesc() {
		return serviceIdInDesc;
	}

	public void setServiceIdInDesc(String serviceIdInDesc) {
		this.serviceIdInDesc = serviceIdInDesc;
	}

	public String getServiceIdOut() {
		return serviceIdOut;
	}

	public void setServiceIdOut(String serviceIdOut) {
		this.serviceIdOut = serviceIdOut;
	}

	public String getServiceIdOutDesc() {
		return serviceIdOutDesc;
	}

	public void setServiceIdOutDesc(String serviceIdOutDesc) {
		this.serviceIdOutDesc = serviceIdOutDesc;
	}

	public BigDecimal getRecordId() {
		return recordId;
	}

	public void setRecordId(BigDecimal recordId) {
		this.recordId = recordId;
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
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result
				+ ((serviceIdIn == null) ? 0 : serviceIdIn.hashCode());
		result = prime * result
				+ ((serviceIdInDesc == null) ? 0 : serviceIdInDesc.hashCode());
		result = prime * result
				+ ((serviceIdOut == null) ? 0 : serviceIdOut.hashCode());
		result = prime
				* result
				+ ((serviceIdOutDesc == null) ? 0 : serviceIdOutDesc.hashCode());
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
		WebSystemControlServicesModel other = (WebSystemControlServicesModel) obj;
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
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		if (serviceIdIn == null) {
			if (other.serviceIdIn != null)
				return false;
		} else if (!serviceIdIn.equals(other.serviceIdIn))
			return false;
		if (serviceIdInDesc == null) {
			if (other.serviceIdInDesc != null)
				return false;
		} else if (!serviceIdInDesc.equals(other.serviceIdInDesc))
			return false;
		if (serviceIdOut == null) {
			if (other.serviceIdOut != null)
				return false;
		} else if (!serviceIdOut.equals(other.serviceIdOut))
			return false;
		if (serviceIdOutDesc == null) {
			if (other.serviceIdOutDesc != null)
				return false;
		} else if (!serviceIdOutDesc.equals(other.serviceIdOutDesc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return serviceIdOut;
				

	}
    
    
	

}
