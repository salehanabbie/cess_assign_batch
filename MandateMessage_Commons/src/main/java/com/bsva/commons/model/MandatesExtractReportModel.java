package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @author DimakatsoN
 *
 */

public class MandatesExtractReportModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String serviceId;
	private BigDecimal nrMsgsExtracted;

	
	
	
	public MandatesExtractReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}




	public String getServiceId() {
		return serviceId;
	}




	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}




	public BigDecimal getNrMsgsExtracted() {
		return nrMsgsExtracted;
	}




	public void setNrMsgsExtracted(BigDecimal nrMsgsExtracted) {
		this.nrMsgsExtracted = nrMsgsExtracted;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nrMsgsExtracted == null) ? 0 : nrMsgsExtracted.hashCode());
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
		MandatesExtractReportModel other = (MandatesExtractReportModel) obj;
		if (nrMsgsExtracted == null) {
			if (other.nrMsgsExtracted != null)
				return false;
		} else if (!nrMsgsExtracted.equals(other.nrMsgsExtracted))
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
		return "MandatesExtractReportModel [serviceId=" + serviceId
				+ ", nrMsgsExtracted=" + nrMsgsExtracted + "]";
	}



	









	
	
	
	

}
