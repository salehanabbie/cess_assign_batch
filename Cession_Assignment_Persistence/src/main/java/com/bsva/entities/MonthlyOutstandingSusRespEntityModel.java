package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthlyOutstandingSusRespEntityModel implements Serializable {
	
	private String debtorBank;
	private String creditorBank;
	private String creditorName;
	private BigDecimal nrOfTxns;
	private String serviceId; 
	private String debtorName;
	
	public MonthlyOutstandingSusRespEntityModel() {
		
	}

	public String getDebtorBank() {
		return debtorBank;
	}

	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}

	public String getCreditorBank() {
		return creditorBank;
	}

	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public BigDecimal getNrOfTxns() {
		return nrOfTxns;
	}

	public void setNrOfTxns(BigDecimal nrOfTxns) {
		this.nrOfTxns = nrOfTxns;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
		result = prime * result + ((creditorName == null) ? 0 : creditorName.hashCode());
		result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + ((debtorName == null) ? 0 : debtorName.hashCode());
		result = prime * result + ((nrOfTxns == null) ? 0 : nrOfTxns.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
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
		MonthlyOutstandingSusRespEntityModel other = (MonthlyOutstandingSusRespEntityModel) obj;
		if (creditorBank == null) {
			if (other.creditorBank != null)
				return false;
		} else if (!creditorBank.equals(other.creditorBank))
			return false;
		if (creditorName == null) {
			if (other.creditorName != null)
				return false;
		} else if (!creditorName.equals(other.creditorName))
			return false;
		if (debtorBank == null) {
			if (other.debtorBank != null)
				return false;
		} else if (!debtorBank.equals(other.debtorBank))
			return false;
		if (debtorName == null) {
			if (other.debtorName != null)
				return false;
		} else if (!debtorName.equals(other.debtorName))
			return false;
		if (nrOfTxns == null) {
			if (other.nrOfTxns != null)
				return false;
		} else if (!nrOfTxns.equals(other.nrOfTxns))
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
		return "MonthlyOutstandingSusRespEntityModel [debtorBank=" + debtorBank + ", creditorBank=" + creditorBank
				+ ", creditorName=" + creditorName + ", nrOfTxns=" + nrOfTxns + ", serviceId=" + serviceId
				+ ", debtorName=" + debtorName + "]";
	}
	
	

}
