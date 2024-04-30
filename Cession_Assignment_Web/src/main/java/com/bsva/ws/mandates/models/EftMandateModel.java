package com.bsva.ws.mandates.models;
import java.io.Serializable;
import java.util.Date;



public class EftMandateModel  implements Serializable{

	private String mandateId; 
	private String endToEndId;
	private Double instructedAmount;
	private Date firstCollectionDate;
	private Integer frequency;
	private String debtor;
	private String debtorAccount;
	private String debtorBicCode;
	private String creditor;
	private String creditorAccount;
	private String creditorBicCode;

	/**
	 * @return the endToEndId
	 */
	public String getEndToEndId() {
		return this.endToEndId;
	}
	/**
	 * @param endToEndId the endToEndId to set
	 */
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
		
	/**
	 * @return the mandateId
	 */
	public String getMandateId() {
		return this.mandateId;
	}
	/**
	 * @param mandateId the mandateId to set
	 */
	public void setMandateId(String mandateId) {
		this.mandateId = mandateId;
	}
	/**
	 * @return the instructedAmount
	 */
	public Double getInstructedAmount() {
		return this.instructedAmount;
	}
	/**
	 * @param instructedAmount the instructedAmount to set
	 */
	public void setInstructedAmount(Double instructedAmount) {
		this.instructedAmount = instructedAmount;
	}
	
	/**
	 * @return the firstCollectionDate
	 */
	public Date getFirstCollectionDate() {
		return this.firstCollectionDate;
	}
	/**
	 * @param firstCollectionDate the firstCollectionDate to set
	 */
	public void setFirstCollectionDate(Date firstCollectionDate) {
		this.firstCollectionDate = firstCollectionDate;
	}

	/**
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return this.frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * @return the debtor
	 */
	public String getDebtor() {
		return this.debtor;
	}
	/**
	 * @param debtor the debtor to set
	 */
	public void setDebtor(String debtor) {
		this.debtor = debtor;
	}

	/**
	 * @return the debtorAccount
	 */
	public String getDebtorAccount() {
		return this.debtorAccount;
	}
	/**
	 * @param debtorAccount the debtorAccount to set
	 */
	public void setDebtorAccount(String debtorAccount) {
		this.debtorAccount = debtorAccount;
	}

	/**
	 * @return the debtorBicCode
	 */
	public String getDebtorBicCode() {
		return this.debtorBicCode;
	}
	/**
	 * @param debtorBicCode the debtorBicCode to set
	 */
	public void setDebtorBicCode(String debtorBicCode) {
		this.debtorBicCode = debtorBicCode;
	}

	/**
	 * @return the creditor
	 */
	public String getCreditor() {
		return this.creditor;
	}
	/**
	 * @param creditor the creditor to set
	 */
	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	/**
	 * @return the creditorAccount
	 */
	public String getCreditorAccount() {
		return this.creditorAccount;
	}
	/**
	 * @param creditorAccount the creditorAccount to set
	 */
	public void setCreditorAccount(String creditorAccount) {
		this.creditorAccount = creditorAccount;
	}

	/**
	 * @return the creditorBicCode
	 */
	public String getCreditorBicCode() {
		return this.creditorBicCode;
	}
	/**
	 * @param creditorBicCode the creditorBicCode to set
	 */
	public void setCreditorBicCode(String creditorBicCode) {
		this.creditorBicCode = creditorBicCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mandateId == null) ? 0 : mandateId.hashCode());
		result = prime * result
				+ ((instructedAmount == null) ? 0 : instructedAmount.hashCode());
		result = prime * result + ((firstCollectionDate == null) ? 0 : firstCollectionDate.hashCode());
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((debtor == null) ? 0 : debtor.hashCode());
		result = prime * result + ((debtorAccount == null) ? 0 : debtorAccount.hashCode());
		result = prime * result + ((debtorBicCode == null) ? 0 : debtorBicCode.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EftMandateModel other = (EftMandateModel) obj;
		if (mandateId == null) {
			if (other.mandateId != null)
				return false;
		} else if (!mandateId.equals(other.mandateId))
			return false;
		if (instructedAmount == null) {
			if (other.instructedAmount != null)
				return false;
		} else if (!instructedAmount.equals(other.instructedAmount))
			return false;
		if (firstCollectionDate == null) {
			if (other.firstCollectionDate != null)
				return false;
		} else if (!firstCollectionDate.equals(other.firstCollectionDate))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (debtor == null) {
			if (other.debtor != null)
				return false;
		} else if (!debtor.equals(other.debtor))
			return false;
		if (debtorAccount == null) {
			if (other.debtorAccount != null)
				return false;
		} else if (!debtorAccount.equals(other.debtorAccount))
			return false;
		if (debtorBicCode == null) {
			if (other.debtorBicCode != null)
				return false;
		} else if (!debtorBicCode.equals(other.debtorBicCode))
			return false;
		if (creditor == null) {
			if (other.creditor != null)
				return false;
		} else if (!creditor.equals(other.creditor))
			return false;
		if (creditorAccount == null) {
			if (other.creditorAccount != null)
				return false;
		} else if (!creditorAccount.equals(other.creditorAccount))
			return false;
		if (creditorBicCode == null) {
			if (other.creditorBicCode != null)
				return false;
		} else if (!creditorBicCode.equals(other.creditorBicCode))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EftMandateModel [mandateId=" + mandateId + ", instructedAmount="
				+ instructedAmount + ", firstCollectionDate=" + firstCollectionDate 
				+ ", frequency=" + frequency 
				+ ", debtor=" + debtor  
				+ ", debtorAccount=" + debtorAccount  
				+ ", debtorBicCode=" + debtorBicCode  
				+ ", creditor=" + creditor  
				+ ", creditorAccount=" + creditorAccount  
				+ ", creditorBicCode=" + creditorBicCode  + "]";
	} 
}
