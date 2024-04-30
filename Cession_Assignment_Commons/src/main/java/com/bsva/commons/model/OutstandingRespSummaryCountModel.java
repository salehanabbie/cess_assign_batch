package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 
 * @author DimakatsoN
 *
 */

public class OutstandingRespSummaryCountModel implements Serializable {
	
	private BigDecimal nrOfDays;

	public BigDecimal getNrOfDays() {
		return nrOfDays;
	}

	public void setNrOfDays(BigDecimal nrOfDays) {
		this.nrOfDays = nrOfDays;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nrOfDays == null) ? 0 : nrOfDays.hashCode());
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
		OutstandingRespSummaryCountModel other = (OutstandingRespSummaryCountModel) obj;
		if (nrOfDays == null) {
			if (other.nrOfDays != null)
				return false;
		} else if (!nrOfDays.equals(other.nrOfDays))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutstandingRespSummaryCountModel [nrOfDays=" + nrOfDays + "]";
	}
	
	
	

}
