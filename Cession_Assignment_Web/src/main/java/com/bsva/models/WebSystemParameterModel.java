package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class WebSystemParameterModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sysName;
    private String defCurr;
    private Short inactiveDuration;   
    private String sysType;
    private String activeInd;
    private String sodRunInd;
    private String eodRunInd;
    private String cisDwnldInd;
    private Date cisDwnldDate;
    private Date processDate;
    private String nextMondayHolInd;
    private String easterDaysInd;
    private BigInteger archivePeriod;
    private String inBalanceInd;  
    private String forcecloseReason;
    private String sysCloseRunInd;
    private Short responsePeriod;  
    private String strCisDownloadDate;
    private String strProcessDate;
    private Short iamPort;
    private BigInteger itemLimit;
    
    
    public WebSystemParameterModel (String sysName)
    {
    	this.sysName = sysName;
    }
    
    public WebSystemParameterModel()
    {
    	
    }

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getDefCurr() {
		return defCurr;
	}

	public void setDefCurr(String defCurr) {
		this.defCurr = defCurr;
	}

	public Short getInactiveDuration() {
		return inactiveDuration;
	}

	public void setInactiveDuration(Short inactiveDuration) {
		this.inactiveDuration = inactiveDuration;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public String getSodRunInd() {
		return sodRunInd;
	}

	public void setSodRunInd(String sodRunInd) {
		this.sodRunInd = sodRunInd;
	}

	public String getEodRunInd() {
		return eodRunInd;
	}

	public void setEodRunInd(String eodRunInd) {
		this.eodRunInd = eodRunInd;
	}

	public String getCisDwnldInd() {
		return cisDwnldInd;
	}

	public void setCisDwnldInd(String cisDwnldInd) {
		this.cisDwnldInd = cisDwnldInd;
	}

	public Date getCisDwnldDate() {
		return cisDwnldDate;
	}

	public void setCisDwnldDate(Date cisDwnldDate) {
		this.cisDwnldDate = cisDwnldDate;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getNextMondayHolInd() {
		return nextMondayHolInd;
	}

	public void setNextMondayHolInd(String nextMondayHolInd) {
		this.nextMondayHolInd = nextMondayHolInd;
	}

	public String getEasterDaysInd() {
		return easterDaysInd;
	}

	public void setEasterDaysInd(String easterDaysInd) {
		this.easterDaysInd = easterDaysInd;
	}

	public BigInteger getArchivePeriod() {
		return archivePeriod;
	}

	public void setArchivePeriod(BigInteger archivePeriod) {
		this.archivePeriod = archivePeriod;
	}

	public String getInBalanceInd() {
		return inBalanceInd;
	}

	public void setInBalanceInd(String inBalanceInd) {
		this.inBalanceInd = inBalanceInd;
	}

	public String getForcecloseReason() {
		return forcecloseReason;
	}

	public void setForcecloseReason(String forcecloseReason) {
		this.forcecloseReason = forcecloseReason;
	}

	public String getSysCloseRunInd() {
		return sysCloseRunInd;
	}

	public void setSysCloseRunInd(String sysCloseRunInd) {
		this.sysCloseRunInd = sysCloseRunInd;
	}

	public Short getResponsePeriod() {
		return responsePeriod;
	}

	public void setResponsePeriod(Short responsePeriod) {
		this.responsePeriod = responsePeriod;
	}

	public String getStrCisDownloadDate() {
		return strCisDownloadDate;
	}

	public void setStrCisDownloadDate(String strCisDownloadDate) {
		this.strCisDownloadDate = strCisDownloadDate;
	}

	public String getStrProcessDate() {
		return strProcessDate;
	}

	public void setStrProcessDate(String strProcessDate) {
		this.strProcessDate = strProcessDate;
	}

	public Short getIamPort() {
		return iamPort;
	}

	public void setIamPort(Short iamPort) {
		this.iamPort = iamPort;
	}
	
	public BigInteger getItemLimit() {
		 return itemLimit;
	}
	public void setItemLimit(BigInteger itemLimit) {
		this.itemLimit = itemLimit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result + ((archivePeriod == null) ? 0 : archivePeriod.hashCode());
		result = prime * result + ((cisDwnldDate == null) ? 0 : cisDwnldDate.hashCode());
		result = prime * result + ((cisDwnldInd == null) ? 0 : cisDwnldInd.hashCode());
		result = prime * result + ((defCurr == null) ? 0 : defCurr.hashCode());
		result = prime * result + ((easterDaysInd == null) ? 0 : easterDaysInd.hashCode());
		result = prime * result + ((eodRunInd == null) ? 0 : eodRunInd.hashCode());
		result = prime * result + ((forcecloseReason == null) ? 0 : forcecloseReason.hashCode());
		result = prime * result + ((iamPort == null) ? 0 : iamPort.hashCode());
		result = prime * result + ((inBalanceInd == null) ? 0 : inBalanceInd.hashCode());
		result = prime * result + ((inactiveDuration == null) ? 0 : inactiveDuration.hashCode());
		result = prime * result + ((nextMondayHolInd == null) ? 0 : nextMondayHolInd.hashCode());
		result = prime * result + ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result + ((responsePeriod == null) ? 0 : responsePeriod.hashCode());
		result = prime * result + ((sodRunInd == null) ? 0 : sodRunInd.hashCode());
		result = prime * result + ((strCisDownloadDate == null) ? 0 : strCisDownloadDate.hashCode());
		result = prime * result + ((strProcessDate == null) ? 0 : strProcessDate.hashCode());
		result = prime * result + ((sysCloseRunInd == null) ? 0 : sysCloseRunInd.hashCode());
		result = prime * result + ((sysName == null) ? 0 : sysName.hashCode());
		result = prime * result + ((sysType == null) ? 0 : sysType.hashCode());
		result = prime * result + ((itemLimit == null ? 0 : itemLimit.hashCode()));
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
		WebSystemParameterModel other = (WebSystemParameterModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (archivePeriod == null) {
			if (other.archivePeriod != null)
				return false;
		} else if (!archivePeriod.equals(other.archivePeriod))
			return false;
		if (cisDwnldDate == null) {
			if (other.cisDwnldDate != null)
				return false;
		} else if (!cisDwnldDate.equals(other.cisDwnldDate))
			return false;
		if (cisDwnldInd == null) {
			if (other.cisDwnldInd != null)
				return false;
		} else if (!cisDwnldInd.equals(other.cisDwnldInd))
			return false;
		if (defCurr == null) {
			if (other.defCurr != null)
				return false;
		} else if (!defCurr.equals(other.defCurr))
			return false;
		if (easterDaysInd == null) {
			if (other.easterDaysInd != null)
				return false;
		} else if (!easterDaysInd.equals(other.easterDaysInd))
			return false;
		if (eodRunInd == null) {
			if (other.eodRunInd != null)
				return false;
		} else if (!eodRunInd.equals(other.eodRunInd))
			return false;
		if (forcecloseReason == null) {
			if (other.forcecloseReason != null)
				return false;
		} else if (!forcecloseReason.equals(other.forcecloseReason))
			return false;
		if (iamPort == null) {
			if (other.iamPort != null)
				return false;
		} else if (!iamPort.equals(other.iamPort))
			return false;
		if (inBalanceInd == null) {
			if (other.inBalanceInd != null)
				return false;
		} else if (!inBalanceInd.equals(other.inBalanceInd))
			return false;
		if (inactiveDuration == null) {
			if (other.inactiveDuration != null)
				return false;
		} else if (!inactiveDuration.equals(other.inactiveDuration))
			return false;
		if (nextMondayHolInd == null) {
			if (other.nextMondayHolInd != null)
				return false;
		} else if (!nextMondayHolInd.equals(other.nextMondayHolInd))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (responsePeriod == null) {
			if (other.responsePeriod != null)
				return false;
		} else if (!responsePeriod.equals(other.responsePeriod))
			return false;
		if (sodRunInd == null) {
			if (other.sodRunInd != null)
				return false;
		} else if (!sodRunInd.equals(other.sodRunInd))
			return false;
		if (strCisDownloadDate == null) {
			if (other.strCisDownloadDate != null)
				return false;
		} else if (!strCisDownloadDate.equals(other.strCisDownloadDate))
			return false;
		if (strProcessDate == null) {
			if (other.strProcessDate != null)
				return false;
		} else if (!strProcessDate.equals(other.strProcessDate))
			return false;
		if (sysCloseRunInd == null) {
			if (other.sysCloseRunInd != null)
				return false;
		} else if (!sysCloseRunInd.equals(other.sysCloseRunInd))
			return false;
		if (sysName == null) {
			if (other.sysName != null)
				return false;
		} else if (!sysName.equals(other.sysName))
			return false;
		if (sysType == null) {
			if (other.sysType != null)
				return false;
		} else if (!sysType.equals(other.sysType))
			return false;
		if(itemLimit == null) {
			if (other.itemLimit != null)
				return false;
		} else if(!itemLimit.equals(other.itemLimit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebSystemParameterModel [sysName=" + sysName + ", defCurr=" + defCurr + ", inactiveDuration="
				+ inactiveDuration + ", sysType=" + sysType + ", activeInd=" + activeInd + ", sodRunInd=" + sodRunInd
				+ ", eodRunInd=" + eodRunInd + ", cisDwnldInd=" + cisDwnldInd + ", cisDwnldDate=" + cisDwnldDate
				+ ", processDate=" + processDate + ", nextMondayHolInd=" + nextMondayHolInd + ", easterDaysInd="
				+ easterDaysInd + ", archivePeriod=" + archivePeriod + ", inBalanceInd=" + inBalanceInd
				+ ", forcecloseReason=" + forcecloseReason + ", sysCloseRunInd=" + sysCloseRunInd + ", responsePeriod="
				+ responsePeriod + ", strCisDownloadDate=" + strCisDownloadDate + ", strProcessDate=" + strProcessDate
				+ ", iamPort=" + iamPort + ",itemLimit="+ itemLimit +"]";
	}

	
	
		
}
