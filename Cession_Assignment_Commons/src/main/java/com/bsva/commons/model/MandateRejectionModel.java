package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author DimakatsoN
 *
 */
public class MandateRejectionModel implements Serializable {
	
	private BigDecimal rejectReasonCodeCount;

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
		result = prime
				* result
				+ ((rejectReasonCodeCount == null) ? 0 : rejectReasonCodeCount
						.hashCode());
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
		MandateRejectionModel other = (MandateRejectionModel) obj;
		if (rejectReasonCodeCount == null) {
			if (other.rejectReasonCodeCount != null)
				return false;
		} else if (!rejectReasonCodeCount.equals(other.rejectReasonCodeCount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MandateRejectionModel [rejectReasonCodeCount="
				+ rejectReasonCodeCount + "]";
	}
	
	
	

}
