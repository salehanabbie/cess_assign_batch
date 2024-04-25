package com.bsva.commons.model;
import java.io.Serializable;

public class OutSotEotModel implements Serializable
{
	private String sotOut;
	private String eotOut;
	private String serviceId;
	private String instId;
	
	public OutSotEotModel()
	{
		super();
	}

	public String getSotOut() 
	{
		return sotOut;
	}

	public void setSotOut(String sotOut)
	{
		this.sotOut = sotOut;
	}

	public String getEotOut() 
	{
		return eotOut;
	}

	public void setEotOut(String eotOut) 
	{
		this.eotOut = eotOut;
	}

	public String getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

	public String getInstId() 
	{
		return instId;
	}

	public void setInstId(String instId) 
	{
		this.instId = instId;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eotOut == null) ? 0 : eotOut.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((sotOut == null) ? 0 : sotOut.hashCode());
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
		OutSotEotModel other = (OutSotEotModel) obj;
		if (eotOut == null) 
		{
			if (other.eotOut != null)
				return false;
		} 
		else if (!eotOut.equals(other.eotOut))
			return false;
		if (instId == null) 
		{
			if (other.instId != null)
				return false;
		} 
		else if (!instId.equals(other.instId))
			return false;
		if (serviceId == null)
		{
			if (other.serviceId != null)
				return false;
		} 
		else if (!serviceId.equals(other.serviceId))
			return false;
		if (sotOut == null) 
		{
			if (other.sotOut != null)
				return false;
		} 
		else if (!sotOut.equals(other.sotOut))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return serviceId;
	}
}
