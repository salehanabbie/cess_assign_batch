package com.bsva.commons.model;

import java.io.Serializable;

/**
 * 
 * @author DimakatsoN
 *
 */
public class StatusReportEotModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int numberOfFiles;
	private String instrAgnt;
	private String serviceId;
	
	
	public StatusReportEotModel() {
	
	}
	

	public int getNumberOfFiles() {
		return numberOfFiles;
	}


	public void setNumberOfFiles(int numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}


	public String getInstrAgnt() {
		return instrAgnt;
	}

	public void setInstrAgnt(String instrAgnt) {
		this.instrAgnt = instrAgnt;
	}

	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instrAgnt == null) ? 0 : instrAgnt.hashCode());
		result = prime * result + numberOfFiles;
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
		StatusReportEotModel other = (StatusReportEotModel) obj;
		if (instrAgnt == null) {
			if (other.instrAgnt != null)
				return false;
		} else if (!instrAgnt.equals(other.instrAgnt))
			return false;
		if (numberOfFiles != other.numberOfFiles)
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
		return "StatusReportEotModel [numberOfFiles=" + numberOfFiles
				+ ", instrAgnt=" + instrAgnt + ", serviceId=" + serviceId + "]";
	}
	
	
	

}
