package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class BatchAmendmentEntityModel implements Serializable {

    private String creditor;
    private BigDecimal reqByCustomer;
    private BigDecimal canAmndReqByInitPrty;
    private BigDecimal unspndMndtWithChngs;
    private BigDecimal unspndUnchgMndt;
    private BigDecimal rsnNotSpcByCust;

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public BigDecimal getReqByCustomer() {
        return reqByCustomer;
    }

    public void setReqByCustomer(BigDecimal reqByCustomer) {
        this.reqByCustomer = reqByCustomer;
    }

    public BigDecimal getCanAmndReqByInitPrty() {
        return canAmndReqByInitPrty;
    }

    public void setCanAmndReqByInitPrty(BigDecimal canAmndReqByInitPrty) {
        this.canAmndReqByInitPrty = canAmndReqByInitPrty;
    }

    public BigDecimal getUnspndMndtWithChngs() {
        return unspndMndtWithChngs;
    }

    public void setUnspndMndtWithChngs(BigDecimal unspndMndtWithChngs) {
        this.unspndMndtWithChngs = unspndMndtWithChngs;
    }

    public BigDecimal getUnspndUnchgMndt() {
        return unspndUnchgMndt;
    }

    public void setUnspndUnchgMndt(BigDecimal unspndUnchgMndt) {
        this.unspndUnchgMndt = unspndUnchgMndt;
    }

    public BigDecimal getRsnNotSpcByCust() {
        return rsnNotSpcByCust;
    }

    public void setRsnNotSpcByCust(BigDecimal rsnNotSpcByCust) {
        this.rsnNotSpcByCust = rsnNotSpcByCust;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((canAmndReqByInitPrty == null) ? 0 : canAmndReqByInitPrty.hashCode());
		result = prime * result + ((creditor == null) ? 0 : creditor.hashCode());
		result = prime * result + ((reqByCustomer == null) ? 0 : reqByCustomer.hashCode());
		result = prime * result + ((rsnNotSpcByCust == null) ? 0 : rsnNotSpcByCust.hashCode());
		result = prime * result + ((unspndMndtWithChngs == null) ? 0 : unspndMndtWithChngs.hashCode());
		result = prime * result + ((unspndUnchgMndt == null) ? 0 : unspndUnchgMndt.hashCode());
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
		BatchAmendmentEntityModel other = (BatchAmendmentEntityModel) obj;
		if (canAmndReqByInitPrty == null) {
			if (other.canAmndReqByInitPrty != null)
				return false;
		} else if (!canAmndReqByInitPrty.equals(other.canAmndReqByInitPrty))
			return false;
		if (creditor == null) {
			if (other.creditor != null)
				return false;
		} else if (!creditor.equals(other.creditor))
			return false;
		if (reqByCustomer == null) {
			if (other.reqByCustomer != null)
				return false;
		} else if (!reqByCustomer.equals(other.reqByCustomer))
			return false;
		if (rsnNotSpcByCust == null) {
			if (other.rsnNotSpcByCust != null)
				return false;
		} else if (!rsnNotSpcByCust.equals(other.rsnNotSpcByCust))
			return false;
		if (unspndMndtWithChngs == null) {
			if (other.unspndMndtWithChngs != null)
				return false;
		} else if (!unspndMndtWithChngs.equals(other.unspndMndtWithChngs))
			return false;
		if (unspndUnchgMndt == null) {
			if (other.unspndUnchgMndt != null)
				return false;
		} else if (!unspndUnchgMndt.equals(other.unspndUnchgMndt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatchAmendmentEntityModel [creditor=" + creditor + ", reqByCustomer=" + reqByCustomer
				+ ", canAmndReqByInitPrty=" + canAmndReqByInitPrty + ", unspndMndtWithChngs=" + unspndMndtWithChngs
				+ ", unspndUnchgMndt=" + unspndUnchgMndt + ", rsnNotSpcByCust=" + rsnNotSpcByCust + "]";
	}
    
    
}
