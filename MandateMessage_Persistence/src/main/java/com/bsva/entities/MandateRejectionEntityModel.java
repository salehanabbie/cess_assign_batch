package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author DimakatsoN
 *
 */

public class MandateRejectionEntityModel implements Serializable 
{	
	private String creditorBank;
	private String debtorBank;
	private String creditorBankName;
	private String debtorBankName;
	private String reasonName;
	private String reasonCode;
	private String txnDate;
	private BigDecimal rejectReasonCodeCount;
	
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
	
	public String getCreditorBankName() {
		return creditorBankName;
	}
	
	public void setCreditorBankName(String creditorBankName) {
		this.creditorBankName = creditorBankName;
	}
	
	public String getDebtorBankName() {
		return debtorBankName;
	}
	
	public void setDebtorBankName(String debtorBankName) {
		this.debtorBankName = debtorBankName;
	}
	
	public String getReasonName() {
		return reasonName;
	}
	
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}
	
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	public String getTxnDate() {
		return txnDate;
	}
	
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	
	public BigDecimal getRejectReasonCodeCount() {
		return rejectReasonCodeCount;
	}
	
	public void setRejectReasonCodeCount(BigDecimal rejectReasonCodeCount) {
		this.rejectReasonCodeCount = rejectReasonCodeCount;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
		result = prime * result + ((creditorBankName == null) ? 0 : creditorBankName.hashCode());
		result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + ((debtorBankName == null) ? 0 : debtorBankName.hashCode());
		result = prime * result + ((reasonCode == null) ? 0 : reasonCode.hashCode());
		result = prime * result + ((reasonName == null) ? 0 : reasonName.hashCode());
		result = prime * result + ((rejectReasonCodeCount == null) ? 0 : rejectReasonCodeCount.hashCode());
		result = prime * result + ((txnDate == null) ? 0 : txnDate.hashCode());
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
		MandateRejectionEntityModel other = (MandateRejectionEntityModel) obj;
		if (creditorBank == null) {
			if (other.creditorBank != null)
				return false;
		} else if (!creditorBank.equals(other.creditorBank))
			return false;
		if (creditorBankName == null) {
			if (other.creditorBankName != null)
				return false;
		} else if (!creditorBankName.equals(other.creditorBankName))
			return false;
		if (debtorBank == null) {
			if (other.debtorBank != null)
				return false;
		} else if (!debtorBank.equals(other.debtorBank))
			return false;
		if (debtorBankName == null) {
			if (other.debtorBankName != null)
				return false;
		} else if (!debtorBankName.equals(other.debtorBankName))
			return false;
		if (reasonCode == null) {
			if (other.reasonCode != null)
				return false;
		} else if (!reasonCode.equals(other.reasonCode))
			return false;
		if (reasonName == null) {
			if (other.reasonName != null)
				return false;
		} else if (!reasonName.equals(other.reasonName))
			return false;
		if (rejectReasonCodeCount == null) {
			if (other.rejectReasonCodeCount != null)
				return false;
		} else if (!rejectReasonCodeCount.equals(other.rejectReasonCodeCount))
			return false;
		if (txnDate == null) {
			if (other.txnDate != null)
				return false;
		} else if (!txnDate.equals(other.txnDate))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "MandateRejectionEntityModel ===> "
//				+ "[creditorBank=" + creditorBank + "\n" +
//				"  , debtorBank=" + debtorBank + "\n" + 
				+"  , creditorBankName=" + creditorBankName +"\n" + 
				"  , debtorBankName=" + debtorBankName + "\n" + 
				"  , reasonName="+ reasonName +"\n" + 
				"  , reasonCode=" + reasonCode + "\n" +
//				"  , txnDate=" + txnDate + "\n" +
				"  , rejectReasonCodeCount="+ rejectReasonCodeCount + "]";
	}
	
	

	
	
	
	

}
