package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @author salehas
 *
 */
public class WebLocalInstrumentCodesModel implements Serializable
{
	String localInstrumentCode;
	String instCodeDescription;
	String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
	
	public WebLocalInstrumentCodesModel(String localInstrumentCode)
	{
		this.localInstrumentCode = localInstrumentCode;
	}
	
	public WebLocalInstrumentCodesModel()
	{
		
	}
	


	public String getLocalInstrumentCode() {
		return localInstrumentCode;
	}
	public void setLocalInstrumentCode(String localInstrumentCode) {
		this.localInstrumentCode = localInstrumentCode;
	}
	public String getInstCodeDescription() {
		return instCodeDescription;
	}
	public void setInstCodeDescription(String instCodeDescription) {
		this.instCodeDescription = instCodeDescription;
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
				+ ((instCodeDescription == null) ? 0 : instCodeDescription
						.hashCode());
		result = prime
				* result
				+ ((localInstrumentCode == null) ? 0 : localInstrumentCode
						.hashCode());
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
		WebLocalInstrumentCodesModel other = (WebLocalInstrumentCodesModel) obj;
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
		if (instCodeDescription == null) {
			if (other.instCodeDescription != null)
				return false;
		} else if (!instCodeDescription.equals(other.instCodeDescription))
			return false;
		if (localInstrumentCode == null) {
			if (other.localInstrumentCode != null)
				return false;
		} else if (!localInstrumentCode.equals(other.localInstrumentCode))
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
		return  localInstrumentCode;
	}


	
	
	
}
