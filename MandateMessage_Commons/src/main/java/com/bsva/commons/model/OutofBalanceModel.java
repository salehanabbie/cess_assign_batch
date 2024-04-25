package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OutofBalanceModel implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;

	
	private String serviceId;
   private BigDecimal nrMsgsAccepted;
	
	
	
	public OutofBalanceModel()
	{
		super();
	}



	public String getServiceId() {
		return serviceId;
	}



	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}



	public BigDecimal getNrMsgsAccepted() {
		return nrMsgsAccepted;
	}



	public void setNrMsgsAccepted(BigDecimal nrMsgsAccepted) {
		this.nrMsgsAccepted = nrMsgsAccepted;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nrMsgsAccepted == null) ? 0 : nrMsgsAccepted.hashCode());
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
		OutofBalanceModel other = (OutofBalanceModel) obj;
		if (nrMsgsAccepted == null) {
			if (other.nrMsgsAccepted != null)
				return false;
		} else if (!nrMsgsAccepted.equals(other.nrMsgsAccepted))
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
		return "OutofBalanceModel [serviceId=" + serviceId
				+ ", nrMsgsAccepted=" + nrMsgsAccepted + "]";
	}






	


	

	
	

}
