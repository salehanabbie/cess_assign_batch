package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 
 * @author DimakatsoN
 *
 */

public class SystemParamReportEntityModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String memberNo;
	private String memberName;
	private BigDecimal maxNrRecords;
	private char debtorBank;
	private char creditorBank;
	private BigDecimal nrOfDaysProc;
	
	public SystemParamReportEntityModel() {
		
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getMaxNrRecords() {
		return maxNrRecords;
	}

	public void setMaxNrRecords(BigDecimal maxNrRecords) {
		this.maxNrRecords = maxNrRecords;
	}

	public char getDebtorBank() {
		return debtorBank;
	}

	public void setDebtorBank(char debtorBank) {
		this.debtorBank = debtorBank;
	}

	public char getCreditorBank() {
		return creditorBank;
	}

	public void setCreditorBank(char creditorBank) {
		this.creditorBank = creditorBank;
	}

	public BigDecimal getNrOfDaysProc() {
		return nrOfDaysProc;
	}

	public void setNrOfDaysProc(BigDecimal nrOfDaysProc) {
		this.nrOfDaysProc = nrOfDaysProc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + creditorBank;
		result = prime * result + debtorBank;
		result = prime * result
				+ ((maxNrRecords == null) ? 0 : maxNrRecords.hashCode());
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((nrOfDaysProc == null) ? 0 : nrOfDaysProc.hashCode());
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
		SystemParamReportEntityModel other = (SystemParamReportEntityModel) obj;
		if (creditorBank != other.creditorBank)
			return false;
		if (debtorBank != other.debtorBank)
			return false;
		if (maxNrRecords == null) {
			if (other.maxNrRecords != null)
				return false;
		} else if (!maxNrRecords.equals(other.maxNrRecords))
			return false;
		if (memberName == null) {
			if (other.memberName != null)
				return false;
		} else if (!memberName.equals(other.memberName))
			return false;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		if (nrOfDaysProc == null) {
			if (other.nrOfDaysProc != null)
				return false;
		} else if (!nrOfDaysProc.equals(other.nrOfDaysProc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SystemParamReportEntityModel [memberNo=" + memberNo
				+ ", memberName=" + memberName + ", maxNrRecords="
				+ maxNrRecords + ", debtorBank=" + debtorBank
				+ ", creditorBank=" + creditorBank + ", nrOfDaysProc="
				+ nrOfDaysProc + "]";
	}


	

	
	
	
	
	

}
