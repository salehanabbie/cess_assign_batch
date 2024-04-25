package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SalehaR
 *
 */
public class MonthlyVolumeCountEntityModel implements Serializable
{
	String instId, service, localInst;
	BigDecimal nrOfMsgs,nrOfAccpMsgs, nrOfRjctMsgs, nrOfExtMsgs;
	BigDecimal totalNrOfMsgs, totalAccpMsgs, totalRjctdMsgs, totalNrOfExtMsgs;

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public BigDecimal getNrOfMsgs() {
		return nrOfMsgs;
	}

	public void setNrOfMsgs(BigDecimal nrOfMsgs) {
		this.nrOfMsgs = nrOfMsgs;
	}

	public BigDecimal getNrOfAccpMsgs() {
		return nrOfAccpMsgs;
	}

	public void setNrOfAccpMsgs(BigDecimal nrOfAccpMsgs) {
		this.nrOfAccpMsgs = nrOfAccpMsgs;
	}

	public BigDecimal getNrOfRjctMsgs() {
		return nrOfRjctMsgs;
	}

	public void setNrOfRjctMsgs(BigDecimal nrOfRjctMsgs) {
		this.nrOfRjctMsgs = nrOfRjctMsgs;
	}

	public BigDecimal getTotalNrOfMsgs() {
		return totalNrOfMsgs;
	}

	public void setTotalNrOfMsgs(BigDecimal totalNrOfMsgs) {
		this.totalNrOfMsgs = totalNrOfMsgs;
	}

	public BigDecimal getTotalAccpMsgs() {
		return totalAccpMsgs;
	}

	public void setTotalAccpMsgs(BigDecimal totalAccpMsgs) {
		this.totalAccpMsgs = totalAccpMsgs;
	}

	public BigDecimal getTotalRjctdMsgs() {
		return totalRjctdMsgs;
	}

	public void setTotalRjctdMsgs(BigDecimal totalRjctdMsgs) {
		this.totalRjctdMsgs = totalRjctdMsgs;
	}

	public String getLocalInst() {
		return localInst;
	}

	public void setLocalInst(String localInst) {
		this.localInst = localInst;
	}

	public BigDecimal getNrOfExtMsgs() {
		return nrOfExtMsgs;
	}

	public void setNrOfExtMsgs(BigDecimal nrOfExtMsgs) {
		this.nrOfExtMsgs = nrOfExtMsgs;
	}

	public BigDecimal getTotalNrOfExtMsgs() {
		return totalNrOfExtMsgs;
	}

	public void setTotalNrOfExtMsgs(BigDecimal totalNrOfExtMsgs) {
		this.totalNrOfExtMsgs = totalNrOfExtMsgs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((localInst == null) ? 0 : localInst.hashCode());
		result = prime * result + ((nrOfAccpMsgs == null) ? 0 : nrOfAccpMsgs.hashCode());
		result = prime * result + ((nrOfExtMsgs == null) ? 0 : nrOfExtMsgs.hashCode());
		result = prime * result + ((nrOfMsgs == null) ? 0 : nrOfMsgs.hashCode());
		result = prime * result + ((nrOfRjctMsgs == null) ? 0 : nrOfRjctMsgs.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((totalAccpMsgs == null) ? 0 : totalAccpMsgs.hashCode());
		result = prime * result + ((totalNrOfExtMsgs == null) ? 0 : totalNrOfExtMsgs.hashCode());
		result = prime * result + ((totalNrOfMsgs == null) ? 0 : totalNrOfMsgs.hashCode());
		result = prime * result + ((totalRjctdMsgs == null) ? 0 : totalRjctdMsgs.hashCode());
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
		MonthlyVolumeCountEntityModel other = (MonthlyVolumeCountEntityModel) obj;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (localInst == null) {
			if (other.localInst != null)
				return false;
		} else if (!localInst.equals(other.localInst))
			return false;
		if (nrOfAccpMsgs == null) {
			if (other.nrOfAccpMsgs != null)
				return false;
		} else if (!nrOfAccpMsgs.equals(other.nrOfAccpMsgs))
			return false;
		if (nrOfExtMsgs == null) {
			if (other.nrOfExtMsgs != null)
				return false;
		} else if (!nrOfExtMsgs.equals(other.nrOfExtMsgs))
			return false;
		if (nrOfMsgs == null) {
			if (other.nrOfMsgs != null)
				return false;
		} else if (!nrOfMsgs.equals(other.nrOfMsgs))
			return false;
		if (nrOfRjctMsgs == null) {
			if (other.nrOfRjctMsgs != null)
				return false;
		} else if (!nrOfRjctMsgs.equals(other.nrOfRjctMsgs))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (totalAccpMsgs == null) {
			if (other.totalAccpMsgs != null)
				return false;
		} else if (!totalAccpMsgs.equals(other.totalAccpMsgs))
			return false;
		if (totalNrOfExtMsgs == null) {
			if (other.totalNrOfExtMsgs != null)
				return false;
		} else if (!totalNrOfExtMsgs.equals(other.totalNrOfExtMsgs))
			return false;
		if (totalNrOfMsgs == null) {
			if (other.totalNrOfMsgs != null)
				return false;
		} else if (!totalNrOfMsgs.equals(other.totalNrOfMsgs))
			return false;
		if (totalRjctdMsgs == null) {
			if (other.totalRjctdMsgs != null)
				return false;
		} else if (!totalRjctdMsgs.equals(other.totalRjctdMsgs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MonthlyVolumeCountEntityModel [instId=" + instId + ", service=" + service + ", localInst="
				+ localInst + ", nrOfMsgs=" + nrOfMsgs + ", nrOfAccpMsgs=" + nrOfAccpMsgs + ", nrOfRjctMsgs="
				+ nrOfRjctMsgs + ", nrOfExtMsgs=" + nrOfExtMsgs + ", totalNrOfMsgs=" + totalNrOfMsgs
				+ ", totalAccpMsgs=" + totalAccpMsgs + ", totalRjctdMsgs=" + totalRjctdMsgs + ", totalNrOfExtMsgs="
				+ totalNrOfExtMsgs + "]";
	}

}
