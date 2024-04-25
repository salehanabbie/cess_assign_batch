package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class MandateRegModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String msgId;
    private String mandateId;
    private String mandateReqId;
    private String sequenceType;
    private String frequency;
    private Date fromDate;
    private Date toDate;
    private Date firstCollDate;
    private Date finalCollDate;
    private String collCurrency;
    private BigInteger collAmount;
    private String maxAmountCurr;
    private BigDecimal maxAmount;
    private String localInstrCd;
    private String serviceLevel;
    private String activeInd;
    private Date activeIndChangeDate;
    private String fileName;
    private Date fileDate;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String processStatus;
    private String modReason;
    private String debtorName;
    private String creditorName;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMandateId() {
		return mandateId;
	}
	public void setMandateId(String mandateId) {
		this.mandateId = mandateId;
	}
	public String getMandateReqId() {
		return mandateReqId;
	}
	public void setMandateReqId(String mandateReqId) {
		this.mandateReqId = mandateReqId;
	}
	public String getSequenceType() {
		return sequenceType;
	}
	public void setSequenceType(String sequenceType) {
		this.sequenceType = sequenceType;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getFirstCollDate() {
		return firstCollDate;
	}
	public void setFirstCollDate(Date firstCollDate) {
		this.firstCollDate = firstCollDate;
	}
	public Date getFinalCollDate() {
		return finalCollDate;
	}
	public void setFinalCollDate(Date finalCollDate) {
		this.finalCollDate = finalCollDate;
	}
	public String getCollCurrency() {
		return collCurrency;
	}
	public void setCollCurrency(String collCurrency) {
		this.collCurrency = collCurrency;
	}
	public BigInteger getCollAmount() {
		return collAmount;
	}
	public void setCollAmount(BigInteger collAmount) {
		this.collAmount = collAmount;
	}
	public String getMaxAmountCurr() {
		return maxAmountCurr;
	}
	public void setMaxAmountCurr(String maxAmountCurr) {
		this.maxAmountCurr = maxAmountCurr;
	}
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}
	public String getLocalInstrCd() {
		return localInstrCd;
	}
	public void setLocalInstrCd(String localInstrCd) {
		this.localInstrCd = localInstrCd;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}
	public Date getActiveIndChangeDate() {
		return activeIndChangeDate;
	}
	public void setActiveIndChangeDate(Date activeIndChangeDate) {
		this.activeIndChangeDate = activeIndChangeDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getModReason() {
		return modReason;
	}
	public void setModReason(String modReason) {
		this.modReason = modReason;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime
				* result
				+ ((activeIndChangeDate == null) ? 0 : activeIndChangeDate
						.hashCode());
		result = prime * result
				+ ((collAmount == null) ? 0 : collAmount.hashCode());
		result = prime * result
				+ ((collCurrency == null) ? 0 : collCurrency.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((creditorName == null) ? 0 : creditorName.hashCode());
		result = prime * result
				+ ((debtorName == null) ? 0 : debtorName.hashCode());
		result = prime * result
				+ ((fileDate == null) ? 0 : fileDate.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((finalCollDate == null) ? 0 : finalCollDate.hashCode());
		result = prime * result
				+ ((firstCollDate == null) ? 0 : firstCollDate.hashCode());
		result = prime * result
				+ ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result
				+ ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result
				+ ((localInstrCd == null) ? 0 : localInstrCd.hashCode());
		result = prime * result
				+ ((mandateId == null) ? 0 : mandateId.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
		result = prime * result
				+ ((maxAmount == null) ? 0 : maxAmount.hashCode());
		result = prime * result
				+ ((maxAmountCurr == null) ? 0 : maxAmountCurr.hashCode());
		result = prime * result
				+ ((modReason == null) ? 0 : modReason.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result
				+ ((sequenceType == null) ? 0 : sequenceType.hashCode());
		result = prime * result
				+ ((serviceLevel == null) ? 0 : serviceLevel.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
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
		MandateRegModel other = (MandateRegModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (activeIndChangeDate == null) {
			if (other.activeIndChangeDate != null)
				return false;
		} else if (!activeIndChangeDate.equals(other.activeIndChangeDate))
			return false;
		if (collAmount == null) {
			if (other.collAmount != null)
				return false;
		} else if (!collAmount.equals(other.collAmount))
			return false;
		if (collCurrency == null) {
			if (other.collCurrency != null)
				return false;
		} else if (!collCurrency.equals(other.collCurrency))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (creditorName == null) {
			if (other.creditorName != null)
				return false;
		} else if (!creditorName.equals(other.creditorName))
			return false;
		if (debtorName == null) {
			if (other.debtorName != null)
				return false;
		} else if (!debtorName.equals(other.debtorName))
			return false;
		if (fileDate == null) {
			if (other.fileDate != null)
				return false;
		} else if (!fileDate.equals(other.fileDate))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (finalCollDate == null) {
			if (other.finalCollDate != null)
				return false;
		} else if (!finalCollDate.equals(other.finalCollDate))
			return false;
		if (firstCollDate == null) {
			if (other.firstCollDate != null)
				return false;
		} else if (!firstCollDate.equals(other.firstCollDate))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (localInstrCd == null) {
			if (other.localInstrCd != null)
				return false;
		} else if (!localInstrCd.equals(other.localInstrCd))
			return false;
		if (mandateId == null) {
			if (other.mandateId != null)
				return false;
		} else if (!mandateId.equals(other.mandateId))
			return false;
		if (mandateReqId == null) {
			if (other.mandateReqId != null)
				return false;
		} else if (!mandateReqId.equals(other.mandateReqId))
			return false;
		if (maxAmount == null) {
			if (other.maxAmount != null)
				return false;
		} else if (!maxAmount.equals(other.maxAmount))
			return false;
		if (maxAmountCurr == null) {
			if (other.maxAmountCurr != null)
				return false;
		} else if (!maxAmountCurr.equals(other.maxAmountCurr))
			return false;
		if (modReason == null) {
			if (other.modReason != null)
				return false;
		} else if (!modReason.equals(other.modReason))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (sequenceType == null) {
			if (other.sequenceType != null)
				return false;
		} else if (!sequenceType.equals(other.sequenceType))
			return false;
		if (serviceLevel == null) {
			if (other.serviceLevel != null)
				return false;
		} else if (!serviceLevel.equals(other.serviceLevel))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MandateRegModel [msgId=" + msgId + ", mandateId=" + mandateId
				+ ", mandateReqId=" + mandateReqId + ", sequenceType="
				+ sequenceType + ", frequency=" + frequency + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", firstCollDate="
				+ firstCollDate + ", finalCollDate=" + finalCollDate
				+ ", collCurrency=" + collCurrency + ", collAmount="
				+ collAmount + ", maxAmountCurr=" + maxAmountCurr
				+ ", maxAmount=" + maxAmount + ", localInstrCd=" + localInstrCd
				+ ", serviceLevel=" + serviceLevel + ", activeInd=" + activeInd
				+ ", activeIndChangeDate=" + activeIndChangeDate
				+ ", fileName=" + fileName + ", fileDate=" + fileDate
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", processStatus=" + processStatus
				+ ", modReason=" + modReason + ", debtorName=" + debtorName
				+ ", creditorName=" + creditorName + "]";
	}
	
	
	

}
