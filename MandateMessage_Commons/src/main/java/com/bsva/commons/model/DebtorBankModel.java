package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtorBankModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberNo;
	private String memberName;
	private BigDecimal txnCount;
	
	
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

	public BigDecimal getTxnCount() {
		return txnCount;
	}

	public void setTxnCount(BigDecimal txnCount) {
		this.txnCount = txnCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((txnCount == null) ? 0 : txnCount.hashCode());
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
		DebtorBankModel other = (DebtorBankModel) obj;
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
		if (txnCount == null) {
			if (other.txnCount != null)
				return false;
		} else if (!txnCount.equals(other.txnCount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DebtorBankModel [memberNo=" + memberNo + ", memberName="
				+ memberName + ", txnCount=" + txnCount + "]";
	}
	
	

}
