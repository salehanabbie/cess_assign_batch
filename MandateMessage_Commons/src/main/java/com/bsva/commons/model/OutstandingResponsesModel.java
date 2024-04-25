package com.bsva.commons.model;


import java.io.Serializable;

/**
* 
 * @author NhlanhlaM
*
*/
public class OutstandingResponsesModel implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mandateReqTranId;
	private String mandateReqId;
	private String mandateRefNr;
	private String serviceId;
	private String bankName;
	private String creditorMemberNo;

	private String fileName;
	
	public String getMandateReqTranId() {
		return mandateReqTranId;
	}
	public void setMandateReqTranId(String mandateReqTranId) {
		this.mandateReqTranId = mandateReqTranId;
	}
	public String getMandateReqId() {
		return mandateReqId;
	}
	public void setMandateReqId(String mandateReqId) {
		this.mandateReqId = mandateReqId;
	}
	public String getMandateRefNr() {
		return mandateRefNr;
	}
	public void setMandateRefNr(String mandateRefNr) {
		this.mandateRefNr = mandateRefNr;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCreditorMemberNo() {
		return creditorMemberNo;
	}
	public void setCreditorMemberNo(String creditorMemberNo) {
		this.creditorMemberNo = creditorMemberNo;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bankName == null) ? 0 : bankName.hashCode());
		result = prime
				* result
				+ ((creditorMemberNo == null) ? 0 : creditorMemberNo.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((mandateRefNr == null) ? 0 : mandateRefNr.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime
				* result
				+ ((mandateReqTranId == null) ? 0 : mandateReqTranId.hashCode());
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
		OutstandingResponsesModel other = (OutstandingResponsesModel) obj;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (creditorMemberNo == null) {
			if (other.creditorMemberNo != null)
				return false;
		} else if (!creditorMemberNo.equals(other.creditorMemberNo))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (mandateRefNr == null) {
			if (other.mandateRefNr != null)
				return false;
		} else if (!mandateRefNr.equals(other.mandateRefNr))
			return false;
		if (mandateReqId == null) {
			if (other.mandateReqId != null)
				return false;
		} else if (!mandateReqId.equals(other.mandateReqId))
			return false;
		if (mandateReqTranId == null) {
			if (other.mandateReqTranId != null)
				return false;
		} else if (!mandateReqTranId.equals(other.mandateReqTranId))
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
		return "OutstandingResponsesModel [mandateReqTranId="
				+ mandateReqTranId + ", mandateReqId=" + mandateReqId
				+ ", mandateRefNr=" + mandateRefNr + ", serviceId=" + serviceId
				+ ", bankName=" + bankName + ", creditorMemberNo="
				+ creditorMemberNo + ", fileName=" + fileName + "]";
	}
	
	
	
}

