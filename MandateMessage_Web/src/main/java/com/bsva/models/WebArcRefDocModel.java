package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */
public class WebArcRefDocModel implements Serializable 
{
    private String mandateReqId;
    private String code;
    private String refDocNumber;
    private Date relatedDate;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

	public String getMandateReqId()
	{
		return mandateReqId;
	}

	public void setMandateReqId(String mandateReqId) 
	{
		this.mandateReqId = mandateReqId;
	}

	public String getCode()
{
		return code;
	}

	public void setCode(String code) 
	{
		this.code = code;
	}

	public String getRefDocNumber()
{
		return refDocNumber;
	}

	public void setRefDocNumber(String refDocNumber) 
	{
		this.refDocNumber = refDocNumber;
	}

	public Date getRelatedDate()
	{
		return relatedDate;
	}

	public void setRelatedDate(Date relatedDate) 
	{
		this.relatedDate = relatedDate;
	}

	public String getCreatedBy() 
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getModifiedBy()
	{
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) 
	{
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate()
	{
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate = modifiedDate;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((refDocNumber == null) ? 0 : refDocNumber.hashCode());
		result = prime * result
				+ ((relatedDate == null) ? 0 : relatedDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebArcRefDocModel other = (WebArcRefDocModel) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		if (refDocNumber == null) {
			if (other.refDocNumber != null)
				return false;
		} else if (!refDocNumber.equals(other.refDocNumber))
			return false;
		if (relatedDate == null) {
			if (other.relatedDate != null)
				return false;
		} else if (!relatedDate.equals(other.relatedDate))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "WebArcRefDocModel [mandateReqId=" + mandateReqId + ", code="
				+ code + ", refDocNumber=" + refDocNumber + ", relatedDate="
				+ relatedDate + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
}
