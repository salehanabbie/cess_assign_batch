package com.bsva.models;
import java.io.Serializable;
import java.math.BigDecimal;

public class WebComParamModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private BigDecimal compId;
	private String compName;
	private String compAbbrevName;
	private String registrationNr;
	private String activeInd;
	private String address1;
	private String address2;
	private String city;
	private String postCode;
	private String dialCode;
	private String telNr;
	private String faxCode;
	private String faxNr;
	private String email;
	private String contactName;
	private String lastFileNr;
	private String transamissionInd;
	private String bicCode;
	private String achId;
	
	public BigDecimal getCompId() 
	{
		return compId;
	}
	
	public void setCompId(BigDecimal compId) 
	{
		this.compId = compId;
	}
	
	public String getCompName() 
	{
		return compName;
	}
	
	public void setCompName(String compName) 
	{
		this.compName = compName;
	}
	
	public String getCompAbbrevName() 
	{
		return compAbbrevName;
	}
	
	public void setCompAbbrevName(String compAbbrevName) 
	{
		this.compAbbrevName = compAbbrevName;
	}
	
	public String getRegistrationNr() 
	{
		return registrationNr;
	}
	
	public void setRegistrationNr(String registrationNr) 
	{
		this.registrationNr = registrationNr;
	}
	
	public String getActiveInd() 
	{
		return activeInd;
	}
	
	public void setActiveInd(String activeInd) 
	{
		this.activeInd = activeInd;
	}
	
	public String getAddress1()
	{
		return address1;
	}
	
	public void setAddress1(String address1) 
	{
		this.address1 = address1;
	}
	
	public String getAddress2() 
	{
		return address2;
	}
	
	public void setAddress2(String address2) 
	{
		this.address2 = address2;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city) 
	{
		this.city = city;
	}
	
	public String getPostCode()
	{
		return postCode;
	}
	
	public void setPostCode(String postCode) 
	{
		this.postCode = postCode;
	}
	
	public String getDialCode()
	{
		return dialCode;
	}
	
	public void setDialCode(String dialCode)
	{
		this.dialCode = dialCode;
	}
	
	public String getTelNr() 
	{
		return telNr;
	}
	
	public void setTelNr(String telNr) 
	{
		this.telNr = telNr;
	}
	
	public String getFaxCode() 
	{
		return faxCode;
	}
	
	public void setFaxCode(String faxCode) 
	{
		this.faxCode = faxCode;
	}
	
	public String getFaxNr() 
	{
		return faxNr;
	}
	
	public void setFaxNr(String faxNr) 
	{
		this.faxNr = faxNr;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getContactName() 
	{
		return contactName;
	}
	
	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}
	
	public String getLastFileNr()
	{
		return lastFileNr;
	}
	
	public void setLastFileNr(String lastFileNr)
	{
		this.lastFileNr = lastFileNr;
	}
	
	public String getTransamissionInd() 
	{
		return transamissionInd;
	}
	
	public void setTransamissionInd(String transamissionInd)
	{
		this.transamissionInd = transamissionInd;
	}
	
	public String getBicCode() 
	{
		return bicCode;
	}
	
	public void setBicCode(String bicCode)
	{
		this.bicCode = bicCode;
	}
	
	public String getAchId()
	{
		return achId;
	}
	
	public void setAchId(String achId)
	{
		this.achId = achId;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achId == null) ? 0 : achId.hashCode());
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((bicCode == null) ? 0 : bicCode.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((compAbbrevName == null) ? 0 : compAbbrevName.hashCode());
		result = prime * result + ((compId == null) ? 0 : compId.hashCode());
		result = prime * result
				+ ((compName == null) ? 0 : compName.hashCode());
		result = prime * result
				+ ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result
				+ ((dialCode == null) ? 0 : dialCode.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((faxCode == null) ? 0 : faxCode.hashCode());
		result = prime * result + ((faxNr == null) ? 0 : faxNr.hashCode());
		result = prime * result
				+ ((lastFileNr == null) ? 0 : lastFileNr.hashCode());
		result = prime * result
				+ ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result
				+ ((registrationNr == null) ? 0 : registrationNr.hashCode());
		result = prime * result + ((telNr == null) ? 0 : telNr.hashCode());
		result = prime
				* result
				+ ((transamissionInd == null) ? 0 : transamissionInd.hashCode());
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
		WebComParamModel other = (WebComParamModel) obj;
		if (achId == null) {
			if (other.achId != null)
				return false;
		} else if (!achId.equals(other.achId))
			return false;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (bicCode == null) {
			if (other.bicCode != null)
				return false;
		} else if (!bicCode.equals(other.bicCode))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (compAbbrevName == null) {
			if (other.compAbbrevName != null)
				return false;
		} else if (!compAbbrevName.equals(other.compAbbrevName))
			return false;
		if (compId == null) {
			if (other.compId != null)
				return false;
		} else if (!compId.equals(other.compId))
			return false;
		if (compName == null) {
			if (other.compName != null)
				return false;
		} else if (!compName.equals(other.compName))
			return false;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
			return false;
		if (dialCode == null) {
			if (other.dialCode != null)
				return false;
		} else if (!dialCode.equals(other.dialCode))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (faxCode == null) {
			if (other.faxCode != null)
				return false;
		} else if (!faxCode.equals(other.faxCode))
			return false;
		if (faxNr == null) {
			if (other.faxNr != null)
				return false;
		} else if (!faxNr.equals(other.faxNr))
			return false;
		if (lastFileNr == null) {
			if (other.lastFileNr != null)
				return false;
		} else if (!lastFileNr.equals(other.lastFileNr))
			return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		if (registrationNr == null) {
			if (other.registrationNr != null)
				return false;
		} else if (!registrationNr.equals(other.registrationNr))
			return false;
		if (telNr == null) {
			if (other.telNr != null)
				return false;
		} else if (!telNr.equals(other.telNr))
			return false;
		if (transamissionInd == null) {
			if (other.transamissionInd != null)
				return false;
		} else if (!transamissionInd.equals(other.transamissionInd))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "ComParamModel [compId=" + compId + ", compName=" + compName
				+ ", compAbbrevName=" + compAbbrevName + ", registrationNr="
				+ registrationNr + ", activeInd=" + activeInd + ", address1="
				+ address1 + ", address2=" + address2 + ", city=" + city
				+ ", postCode=" + postCode + ", dialCode=" + dialCode
				+ ", telNr=" + telNr + ", faxCode=" + faxCode + ", faxNr="
				+ faxNr + ", email=" + email + ", contactName=" + contactName
				+ ", lastFileNr=" + lastFileNr + ", transamissionInd="
				+ transamissionInd + ", bicCode=" + bicCode + ", achId="
				+ achId + "]";
	}
}
