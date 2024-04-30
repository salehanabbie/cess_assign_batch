package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class OutstandingRespSummaryCountEntityModel implements Serializable {
	
	private BigDecimal nrOfTxns;

	public BigDecimal getNrOfDays() {
		return nrOfTxns;
	}

	public void setNrOfDays(BigDecimal nrOfTxns) {
		this.nrOfTxns = nrOfTxns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nrOfTxns == null) ? 0 : nrOfTxns.hashCode());
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
		OutstandingRespSummaryCountEntityModel other = (OutstandingRespSummaryCountEntityModel) obj;
		if (nrOfTxns == null) {
			if (other.nrOfTxns != null)
				return false;
		} else if (!nrOfTxns.equals(other.nrOfTxns))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutstandingRespSummaryCountEntityModel [nrOfTxns=" + nrOfTxns
				+ "]";
	}
	
	

}
