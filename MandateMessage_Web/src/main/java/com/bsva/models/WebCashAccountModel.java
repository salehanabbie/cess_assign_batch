package com.bsva.models;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author salehas
 *
 */
public class WebCashAccountModel implements Serializable
{
	private String mandateReqId;
	private String partyIdentTypeId;
	private String accountNumber;
    private String accountName;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String accountType;
    private String currency;
    
	public String getMandateReqId() 
	{
		return mandateReqId;
	}
	
	public void setMandateReqId(String mandateReqId)
	{
		this.mandateReqId = mandateReqId;
	}
	
	public String getPartyIdentTypeId() 
	{
		return partyIdentTypeId;
	}
	
	public void setPartyIdentTypeId(String partyIdentTypeId) 
	{
		this.partyIdentTypeId = partyIdentTypeId;
	}
	
	public String getAccountNumber() 
	{
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) 
	{
		this.accountNumber = accountNumber;
	}
	
	public String getAccountName() 
	{
		return accountName;
	}
	
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
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
	
	public String getAccountType() 
	{
		return accountType;
	}
	
	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}
	
	public String getCurrency() 
	{
		return currency;
	}
	
	public void setCurrency(String currency) 
	{
		this.currency = currency;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime
				* result
				+ ((partyIdentTypeId == null) ? 0 : partyIdentTypeId.hashCode());
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
		WebCashAccountModel other = (WebCashAccountModel) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
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
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
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
		if (partyIdentTypeId == null) {
			if (other.partyIdentTypeId != null)
				return false;
		} else if (!partyIdentTypeId.equals(other.partyIdentTypeId))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "WebCashAccountModel [mandateReqId=" + mandateReqId
				+ ", partyIdentTypeId=" + partyIdentTypeId + ", accountNumber="
				+ accountNumber + ", accountName=" + accountName
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", accountType=" + accountType + ", currency="
				+ currency + "]";
	}   	
}
