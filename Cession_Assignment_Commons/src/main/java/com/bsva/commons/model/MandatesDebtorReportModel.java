package com.bsva.commons.model;

import java.math.BigInteger;
import java.util.Date;

public class MandatesDebtorReportModel {
	
	private static final long serialVersionUID = 1L;
	private String mandateId;
    private String mandateReqId;
    private Date fromDate;
    private Date toDate;
    private String finInstName;
    private BigInteger collAmount;
   
    
	public MandatesDebtorReportModel() {
		super();
	}
    
	public String getMandateId() {
		return mandateId;
	}
	public void setMandateId(String mandateId) {
		this.mandateId = mandateId;
	}
	public String getMandateReqId() {
		return mandateReqId;
	}
	public void setMandateReqId(String mandateReqId) {
		this.mandateReqId = mandateReqId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getFinInstName() {
		return finInstName;
	}
	public void setFinInstName(String finInstName) {
		this.finInstName = finInstName;
	}
	public BigInteger getCollAmount() {
		return collAmount;
	}
	public void setCollAmount(BigInteger collAmount) {
		this.collAmount = collAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collAmount == null) ? 0 : collAmount.hashCode());
		result = prime * result
				+ ((finInstName == null) ? 0 : finInstName.hashCode());
		result = prime * result
				+ ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result
				+ ((mandateId == null) ? 0 : mandateId.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
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
		MandatesDebtorReportModel other = (MandatesDebtorReportModel) obj;
		if (collAmount == null) {
			if (other.collAmount != null)
				return false;
		} else if (!collAmount.equals(other.collAmount))
			return false;
		if (finInstName == null) {
			if (other.finInstName != null)
				return false;
		} else if (!finInstName.equals(other.finInstName))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (mandateId == null) {
			if (other.mandateId != null)
				return false;
		} else if (!mandateId.equals(other.mandateId))
			return false;
		if (mandateReqId == null) {
			if (other.mandateReqId != null)
				return false;
		} else if (!mandateReqId.equals(other.mandateReqId))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MandatesDebtorReportModel [mandateId=" + mandateId
				+ ", mandateReqId=" + mandateReqId + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", finInstName=" + finInstName
				+ ", collAmount=" + collAmount + "]";
	}

    
    
}
