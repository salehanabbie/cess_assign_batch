package com.bsva.entities;

import java.io.Serializable;

/**
 * 
 * @author DimakatsoN
 *
 */

public class CasOpsSotEotEntityModel implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sotOut;
	 private String eotOut;
	 private String sotIn;
	 private String eotIn;
	 private String serviceId;
	 private String instId;
	 
	public CasOpsSotEotEntityModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSotOut() {
		return sotOut;
	}

	public void setSotOut(String sotOut) {
		this.sotOut = sotOut;
	}

	public String getEotOut() {
		return eotOut;
	}

	public void setEotOut(String eotOut) {
		this.eotOut = eotOut;
	}

	public String getSotIn() {
		return sotIn;
	}

	public void setSotIn(String sotIn) {
		this.sotIn = sotIn;
	}

	public String getEotIn() {
		return eotIn;
	}

	public void setEotIn(String eotIn) {
		this.eotIn = eotIn;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eotIn == null) ? 0 : eotIn.hashCode());
		result = prime * result + ((eotOut == null) ? 0 : eotOut.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((sotIn == null) ? 0 : sotIn.hashCode());
		result = prime * result + ((sotOut == null) ? 0 : sotOut.hashCode());
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
		CasOpsSotEotEntityModel other = (CasOpsSotEotEntityModel) obj;
		if (eotIn == null) {
			if (other.eotIn != null)
				return false;
		} else if (!eotIn.equals(other.eotIn))
			return false;
		if (eotOut == null) {
			if (other.eotOut != null)
				return false;
		} else if (!eotOut.equals(other.eotOut))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (sotIn == null) {
			if (other.sotIn != null)
				return false;
		} else if (!sotIn.equals(other.sotIn))
			return false;
		if (sotOut == null) {
			if (other.sotOut != null)
				return false;
		} else if (!sotOut.equals(other.sotOut))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return serviceId;
	}
	 
	
	 
	 

}
