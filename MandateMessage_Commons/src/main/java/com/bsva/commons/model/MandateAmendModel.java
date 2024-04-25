package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 
 * @author DimakatsoN
 *
 */

public class MandateAmendModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal amendReasonCodeCount;

	public BigDecimal getAmendReasonCodeCount() {
		return amendReasonCodeCount;
	}

	public void setAmendReasonCodeCount(BigDecimal amendReasonCodeCount) {
		this.amendReasonCodeCount = amendReasonCodeCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((amendReasonCodeCount == null) ? 0 : amendReasonCodeCount
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
		MandateAmendModel other = (MandateAmendModel) obj;
		if (amendReasonCodeCount == null) {
			if (other.amendReasonCodeCount != null)
				return false;
		} else if (!amendReasonCodeCount.equals(other.amendReasonCodeCount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MandateAmendModel [amendReasonCodeCount="
				+ amendReasonCodeCount + "]";
	}
	
	
	
	
	
	
	

	
	

}
