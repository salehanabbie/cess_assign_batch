package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RealTimeOuststaindingReportModel implements Serializable{
	
	private String debtorBank;
	private String creditorBank;
	private String debtorMemberNo;
	private BigDecimal mdtInitOneDay;
	private BigDecimal mdtInitOverOneDay;
	private BigDecimal mdtAmendOneDay;
	private BigDecimal mdtAmendOverOneDay;
	private BigDecimal mdtCancOneDay;
	private BigDecimal mdtCancOverOneDay;
	
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
	public String getDebtorMemberNo() {
		return debtorMemberNo;
	}
	public void setDebtorMemberNo(String debtorMemberNo) {
		this.debtorMemberNo = debtorMemberNo;
	}
	public BigDecimal getMdtInitOneDay() {
		return mdtInitOneDay;
	}
	public void setMdtInitOneDay(BigDecimal mdtInitOneDay) {
		this.mdtInitOneDay = mdtInitOneDay;
	}
	public BigDecimal getMdtInitOverOneDay() {
		return mdtInitOverOneDay;
	}
	public void setMdtInitOverOneDay(BigDecimal mdtInitOverOneDay) {
		this.mdtInitOverOneDay = mdtInitOverOneDay;
	}
	public BigDecimal getMdtAmendOneDay() {
		return mdtAmendOneDay;
	}
	public void setMdtAmendOneDay(BigDecimal mdtAmendOneDay) {
		this.mdtAmendOneDay = mdtAmendOneDay;
	}
	public BigDecimal getMdtAmendOverOneDay() {
		return mdtAmendOverOneDay;
	}
	public void setMdtAmendOverOneDay(BigDecimal mdtAmendOverOneDay) {
		this.mdtAmendOverOneDay = mdtAmendOverOneDay;
	}
	public BigDecimal getMdtCancOneDay() {
		return mdtCancOneDay;
	}
	public void setMdtCancOneDay(BigDecimal mdtCancOneDay) {
		this.mdtCancOneDay = mdtCancOneDay;
	}
	public BigDecimal getMdtCancOverOneDay() {
		return mdtCancOverOneDay;
	}
	public void setMdtCancOverOneDay(BigDecimal mdtCancOverOneDay) {
		this.mdtCancOverOneDay = mdtCancOverOneDay;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
		result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + ((debtorMemberNo == null) ? 0 : debtorMemberNo.hashCode());
		result = prime * result + ((mdtAmendOneDay == null) ? 0 : mdtAmendOneDay.hashCode());
		result = prime * result + ((mdtAmendOverOneDay == null) ? 0 : mdtAmendOverOneDay.hashCode());
		result = prime * result + ((mdtCancOneDay == null) ? 0 : mdtCancOneDay.hashCode());
		result = prime * result + ((mdtCancOverOneDay == null) ? 0 : mdtCancOverOneDay.hashCode());
		result = prime * result + ((mdtInitOneDay == null) ? 0 : mdtInitOneDay.hashCode());
		result = prime * result + ((mdtInitOverOneDay == null) ? 0 : mdtInitOverOneDay.hashCode());
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
		RealTimeOuststaindingReportModel other = (RealTimeOuststaindingReportModel) obj;
		if (creditorBank == null) {
			if (other.creditorBank != null)
				return false;
		} else if (!creditorBank.equals(other.creditorBank))
			return false;
		if (debtorBank == null) {
			if (other.debtorBank != null)
				return false;
		} else if (!debtorBank.equals(other.debtorBank))
			return false;
		if (debtorMemberNo == null) {
			if (other.debtorMemberNo != null)
				return false;
		} else if (!debtorMemberNo.equals(other.debtorMemberNo))
			return false;
		if (mdtAmendOneDay == null) {
			if (other.mdtAmendOneDay != null)
				return false;
		} else if (!mdtAmendOneDay.equals(other.mdtAmendOneDay))
			return false;
		if (mdtAmendOverOneDay == null) {
			if (other.mdtAmendOverOneDay != null)
				return false;
		} else if (!mdtAmendOverOneDay.equals(other.mdtAmendOverOneDay))
			return false;
		if (mdtCancOneDay == null) {
			if (other.mdtCancOneDay != null)
				return false;
		} else if (!mdtCancOneDay.equals(other.mdtCancOneDay))
			return false;
		if (mdtCancOverOneDay == null) {
			if (other.mdtCancOverOneDay != null)
				return false;
		} else if (!mdtCancOverOneDay.equals(other.mdtCancOverOneDay))
			return false;
		if (mdtInitOneDay == null) {
			if (other.mdtInitOneDay != null)
				return false;
		} else if (!mdtInitOneDay.equals(other.mdtInitOneDay))
			return false;
		if (mdtInitOverOneDay == null) {
			if (other.mdtInitOverOneDay != null)
				return false;
		} else if (!mdtInitOverOneDay.equals(other.mdtInitOverOneDay))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RealTimeOuststaindingReportModel [debtorBank=" + debtorBank + ", creditorBank=" + creditorBank
				+ ", debtorMemberNo=" + debtorMemberNo + ", mdtInitOneDay=" + mdtInitOneDay + ", mdtInitOverOneDay="
				+ mdtInitOverOneDay + ", mdtAmendOneDay=" + mdtAmendOneDay + ", mdtAmendOverOneDay="
				+ mdtAmendOverOneDay + ", mdtCancOneDay=" + mdtCancOneDay + ", mdtCancOverOneDay=" + mdtCancOverOneDay
				+ "]";
	}
}
