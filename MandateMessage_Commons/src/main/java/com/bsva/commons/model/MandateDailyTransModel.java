package com.bsva.commons.model;

import java.io.Serializable;

public class MandateDailyTransModel implements Serializable  {
	
	private String serviceId;
	private String creditorBank;
	private String debtorBank;
	private String extractMsgId;
	private String actionDate;
	private String mndtReqId;
	private String mndtReqTransId;
	private String mndtRefNumber;
	private String authCode;
	private String trxnStatus;
	private String txnType;
	private String respDate;
	
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getCreditorBank() {
		return creditorBank;
	}
	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public String getExtractMsgId() {
		return extractMsgId;
	}
	public void setExtractMsgId(String extractMsgId) {
		this.extractMsgId = extractMsgId;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getMndtReqId() {
		return mndtReqId;
	}
	public void setMndtReqId(String mndtReqId) {
		this.mndtReqId = mndtReqId;

	}
	public String getMndtReqTransId() {
		return mndtReqTransId;
	}
	public void setMndtReqTransId(String mndtReqTransId) {
		this.mndtReqTransId = mndtReqTransId;
	}

	public String getMndtRefNumber() {
		return mndtRefNumber;
	}
	public void setMndtRefNumber(String mndtRefNumber) {
		this.mndtRefNumber = mndtRefNumber;
	}

	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getTrxnStatus() {
		return trxnStatus;
	}
	public void setTrxnStatus(String trxnStatus) {
		this.trxnStatus = trxnStatus;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getRespDate() {
		return respDate;
	}
	public void setRespDate(String respDate) {
		this.respDate = respDate;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionDate == null) ? 0 : actionDate.hashCode());
		result = prime * result + ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
		result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + ((extractMsgId == null) ? 0 : extractMsgId.hashCode());
		result = prime * result + ((mndtRefNumber == null) ? 0 : mndtRefNumber.hashCode());
		result = prime * result + ((mndtReqId == null) ? 0 : mndtReqId.hashCode());
		result = prime * result + ((mndtReqTransId == null) ? 0 : mndtReqTransId.hashCode());
		result = prime * result + ((respDate == null) ? 0 : respDate.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((trxnStatus == null) ? 0 : trxnStatus.hashCode());
		result = prime * result + ((txnType == null) ? 0 : txnType.hashCode());
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
		MandateDailyTransModel other = (MandateDailyTransModel) obj;
		if (actionDate == null) {
			if (other.actionDate != null)
				return false;
		} else if (!actionDate.equals(other.actionDate))
			return false;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (creditorBank == null) {
			if (other.creditorBank != null)
				return false;
		} else if (!creditorBank.equals(other.creditorBank))
			return false;
		if (debtorBank == null) {
			if (other.debtorBank != null)
				return false;
		} else if (!debtorBank.equals(other.debtorBank))
			return false;
		if (extractMsgId == null) {
			if (other.extractMsgId != null)
				return false;
		} else if (!extractMsgId.equals(other.extractMsgId))
			return false;
		if (mndtRefNumber == null) {
			if (other.mndtRefNumber != null)
				return false;
		} else if (!mndtRefNumber.equals(other.mndtRefNumber))
			return false;
		if (mndtReqId == null) {
			if (other.mndtReqId != null)
				return false;
		} else if (!mndtReqId.equals(other.mndtReqId))
			return false;
		if (mndtReqTransId == null) {
			if (other.mndtReqTransId != null)
				return false;
		} else if (!mndtReqTransId.equals(other.mndtReqTransId))
			return false;
		if (respDate == null) {
			if (other.respDate != null)
				return false;
		} else if (!respDate.equals(other.respDate))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (trxnStatus == null) {
			if (other.trxnStatus != null)
				return false;
		} else if (!trxnStatus.equals(other.trxnStatus))
			return false;
		if (txnType == null) {
			if (other.txnType != null)
				return false;
		} else if (!txnType.equals(other.txnType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MandateDailyTransModel [serviceId=" + serviceId + ", creditorBank=" + creditorBank + ", debtorBank="
				+ debtorBank + ", extractMsgId=" + extractMsgId + ", actionDate=" + actionDate + ", mndtReqId="
				+ mndtReqId + ", mndtReqTransId=" + mndtReqTransId + ", mndtRefNumber=" + mndtRefNumber + ", authCode="
				+ authCode + ", trxnStatus=" + trxnStatus + ", txnType=" + txnType + ", respDate=" + respDate + "]";
	}

}
