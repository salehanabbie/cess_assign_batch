package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.jboss.marshalling.SerializabilityChecker;

/**
 * @author SalehaR
 *
 */
public class MandatesCountCommonsModelEntity implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String instId;
    private String serviceId;
    private BigDecimal nrOfFiles;
	private BigDecimal nrOfMsgs;
	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public BigDecimal getNrOfFiles() {
		return nrOfFiles;
	}
	public void setNrOfFiles(BigDecimal nrOfFiles) {
		this.nrOfFiles = nrOfFiles;
	}
	public BigDecimal getNrOfMsgs() {
		return nrOfMsgs;
	}
	public void setNrOfMsgs(BigDecimal nrOfMsgs) {
		this.nrOfMsgs = nrOfMsgs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result
				+ ((nrOfFiles == null) ? 0 : nrOfFiles.hashCode());
		result = prime * result
				+ ((nrOfMsgs == null) ? 0 : nrOfMsgs.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
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
		MandatesCountCommonsModelEntity other = (MandatesCountCommonsModelEntity) obj;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (nrOfFiles == null) {
			if (other.nrOfFiles != null)
				return false;
		} else if (!nrOfFiles.equals(other.nrOfFiles))
			return false;
		if (nrOfMsgs == null) {
			if (other.nrOfMsgs != null)
				return false;
		} else if (!nrOfMsgs.equals(other.nrOfMsgs))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MandatesCountCommonsModelEntity [instId=" + instId
				+ ", serviceId=" + serviceId + ", nrOfFiles=" + nrOfFiles
				+ ", nrOfMsgs=" + nrOfMsgs + "]";
	}

	
	
	
	
}
