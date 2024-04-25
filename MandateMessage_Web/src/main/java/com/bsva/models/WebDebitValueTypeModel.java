package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @author salehas
 *
 */
public class WebDebitValueTypeModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String debValueTypeCode, debValueTypeDesc, activeInd;
	   private String createdBy;
	    private Date createdDate;
	    private String modifiedBy;
	    private Date modifiedDate;


	public WebDebitValueTypeModel() {
		// TODO Auto-generated constructor stub
	}


	public WebDebitValueTypeModel(String debValueTypeCode2) {
		// TODO Auto-generated constructor stub
	}


	public String getDebValueTypeCode() {
		return debValueTypeCode;
	}

	public void setDebValueTypeCode(String debValueTypeCode) {
		this.debValueTypeCode = debValueTypeCode;
	}

	public String getDebValueTypeDesc() {
		return debValueTypeDesc;
	}

	public void setDebValueTypeDesc(String debValueTypeDesc) {
		this.debValueTypeDesc = debValueTypeDesc;
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
		result = prime
				* result
				+ ((debValueTypeCode == null) ? 0 : debValueTypeCode.hashCode());
		result = prime
				* result
				+ ((debValueTypeDesc == null) ? 0 : debValueTypeDesc.hashCode());
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
		WebDebitValueTypeModel other = (WebDebitValueTypeModel) obj;
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
		if (debValueTypeCode == null) {
			if (other.debValueTypeCode != null)
				return false;
		} else if (!debValueTypeCode.equals(other.debValueTypeCode))
			return false;
		if (debValueTypeDesc == null) {
			if (other.debValueTypeDesc != null)
				return false;
		} else if (!debValueTypeDesc.equals(other.debValueTypeDesc))
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
		return debValueTypeDesc;
	}
	
	
	
	
	
	
}
