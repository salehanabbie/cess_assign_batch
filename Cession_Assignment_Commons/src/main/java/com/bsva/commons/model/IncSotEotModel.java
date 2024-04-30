package com.bsva.commons.model;
import java.io.Serializable;

public class IncSotEotModel implements Serializable
{
	 private String sotIn;
	 private String eotIn;
	 private String serviceId;
	 private String instId;
	 
	 public IncSotEotModel ()
	 {
		 super();
	 }

	public String getSotIn()
	{
		return sotIn;
	}

	public void setSotIn(String sotIn) 
	{
		this.sotIn = sotIn;
	}

	public String getEotIn()
	{
		return eotIn;
	}

	public void setEotIn(String eotIn)
	{
		this.eotIn = eotIn;
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
		result = prime * result + ((eotIn == null) ? 0 : eotIn.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((sotIn == null) ? 0 : sotIn.hashCode());
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
		IncSotEotModel other = (IncSotEotModel) obj;
		if (eotIn == null) 
		{
			if (other.eotIn != null)
				return false;
		} 
		else if (!eotIn.equals(other.eotIn))
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
		if (sotIn == null)
		{
			if (other.sotIn != null)
				return false;
		} 
		else if (!sotIn.equals(other.sotIn))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return serviceId;
	} 
}
