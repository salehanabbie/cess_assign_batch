package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class WebQuerybyMandateModel implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mandateReqTranId;
	private String mandateRefNr;
	private String debtorName;
	private String creditorName;
	private BigDecimal collAmount;
	private Date firstCollDate;
	private String processStatus;
	private String serviceId;
	
	
	public WebQuerybyMandateModel (String mandateReqTranId)
    {
    	this.mandateReqTranId = mandateReqTranId;
    }
    
    public WebQuerybyMandateModel()
    {
    	
    }
    
	public String getMandateReqTranId() {
		return mandateReqTranId;
	}
	public void setMandateReqTranId(String mandateReqTranId) {
		this.mandateReqTranId = mandateReqTranId;
	}
	public String getMandateRefNr() {
		return mandateRefNr;
	}
	public void setMandateRefNr(String mandateRefNr) {
		this.mandateRefNr = mandateRefNr;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public BigDecimal getCollAmount() {
		return collAmount;
	}
	public void setCollAmount(BigDecimal collAmount) {
		this.collAmount = collAmount;
	}
	public Date getFirstCollDate() {
		return firstCollDate;
	}
	public void setFirstCollDate(Date firstCollDate) {
		this.firstCollDate = firstCollDate;
	}
	
	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collAmount == null) ? 0 : collAmount.hashCode());
		result = prime * result
				+ ((creditorName == null) ? 0 : creditorName.hashCode());
		result = prime * result
				+ ((debtorName == null) ? 0 : debtorName.hashCode());
		result = prime * result
				+ ((firstCollDate == null) ? 0 : firstCollDate.hashCode());
		result = prime * result
				+ ((mandateRefNr == null) ? 0 : mandateRefNr.hashCode());
		result = prime
				* result
				+ ((mandateReqTranId == null) ? 0 : mandateReqTranId.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
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
		WebQuerybyMandateModel other = (WebQuerybyMandateModel) obj;
		if (collAmount == null) {
			if (other.collAmount != null)
				return false;
		} else if (!collAmount.equals(other.collAmount))
			return false;
		if (creditorName == null) {
			if (other.creditorName != null)
				return false;
		} else if (!creditorName.equals(other.creditorName))
			return false;
		if (debtorName == null) {
			if (other.debtorName != null)
				return false;
		} else if (!debtorName.equals(other.debtorName))
			return false;
		if (firstCollDate == null) {
			if (other.firstCollDate != null)
				return false;
		} else if (!firstCollDate.equals(other.firstCollDate))
			return false;
		if (mandateRefNr == null) {
			if (other.mandateRefNr != null)
				return false;
		} else if (!mandateRefNr.equals(other.mandateRefNr))
			return false;
		if (mandateReqTranId == null) {
			if (other.mandateReqTranId != null)
				return false;
		} else if (!mandateReqTranId.equals(other.mandateReqTranId))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebQuerybyMandateModel [mandateReqTranId=" + mandateReqTranId
				+ ", mandateRefNr=" + mandateRefNr + ", debtorName="
				+ debtorName + ", creditorName=" + creditorName
				+ ", collAmount=" + collAmount + ", firstCollDate="
				+ firstCollDate + ", processStatus=" + processStatus
				+ ", serviceId=" + serviceId + "]";
	}

	
	
}
