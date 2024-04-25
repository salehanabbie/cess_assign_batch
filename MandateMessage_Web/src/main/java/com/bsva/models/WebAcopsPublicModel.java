package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

public class WebAcopsPublicModel  implements Serializable
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
    private Date pubHolDate;
    private String pubHolidayDesc;
    private String activeInd;
    
	public WebAcopsPublicModel()
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

	public String getPubHolidayDesc() 
	{
		return pubHolidayDesc;
	}

	public void setPubHolidayDesc(String pubHolidayDesc) 
	{
		this.pubHolidayDesc = pubHolidayDesc;
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
				+ ((pubHolDate == null) ? 0 : pubHolDate.hashCode());
		result = prime * result
				+ ((pubHolidayDesc == null) ? 0 : pubHolidayDesc.hashCode());
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
		WebAcopsPublicModel other = (WebAcopsPublicModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (pubHolDate == null) {
			if (other.pubHolDate != null)
				return false;
		} else if (!pubHolDate.equals(other.pubHolDate))
			return false;
		if (pubHolidayDesc == null) {
			if (other.pubHolidayDesc != null)
				return false;
		} else if (!pubHolidayDesc.equals(other.pubHolidayDesc))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "WebAcopsPublicModel [pubHolDate=" + pubHolDate
				+ ", pubHolidayDesc=" + pubHolidayDesc + ", activeInd="
				+ activeInd + "]";
	}
    
}
