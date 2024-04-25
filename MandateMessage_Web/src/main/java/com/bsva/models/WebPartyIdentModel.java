package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author salehas
 *
 */
public class WebPartyIdentModel implements Serializable
{
	
	private String mandateReqId;
    private String partyIdentTypeId;
    private String name;
    private String ctryOfResidence;
    private String contactName;
    private String phoneNr;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String id;
    
	public String getMandateReqId() {
		return mandateReqId;
	}
	public void setMandateReqId(String mandateReqId) {
		this.mandateReqId = mandateReqId;
	}
	public String getPartyIdentTypeId() {
		return partyIdentTypeId;
	}
	public void setPartyIdentTypeId(String partyIdentTypeId) {
		this.partyIdentTypeId = partyIdentTypeId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCtryOfResidence() {
		return ctryOfResidence;
	}
	public void setCtryOfResidence(String ctryOfResidence) {
		this.ctryOfResidence = ctryOfResidence;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getPhoneNr() {
		return phoneNr;
	}
	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((ctryOfResidence == null) ? 0 : ctryOfResidence.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((partyIdentTypeId == null) ? 0 : partyIdentTypeId.hashCode());
		result = prime * result + ((phoneNr == null) ? 0 : phoneNr.hashCode());
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
		WebPartyIdentModel other = (WebPartyIdentModel) obj;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
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
		if (ctryOfResidence == null) {
			if (other.ctryOfResidence != null)
				return false;
		} else if (!ctryOfResidence.equals(other.ctryOfResidence))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partyIdentTypeId == null) {
			if (other.partyIdentTypeId != null)
				return false;
		} else if (!partyIdentTypeId.equals(other.partyIdentTypeId))
			return false;
		if (phoneNr == null) {
			if (other.phoneNr != null)
				return false;
		} else if (!phoneNr.equals(other.phoneNr))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "WebPartyIdentModel [mandateReqId=" + mandateReqId
				+ ", partyIdentTypeId=" + partyIdentTypeId + ", name=" + name + ", ctryOfResidence="
				+ ctryOfResidence + ", contactName=" + contactName
				+ ", phoneNr=" + phoneNr + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", id=" + id + "]";
	}
	
	
	
	
}
