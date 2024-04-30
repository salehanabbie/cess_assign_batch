package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author DimakatsoN
 *
 */

public class MandatesRejectedReportEntityModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceId;
	private BigDecimal nrMsgsRejected;
	
	
	public MandatesRejectedReportEntityModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public BigDecimal getNrMsgsRejected() {
		return nrMsgsRejected;
	}


	public void setNrMsgsRejected(BigDecimal nrMsgsRejected) {
		this.nrMsgsRejected = nrMsgsRejected;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nrMsgsRejected == null) ? 0 : nrMsgsRejected.hashCode());
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
		MandatesRejectedReportEntityModel other = (MandatesRejectedReportEntityModel) obj;
		if (nrMsgsRejected == null) {
			if (other.nrMsgsRejected != null)
				return false;
		} else if (!nrMsgsRejected.equals(other.nrMsgsRejected))
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
		return "MandatesRejectedReportEntityModel [serviceId=" + serviceId
				+ ", nrMsgsRejected=" + nrMsgsRejected + "]";
	}


	

	
}
