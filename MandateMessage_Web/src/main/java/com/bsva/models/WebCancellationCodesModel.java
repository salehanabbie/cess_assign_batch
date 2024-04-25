package com.bsva.models;
import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author NhlanhlaM
 *
 */
public class WebCancellationCodesModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String cancellationCode;
    private String cancellationCodeDescription;
    private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
    public WebCancellationCodesModel (String cancellationCode)
    {
    	this.cancellationCode = cancellationCode;
    }
    
    public WebCancellationCodesModel()
    { 	
    }
    
	public String getCancellationCode()
	{
		return cancellationCode;
	}
	
	public void setCancellationCode(String cancellationCode) 
	{
		this.cancellationCode = cancellationCode;
	}
	
	public String getCancellationCodeDescription() 
	{
		return cancellationCodeDescription;
	}
	
	public void setCancellationCodeDescription(String cancellationCodeDescription) 
	{
		this.cancellationCodeDescription = cancellationCodeDescription;
	}
	
	public String getActiveInd()
	{
		return activeInd;
	}
	
	public void setActiveInd(String activeInd)
	{
		this.activeInd = activeInd;
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
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime
				* result
				+ ((cancellationCode == null) ? 0 : cancellationCode.hashCode());
		result = prime
				* result
				+ ((cancellationCodeDescription == null) ? 0
						: cancellationCodeDescription.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
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
		WebCancellationCodesModel other = (WebCancellationCodesModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (cancellationCode == null) {
			if (other.cancellationCode != null)
				return false;
		} else if (!cancellationCode.equals(other.cancellationCode))
			return false;
		if (cancellationCodeDescription == null) {
			if (other.cancellationCodeDescription != null)
				return false;
		} else if (!cancellationCodeDescription
				.equals(other.cancellationCodeDescription))
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
		return true;
	}

	@Override
	public String toString() 
	{
		return "WebCancellationCodesModel [cancellationCode="
				+ cancellationCode + ", cancellationCodeDescription="
				+ cancellationCodeDescription + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ "]";
	}
}
