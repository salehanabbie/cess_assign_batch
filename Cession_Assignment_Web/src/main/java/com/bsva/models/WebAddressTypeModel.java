package com.bsva.models;
import java.io.Serializable;

/**
 * @author Saleha Saib
 *
 */
public class WebAddressTypeModel implements Serializable 
{
	String addressType, addressTypeDesc, activeInd;

	public WebAddressTypeModel() 
	{
	}
	
	public WebAddressTypeModel(String addressType)
	{	
		this.addressType=addressType;
	}

	public String getAddressType() 
	{
		return addressType;
	}

	public void setAddressType(String addressType) 
	{
		this.addressType = addressType;
	}

	public String getAddressTypeDesc() 
	{
		return addressTypeDesc;
	}

	public void setAddressTypeDesc(String addressTypeDesc) 
	{
		this.addressTypeDesc = addressTypeDesc;
	}

	public String getActiveInd()
	{
		return activeInd;
	}

	public void setActiveInd(String activeInd)
	{
		this.activeInd = activeInd;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((addressType == null) ? 0 : addressType.hashCode());
		result = prime * result
				+ ((addressTypeDesc == null) ? 0 : addressTypeDesc.hashCode());
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
		WebAddressTypeModel other = (WebAddressTypeModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (addressType == null) {
			if (other.addressType != null)
				return false;
		} else if (!addressType.equals(other.addressType))
			return false;
		if (addressTypeDesc == null) {
			if (other.addressTypeDesc != null)
				return false;
		} else if (!addressTypeDesc.equals(other.addressTypeDesc))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return addressType;
	}

}
