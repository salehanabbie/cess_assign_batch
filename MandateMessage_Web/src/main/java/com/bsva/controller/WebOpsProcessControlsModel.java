package com.bsva.controller;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WebOpsProcessControlsModel implements Serializable 
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private String instId;
	private Date processDate;
	private String nrOfDaysProc;
	private String pubHolProc;
	private BigDecimal maxNrRecords;
	private String activeInd;
	
	public WebOpsProcessControlsModel() 
	{
		super();
	}
	
	public String getInstId()
	{
		return instId;
	}
	
	public void setInstId(String instId) 
	{
		this.instId = instId;
	}
	
	public Date getProcessDate() 
	{
		return processDate;
	}
	
	public void setProcessDate(Date processDate) 
	{
		this.processDate = processDate;
	}
	
	public String getNrOfDaysProc() 
	{
		return nrOfDaysProc;
	}
	
	public void setNrOfDaysProc(String nrOfDaysProc) 
	{
		this.nrOfDaysProc = nrOfDaysProc;
	}
	
	public String getPubHolProc() 
	{
		return pubHolProc;
	}
	
	public void setPubHolProc(String pubHolProc)
	{
		this.pubHolProc = pubHolProc;
	}
	
	public BigDecimal getMaxNrRecords()
	{
		return maxNrRecords;
	}
	
	public void setMaxNrRecords(BigDecimal maxNrRecords) 
	{
		this.maxNrRecords = maxNrRecords;
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
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result
				+ ((maxNrRecords == null) ? 0 : maxNrRecords.hashCode());
		result = prime * result
				+ ((nrOfDaysProc == null) ? 0 : nrOfDaysProc.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((pubHolProc == null) ? 0 : pubHolProc.hashCode());
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
		WebOpsProcessControlsModel other = (WebOpsProcessControlsModel) obj;
		if (activeInd == null)
		{
			if (other.activeInd != null)
				return false;
		} 
		else if (!activeInd.equals(other.activeInd))
			return false;
		if (instId == null) 
		{
			if (other.instId != null)
				return false;
		}
		else if (!instId.equals(other.instId))
			return false;
		if (maxNrRecords == null) 
		{
			if (other.maxNrRecords != null)
				return false;
		} 
		else if (!maxNrRecords.equals(other.maxNrRecords))
			return false;
		if (nrOfDaysProc == null) 
		{
			if (other.nrOfDaysProc != null)
				return false;
		} 
		else if (!nrOfDaysProc.equals(other.nrOfDaysProc))
			return false;
		if (processDate == null)
		{
			if (other.processDate != null)
				return false;
		}
		else if (!processDate.equals(other.processDate))
			return false;
		if (pubHolProc == null)
		{
			if (other.pubHolProc != null)
				return false;
		} 
		else if (!pubHolProc.equals(other.pubHolProc))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "WebOpsProcessControlsModel [instId=" + instId
				+ ", processDate=" + processDate + ", nrOfDaysProc="
				+ nrOfDaysProc + ", pubHolProc=" + pubHolProc
				+ ", maxNrRecords=" + maxNrRecords + ", activeInd=" + activeInd
				+ "]";
	}
}
