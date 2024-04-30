package com.bsva.commons.model;

import java.io.Serializable;

public class MandatesSearchModel implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String mandateId;
	  private String name;
	  private String fiName;
	  private String partyId;
	  private String finId;
	public String getMandateId() {
		return mandateId;
	}
	public void setMandateId(String mandateId) {
		this.mandateId = mandateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFiName() {
		return fiName;
	}
	public void setFiName(String fiName) {
		this.fiName = fiName;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getFinId() {
		return finId;
	}
	public void setFinId(String finId) {
		this.finId = finId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fiName == null) ? 0 : fiName.hashCode());
		result = prime * result + ((finId == null) ? 0 : finId.hashCode());
		result = prime * result
				+ ((mandateId == null) ? 0 : mandateId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partyId == null) ? 0 : partyId.hashCode());
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
		MandatesSearchModel other = (MandatesSearchModel) obj;
		if (fiName == null) {
			if (other.fiName != null)
				return false;
		} else if (!fiName.equals(other.fiName))
			return false;
		if (finId == null) {
			if (other.finId != null)
				return false;
		} else if (!finId.equals(other.finId))
			return false;
		if (mandateId == null) {
			if (other.mandateId != null)
				return false;
		} else if (!mandateId.equals(other.mandateId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partyId == null) {
			if (other.partyId != null)
				return false;
		} else if (!partyId.equals(other.partyId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MandatesSearchModel [mandateId=" + mandateId + ", name=" + name
				+ ", fiName=" + fiName + ", partyId=" + partyId + ", finId="
				+ finId + "]";
	}
	  
	  

	  

}
