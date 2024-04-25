package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

public class PublicHolidayDateModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Date pubHolDate;
	
	public PublicHolidayDateModel() 
	{
		super();
	}

	public Date getPubHolDate() 
	{
		return pubHolDate;
	}

	public void setPubHolDate(Date pubHolDate)
	{
		this.pubHolDate = pubHolDate;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pubHolDate == null) ? 0 : pubHolDate.hashCode());
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
		PublicHolidayDateModel other = (PublicHolidayDateModel) obj;
		if (pubHolDate == null) {
			if (other.pubHolDate != null)
				return false;
		} else if (!pubHolDate.equals(other.pubHolDate))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "PublicHolidayDateModel [pubHolDate=" + pubHolDate + "]";
	}
	
	
}
