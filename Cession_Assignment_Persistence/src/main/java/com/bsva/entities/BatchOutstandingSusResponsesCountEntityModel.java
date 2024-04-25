package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class BatchOutstandingSusResponsesCountEntityModel implements Serializable{
	
	private BigDecimal totalnrOfTrxns;

	

	public BigDecimal getTotalnrOfTrxns() {
		return totalnrOfTrxns;
	}

	public void setTotalnrOfTrxns(BigDecimal totalnrOfTrxns) {
		this.totalnrOfTrxns = totalnrOfTrxns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((totalnrOfTrxns == null) ? 0 : totalnrOfTrxns.hashCode());
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
		BatchOutstandingSusResponsesCountEntityModel other = (BatchOutstandingSusResponsesCountEntityModel) obj;
		if (totalnrOfTrxns == null) {
			if (other.totalnrOfTrxns != null)
				return false;
		} else if (!totalnrOfTrxns.equals(other.totalnrOfTrxns))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatchOutstandingSusResponsesCountEntityModel [totalnrOfTrxns=" + totalnrOfTrxns + "]";
	}

	
}
