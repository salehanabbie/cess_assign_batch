package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SalehaR
 *
 */
public class WebOpsStatusDetailsModel implements Serializable 
{
    
    private BigDecimal systemSeqNo;
    private BigDecimal statusHdrSeqNo;
    private String errorCode;
    private String txnId;
    private String endToEndId;
    private String txnStatus;
    private String errorType;
    private String recordId;
    private BigDecimal orgnlTxnSeqNo;
    private String mandateRefNumber;
    private String instId;
    private String processStatus;
    
	public BigDecimal getSystemSeqNo() {
		return systemSeqNo;
	}
	public void setSystemSeqNo(BigDecimal systemSeqNo) {
		this.systemSeqNo = systemSeqNo;
	}
	public BigDecimal getStatusHdrSeqNo() {
		return statusHdrSeqNo;
	}
	public void setStatusHdrSeqNo(BigDecimal statusHdrSeqNo) {
		this.statusHdrSeqNo = statusHdrSeqNo;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getEndToEndId() {
		return endToEndId;
	}
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public BigDecimal getOrgnlTxnSeqNo() {
		return orgnlTxnSeqNo;
	}
	public void setOrgnlTxnSeqNo(BigDecimal orgnlTxnSeqNo) {
		this.orgnlTxnSeqNo = orgnlTxnSeqNo;
	}
	public String getMandateRefNumber() {
		return mandateRefNumber;
	}
	public void setMandateRefNumber(String mandateRefNumber) {
		this.mandateRefNumber = mandateRefNumber;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endToEndId == null) ? 0 : endToEndId.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((mandateRefNumber == null) ? 0 : mandateRefNumber.hashCode());
		result = prime * result + ((orgnlTxnSeqNo == null) ? 0 : orgnlTxnSeqNo.hashCode());
		result = prime * result + ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result + ((statusHdrSeqNo == null) ? 0 : statusHdrSeqNo.hashCode());
		result = prime * result + ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
		result = prime * result + ((txnStatus == null) ? 0 : txnStatus.hashCode());
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
		WebOpsStatusDetailsModel other = (WebOpsStatusDetailsModel) obj;
		if (endToEndId == null) {
			if (other.endToEndId != null)
				return false;
		} else if (!endToEndId.equals(other.endToEndId))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorType == null) {
			if (other.errorType != null)
				return false;
		} else if (!errorType.equals(other.errorType))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (mandateRefNumber == null) {
			if (other.mandateRefNumber != null)
				return false;
		} else if (!mandateRefNumber.equals(other.mandateRefNumber))
			return false;
		if (orgnlTxnSeqNo == null) {
			if (other.orgnlTxnSeqNo != null)
				return false;
		} else if (!orgnlTxnSeqNo.equals(other.orgnlTxnSeqNo))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		if (statusHdrSeqNo == null) {
			if (other.statusHdrSeqNo != null)
				return false;
		} else if (!statusHdrSeqNo.equals(other.statusHdrSeqNo))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (txnId == null) {
			if (other.txnId != null)
				return false;
		} else if (!txnId.equals(other.txnId))
			return false;
		if (txnStatus == null) {
			if (other.txnStatus != null)
				return false;
		} else if (!txnStatus.equals(other.txnStatus))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WebOpsStatusDetailsModel [systemSeqNo=" + systemSeqNo + ", statusHdrSeqNo=" + statusHdrSeqNo
				+ ", errorCode=" + errorCode + ", txnId=" + txnId + ", endToEndId=" + endToEndId + ", txnStatus="
				+ txnStatus + ", errorType=" + errorType + ", recordId=" + recordId + ", orgnlTxnSeqNo=" + orgnlTxnSeqNo
				+ ", mandateRefNumber=" + mandateRefNumber + ", instId=" + instId + ", processStatus=" + processStatus
				+ "]";
	}
    
	
	/*public BigDecimal getSystemSeqNo() {
		return systemSeqNo;
	}
	public void setSystemSeqNo(BigDecimal systemSeqNo) {
		this.systemSeqNo = systemSeqNo;
	}
	public BigDecimal getStatusHdrSeqNo() {
		return statusHdrSeqNo;
	}
	public void setStatusHdrSeqNo(BigDecimal statusHdrSeqNo) {
		this.statusHdrSeqNo = statusHdrSeqNo;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getEndToEndId() {
		return endToEndId;
	}
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public BigDecimal getOrgnlTxnSeqNo() {
		return orgnlTxnSeqNo;
	}
	public void setOrgnlTxnSeqNo(BigDecimal orgnlTxnSeqNo) {
		this.orgnlTxnSeqNo = orgnlTxnSeqNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endToEndId == null) ? 0 : endToEndId.hashCode());
		result = prime * result
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result
				+ ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result
				+ ((orgnlTxnSeqNo == null) ? 0 : orgnlTxnSeqNo.hashCode());
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result
				+ ((statusHdrSeqNo == null) ? 0 : statusHdrSeqNo.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
		result = prime * result
				+ ((txnStatus == null) ? 0 : txnStatus.hashCode());
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
		WebOpsStatusDetailsModel other = (WebOpsStatusDetailsModel) obj;
		if (endToEndId == null) {
			if (other.endToEndId != null)
				return false;
		} else if (!endToEndId.equals(other.endToEndId))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorType == null) {
			if (other.errorType != null)
				return false;
		} else if (!errorType.equals(other.errorType))
			return false;
		if (orgnlTxnSeqNo == null) {
			if (other.orgnlTxnSeqNo != null)
				return false;
		} else if (!orgnlTxnSeqNo.equals(other.orgnlTxnSeqNo))
			return false;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		if (statusHdrSeqNo == null) {
			if (other.statusHdrSeqNo != null)
				return false;
		} else if (!statusHdrSeqNo.equals(other.statusHdrSeqNo))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (txnId == null) {
			if (other.txnId != null)
				return false;
		} else if (!txnId.equals(other.txnId))
			return false;
		if (txnStatus == null) {
			if (other.txnStatus != null)
				return false;
		} else if (!txnStatus.equals(other.txnStatus))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "WebOpsStatusDetailsModel [systemSeqNo=" + systemSeqNo
				+ ", statusHdrSeqNo=" + statusHdrSeqNo + ", errorCode="
				+ errorCode + ", txnId=" + txnId + ", endToEndId=" + endToEndId
				+ ", txnStatus=" + txnStatus + ", errorType=" + errorType
				+ ", recordId=" + recordId + ", orgnlTxnSeqNo=" + orgnlTxnSeqNo
				+ "]";
	}
	*/
	
	
}
