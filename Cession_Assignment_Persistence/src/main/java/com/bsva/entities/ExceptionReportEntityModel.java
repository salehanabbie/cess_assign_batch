package com.bsva.entities;

import java.io.Serializable;

public class ExceptionReportEntityModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String instructingAgent, serviceId, fileName, errorCode, mndtRefNr, mrti, credAbbShrtNm, drBranchNr;

	public String getInstructingAgent() {
		return instructingAgent;
	}

	public void setInstructingAgent(String instructingAgent) {
		this.instructingAgent = instructingAgent;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMndtRefNr() {
		return mndtRefNr;
	}

	public void setMndtRefNr(String mndtRefNr) {
		this.mndtRefNr = mndtRefNr;
	}

	public String getMrti() {
		return mrti;
	}

	public void setMrti(String mrti) {
		this.mrti = mrti;
	}

	public String getCredAbbShrtNm() {
		return credAbbShrtNm;
	}

	public void setCredAbbShrtNm(String credAbbShrtNm) {
		this.credAbbShrtNm = credAbbShrtNm;
	}

	public String getDrBranchNr() {
		return drBranchNr;
	}

	public void setDrBranchNr(String drBranchNr) {
		this.drBranchNr = drBranchNr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((credAbbShrtNm == null) ? 0 : credAbbShrtNm.hashCode());
		result = prime * result + ((drBranchNr == null) ? 0 : drBranchNr.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((instructingAgent == null) ? 0 : instructingAgent.hashCode());
		result = prime * result + ((mndtRefNr == null) ? 0 : mndtRefNr.hashCode());
		result = prime * result + ((mrti == null) ? 0 : mrti.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
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
		ExceptionReportEntityModel other = (ExceptionReportEntityModel) obj;
		if (credAbbShrtNm == null) {
			if (other.credAbbShrtNm != null)
				return false;
		} else if (!credAbbShrtNm.equals(other.credAbbShrtNm))
			return false;
		if (drBranchNr == null) {
			if (other.drBranchNr != null)
				return false;
		} else if (!drBranchNr.equals(other.drBranchNr))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (instructingAgent == null) {
			if (other.instructingAgent != null)
				return false;
		} else if (!instructingAgent.equals(other.instructingAgent))
			return false;
		if (mndtRefNr == null) {
			if (other.mndtRefNr != null)
				return false;
		} else if (!mndtRefNr.equals(other.mndtRefNr))
			return false;
		if (mrti == null) {
			if (other.mrti != null)
				return false;
		} else if (!mrti.equals(other.mrti))
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
		return "ExceptionReportEntityModel [instructingAgent=" + instructingAgent + ", serviceId=" + serviceId
				+ ", fileName=" + fileName + ", errorCode=" + errorCode + ", mndtRefNr=" + mndtRefNr + ", mrti=" + mrti
				+ ", credAbbShrtNm=" + credAbbShrtNm + ", drBranchNr=" + drBranchNr + "]";
	}
	
	

}
