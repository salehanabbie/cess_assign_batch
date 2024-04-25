package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */
public class MandateResponseOutstandingPerBankEntityModel implements Serializable 
{
	private String crdMemberName;
	private String crdMemberNo;
	private String fileName;
	private String transType;
	private BigDecimal nrDaysOutstanding;
	private String  mandateReqTransId;
	private String serviceId;
	private Date createdDate;
	private String dbtrMemberName;
	private String dbtrMemberNo;

	public String getCrdMemberName() {
		return crdMemberName;
	}
	public void setCrdMemberName(String crdMemberName) {
		this.crdMemberName = crdMemberName;
	}
	public String getCrdMemberNo() {
		return crdMemberNo;
	}
	public void setCrdMemberNo(String crdMemberNo) {
		this.crdMemberNo = crdMemberNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getNrDaysOutstanding() {
		return nrDaysOutstanding;
	}
	public void setNrDaysOutstanding(BigDecimal nrDaysOutstanding) {
		this.nrDaysOutstanding = nrDaysOutstanding;
	}
	public String getMandateReqTransId() {
		return mandateReqTransId;
	}
	public void setMandateReqTransId(String mandateReqTransId) {
		this.mandateReqTransId = mandateReqTransId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDbtrMemberName() {
		return dbtrMemberName;
	}
	public void setDbtrMemberName(String dbtrMemberName) {
		this.dbtrMemberName = dbtrMemberName;
	}
	public String getDbtrMemberNo() {
		return dbtrMemberNo;
	}
	public void setDbtrMemberNo(String dbtrMemberNo) {
		this.dbtrMemberNo = dbtrMemberNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crdMemberName == null) ? 0 : crdMemberName.hashCode());
		result = prime * result + ((crdMemberNo == null) ? 0 : crdMemberNo.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((dbtrMemberName == null) ? 0 : dbtrMemberName.hashCode());
		result = prime * result + ((dbtrMemberNo == null) ? 0 : dbtrMemberNo.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((mandateReqTransId == null) ? 0 : mandateReqTransId.hashCode());
		result = prime * result + ((nrDaysOutstanding == null) ? 0 : nrDaysOutstanding.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((transType == null) ? 0 : transType.hashCode());
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
		MandateResponseOutstandingPerBankEntityModel other = (MandateResponseOutstandingPerBankEntityModel) obj;
		if (crdMemberName == null) {
			if (other.crdMemberName != null)
				return false;
		} else if (!crdMemberName.equals(other.crdMemberName))
			return false;
		if (crdMemberNo == null) {
			if (other.crdMemberNo != null)
				return false;
		} else if (!crdMemberNo.equals(other.crdMemberNo))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (dbtrMemberName == null) {
			if (other.dbtrMemberName != null)
				return false;
		} else if (!dbtrMemberName.equals(other.dbtrMemberName))
			return false;
		if (dbtrMemberNo == null) {
			if (other.dbtrMemberNo != null)
				return false;
		} else if (!dbtrMemberNo.equals(other.dbtrMemberNo))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (mandateReqTransId == null) {
			if (other.mandateReqTransId != null)
				return false;
		} else if (!mandateReqTransId.equals(other.mandateReqTransId))
			return false;
		if (nrDaysOutstanding == null) {
			if (other.nrDaysOutstanding != null)
				return false;
		} else if (!nrDaysOutstanding.equals(other.nrDaysOutstanding))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (transType == null) {
			if (other.transType != null)
				return false;
		} else if (!transType.equals(other.transType))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MandateResponseOutstandingPerBankEntityModel [crdMemberName=" + crdMemberName + ", crdMemberNo="
				+ crdMemberNo + ", fileName=" + fileName + ", transType=" + transType + ", nrDaysOutstanding="
				+ nrDaysOutstanding + ", mandateReqTransId=" + mandateReqTransId + ", serviceId=" + serviceId
				+ ", createdDate=" + createdDate + ", dbtrMemberName=" + dbtrMemberName + ", dbtrMemberNo="
				+ dbtrMemberNo + "]";
	}
}
