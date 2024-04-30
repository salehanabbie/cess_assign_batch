package com.bsva.commons.model;

import java.io.Serializable;


/**
 * 
 * @author DimakatsoN
 *
 */
public class PasaOutsatandingReportModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String creditorBank;
	private String debtorBank;
	private int iniResp1;
	private int iniResp2;
	private int amendResp1;
	private int amendResp2;
	private int canResp1;
	private int canResp2;
	public String getCreditorBank() {
		return creditorBank;
	}
	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public int getIniResp1() {
		return iniResp1;
	}
	public void setIniResp1(int iniResp1) {
		this.iniResp1 = iniResp1;
	}
	public int getIniResp2() {
		return iniResp2;
	}
	public void setIniResp2(int iniResp2) {
		this.iniResp2 = iniResp2;
	}
	public int getAmendResp1() {
		return amendResp1;
	}
	public void setAmendResp1(int amendResp1) {
		this.amendResp1 = amendResp1;
	}
	public int getAmendResp2() {
		return amendResp2;
	}
	public void setAmendResp2(int amendResp2) {
		this.amendResp2 = amendResp2;
	}
	public int getCanResp1() {
		return canResp1;
	}
	public void setCanResp1(int canResp1) {
		this.canResp1 = canResp1;
	}
	public int getCanResp2() {
		return canResp2;
	}
	public void setCanResp2(int canResp2) {
		this.canResp2 = canResp2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amendResp1;
		result = prime * result + amendResp2;
		result = prime * result + canResp1;
		result = prime * result + canResp2;
		result = prime * result
				+ ((creditorBank == null) ? 0 : creditorBank.hashCode());
		result = prime * result
				+ ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + iniResp1;
		result = prime * result + iniResp2;
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
		PasaOutsatandingReportModel other = (PasaOutsatandingReportModel) obj;
		if (amendResp1 != other.amendResp1)
			return false;
		if (amendResp2 != other.amendResp2)
			return false;
		if (canResp1 != other.canResp1)
			return false;
		if (canResp2 != other.canResp2)
			return false;
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
		if (iniResp1 != other.iniResp1)
			return false;
		if (iniResp2 != other.iniResp2)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PasaOutsatandingReportModel [creditorBank=" + creditorBank
				+ ", debtorBank=" + debtorBank + ", iniResp1=" + iniResp1
				+ ", iniResp2=" + iniResp2 + ", amendResp1=" + amendResp1
				+ ", amendResp2=" + amendResp2 + ", canResp1=" + canResp1
				+ ", canResp2=" + canResp2 + "]";
	}
	
	
	
	
	
	

}
