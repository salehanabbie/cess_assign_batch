package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AmendmentCodesModel implements Serializable 
{

	private String amendmentCodes;
	private String amendmentCodesDescription;
	private String activeInd;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal txnCount;
	
	public AmendmentCodesModel(String amendmentCodes)
	{
		this.amendmentCodes = amendmentCodes;
	}
	
	public AmendmentCodesModel()
	{
	
	}	
	
	public String getAmendmentCodes(){
		return amendmentCodes;
	}
	
	public void setAmendmentCodes(String amendmentCodes){
		this.amendmentCodes = amendmentCodes;
	}
	
	public String getAmendmentCodesDescription(){
		return amendmentCodesDescription;
	}
	
	public void setAmendmentCodesDescription(String amendmentCodesDescription){
		this.amendmentCodesDescription = amendmentCodesDescription;
	}
	
	public String getActiveInd(){
		return activeInd;
	}
	
	public void setActiveInd(String activeInd){
		this.activeInd = activeInd;
	}
	
	public String getCreatedBy(){
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}
	
	public Date getCreatedDate(){
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate){
		this.createdDate = createdDate;
	}
	
	public String getModifiedBy(){
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy){
		this.modifiedBy = modifiedBy;
	}
	
	public Date getModifiedDate(){
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate){
		this.modifiedDate = modifiedDate;
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
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((amendmentCodes == null) ? 0 : amendmentCodes.hashCode());
		result = prime
				* result
				+ ((amendmentCodesDescription == null) ? 0
						: amendmentCodesDescription.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
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
		AmendmentCodesModel other = (AmendmentCodesModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (amendmentCodes == null) {
			if (other.amendmentCodes != null)
				return false;
		} else if (!amendmentCodes.equals(other.amendmentCodes))
			return false;
		if (amendmentCodesDescription == null) {
			if (other.amendmentCodesDescription != null)
				return false;
		} else if (!amendmentCodesDescription
				.equals(other.amendmentCodesDescription))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
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
		return "AmendmentCodesModel [amendmentCodes=" + amendmentCodes
				+ ", amendmentCodesDescription=" + amendmentCodesDescription
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", txnCount=" + txnCount
				+ "]";
	}

	
	
	
}
