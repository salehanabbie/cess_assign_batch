package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BatchOustandingResponseReportModel implements Serializable {

	private String debtorBank;
    private String creditorName;
    private String debtorName;
    private String serviceId;
    private BigDecimal maninOneDay;
    private BigDecimal maninTwoDay;
    private BigDecimal manamOneDay;
    private BigDecimal manamTwoDay;
    private BigDecimal mancnOneDay;
    private BigDecimal mancnTwoDay;
    
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public BigDecimal getManinOneDay() {
		return maninOneDay;
	}
	public void setManinOneDay(BigDecimal maninOneDay) {
		this.maninOneDay = maninOneDay;
	}
	public BigDecimal getManinTwoDay() {
		return maninTwoDay;
	}
	public void setManinTwoDay(BigDecimal maninTwoDay) {
		this.maninTwoDay = maninTwoDay;
	}
	public BigDecimal getManamOneDay() {
		return manamOneDay;
	}
	public void setManamOneDay(BigDecimal manamOneDay) {
		this.manamOneDay = manamOneDay;
	}
	public BigDecimal getManamTwoDay() {
		return manamTwoDay;
	}
	public void setManamTwoDay(BigDecimal manamTwoDay) {
		this.manamTwoDay = manamTwoDay;
	}
	public BigDecimal getMancnOneDay() {
		return mancnOneDay;
	}
	public void setMancnOneDay(BigDecimal mancnOneDay) {
		this.mancnOneDay = mancnOneDay;
	}
	public BigDecimal getMancnTwoDay() {
		return mancnTwoDay;
	}
	public void setMancnTwoDay(BigDecimal mancnTwoDay) {
		this.mancnTwoDay = mancnTwoDay;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditorName == null) ? 0 : creditorName.hashCode());
		result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + ((debtorName == null) ? 0 : debtorName.hashCode());
		result = prime * result + ((manamOneDay == null) ? 0 : manamOneDay.hashCode());
		result = prime * result + ((manamTwoDay == null) ? 0 : manamTwoDay.hashCode());
		result = prime * result + ((mancnOneDay == null) ? 0 : mancnOneDay.hashCode());
		result = prime * result + ((mancnTwoDay == null) ? 0 : mancnTwoDay.hashCode());
		result = prime * result + ((maninOneDay == null) ? 0 : maninOneDay.hashCode());
		result = prime * result + ((maninTwoDay == null) ? 0 : maninTwoDay.hashCode());
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
		BatchOustandingResponseReportModel other = (BatchOustandingResponseReportModel) obj;
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
		if (manamOneDay == null) {
			if (other.manamOneDay != null)
				return false;
		} else if (!manamOneDay.equals(other.manamOneDay))
			return false;
		if (manamTwoDay == null) {
			if (other.manamTwoDay != null)
				return false;
		} else if (!manamTwoDay.equals(other.manamTwoDay))
			return false;
		if (mancnOneDay == null) {
			if (other.mancnOneDay != null)
				return false;
		} else if (!mancnOneDay.equals(other.mancnOneDay))
			return false;
		if (mancnTwoDay == null) {
			if (other.mancnTwoDay != null)
				return false;
		} else if (!mancnTwoDay.equals(other.mancnTwoDay))
			return false;
		if (maninOneDay == null) {
			if (other.maninOneDay != null)
				return false;
		} else if (!maninOneDay.equals(other.maninOneDay))
			return false;
		if (maninTwoDay == null) {
			if (other.maninTwoDay != null)
				return false;
		} else if (!maninTwoDay.equals(other.maninTwoDay))
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
		return "BatchOustandingResponseReportModel [debtorBank=" + debtorBank + ", creditorName=" + creditorName
				+ ", debtorName=" + debtorName + ", serviceId=" + serviceId + ", maninOneDay=" + maninOneDay
				+ ", maninTwoDay=" + maninTwoDay + ", manamOneDay=" + manamOneDay + ", manamTwoDay=" + manamTwoDay
				+ ", mancnOneDay=" + mancnOneDay + ", mancnTwoDay=" + mancnTwoDay + "]";
	}

    

}
