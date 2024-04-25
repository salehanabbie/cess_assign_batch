package com.bsva.models;
import java.io.Serializable;

public class WebArcPartyIdentIdModel implements Serializable 
{
	private String mandateReqId;
    private String partyIdentTypeId;

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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime
				* result
				+ ((partyIdentTypeId == null) ? 0 : partyIdentTypeId.hashCode());
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
		WebArcPartyIdentIdModel other = (WebArcPartyIdentIdModel) obj;
		if (mandateReqId == null) {
			if (other.mandateReqId != null)
				return false;
		} else if (!mandateReqId.equals(other.mandateReqId))
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
		return "WebArcPartyIdModel [mandateReqId=" + mandateReqId
				+ ", partyIdentTypeId=" + partyIdentTypeId + "]";
	}

}
