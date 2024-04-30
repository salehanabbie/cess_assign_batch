package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class BatchRejectedTransactionEntityModel implements Serializable {

    private String rescode;
    private String mndtRejectionReason;
    private String memberNumber;
    private BigDecimal NoTxns;

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public String getMndtRejectionReason() {
        return mndtRejectionReason;
    }

    public void setMndtRejectionReason(String mndtRejectionReason) {
        this.mndtRejectionReason = mndtRejectionReason;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public BigDecimal getNoTxns() {
        return NoTxns;
    }

    public void setNoTxns(BigDecimal noTxns) {
        NoTxns = noTxns;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NoTxns == null) ? 0 : NoTxns.hashCode());
		result = prime * result + ((memberNumber == null) ? 0 : memberNumber.hashCode());
		result = prime * result + ((mndtRejectionReason == null) ? 0 : mndtRejectionReason.hashCode());
		result = prime * result + ((rescode == null) ? 0 : rescode.hashCode());
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
		BatchRejectedTransactionEntityModel other = (BatchRejectedTransactionEntityModel) obj;
		if (NoTxns == null) {
			if (other.NoTxns != null)
				return false;
		} else if (!NoTxns.equals(other.NoTxns))
			return false;
		if (memberNumber == null) {
			if (other.memberNumber != null)
				return false;
		} else if (!memberNumber.equals(other.memberNumber))
			return false;
		if (mndtRejectionReason == null) {
			if (other.mndtRejectionReason != null)
				return false;
		} else if (!mndtRejectionReason.equals(other.mndtRejectionReason))
			return false;
		if (rescode == null) {
			if (other.rescode != null)
				return false;
		} else if (!rescode.equals(other.rescode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatchRejectedTransactionEntityModel [rescode=" + rescode + ", mndtRejectionReason="
				+ mndtRejectionReason + ", memberNumber=" + memberNumber + ", NoTxns=" + NoTxns + "]";
	}
    
    
}
