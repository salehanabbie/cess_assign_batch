package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author SalehaR
 *
 */
public class OutBalanceCountReportEntityModel implements Serializable {

	private BigDecimal nrMsgsRejected;
	private BigDecimal nrMsgsAccepted;
	private BigDecimal nrMsgsExtracted;
	private BigDecimal totalNrOfMsgs, difference;
	private String inServiceId, outServiceId, extServiceId;
	
	public OutBalanceCountReportEntityModel()
	{
		
	}

	public BigDecimal getNrMsgsRejected() {
		return nrMsgsRejected;
	}

	public void setNrMsgsRejected(BigDecimal nrMsgsRejected) {
		this.nrMsgsRejected = nrMsgsRejected;
	}

	public BigDecimal getNrMsgsAccepted() {
		return nrMsgsAccepted;
	}

	public void setNrMsgsAccepted(BigDecimal nrMsgsAccepted) {
		this.nrMsgsAccepted = nrMsgsAccepted;
	}

	public BigDecimal getNrMsgsExtracted() {
		return nrMsgsExtracted;
	}

	public void setNrMsgsExtracted(BigDecimal nrMsgsExtracted) {
		this.nrMsgsExtracted = nrMsgsExtracted;
	}

	public BigDecimal getTotalNrOfMsgs() {
		return totalNrOfMsgs;
	}

	public void setTotalNrOfMsgs(BigDecimal totalNrOfMsgs) {
		this.totalNrOfMsgs = totalNrOfMsgs;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	public String getInServiceId() {
		return inServiceId;
	}

	public void setInServiceId(String inServiceId) {
		this.inServiceId = inServiceId;
	}

	public String getOutServiceId() {
		return outServiceId;
	}

	public void setOutServiceId(String outServiceId) {
		this.outServiceId = outServiceId;
	}

	public String getExtServiceId() {
		return extServiceId;
	}

	public void setExtServiceId(String extServiceId) {
		this.extServiceId = extServiceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((difference == null) ? 0 : difference.hashCode());
		result = prime * result
				+ ((extServiceId == null) ? 0 : extServiceId.hashCode());
		result = prime * result
				+ ((inServiceId == null) ? 0 : inServiceId.hashCode());
		result = prime * result
				+ ((nrMsgsAccepted == null) ? 0 : nrMsgsAccepted.hashCode());
		result = prime * result
				+ ((nrMsgsExtracted == null) ? 0 : nrMsgsExtracted.hashCode());
		result = prime * result
				+ ((nrMsgsRejected == null) ? 0 : nrMsgsRejected.hashCode());
		result = prime * result
				+ ((outServiceId == null) ? 0 : outServiceId.hashCode());
		result = prime * result
				+ ((totalNrOfMsgs == null) ? 0 : totalNrOfMsgs.hashCode());
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
		OutBalanceCountReportEntityModel other = (OutBalanceCountReportEntityModel) obj;
		if (difference == null) {
			if (other.difference != null)
				return false;
		} else if (!difference.equals(other.difference))
			return false;
		if (extServiceId == null) {
			if (other.extServiceId != null)
				return false;
		} else if (!extServiceId.equals(other.extServiceId))
			return false;
		if (inServiceId == null) {
			if (other.inServiceId != null)
				return false;
		} else if (!inServiceId.equals(other.inServiceId))
			return false;
		if (nrMsgsAccepted == null) {
			if (other.nrMsgsAccepted != null)
				return false;
		} else if (!nrMsgsAccepted.equals(other.nrMsgsAccepted))
			return false;
		if (nrMsgsExtracted == null) {
			if (other.nrMsgsExtracted != null)
				return false;
		} else if (!nrMsgsExtracted.equals(other.nrMsgsExtracted))
			return false;
		if (nrMsgsRejected == null) {
			if (other.nrMsgsRejected != null)
				return false;
		} else if (!nrMsgsRejected.equals(other.nrMsgsRejected))
			return false;
		if (outServiceId == null) {
			if (other.outServiceId != null)
				return false;
		} else if (!outServiceId.equals(other.outServiceId))
			return false;
		if (totalNrOfMsgs == null) {
			if (other.totalNrOfMsgs != null)
				return false;
		} else if (!totalNrOfMsgs.equals(other.totalNrOfMsgs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutBalanceCountReportEntityModel [nrMsgsRejected="
				+ nrMsgsRejected + ", nrMsgsAccepted=" + nrMsgsAccepted
				+ ", nrMsgsExtracted=" + nrMsgsExtracted + ", totalNrOfMsgs="
				+ totalNrOfMsgs + ", difference=" + difference
				+ ", inServiceId=" + inServiceId + ", outServiceId="
				+ outServiceId + ", extServiceId=" + extServiceId + "]";
	}
	
	
	
	
}
