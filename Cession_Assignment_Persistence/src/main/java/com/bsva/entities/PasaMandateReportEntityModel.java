package com.bsva.entities;

import java.io.Serializable;

/**
 * @author SalehaR
 *
 */
public class PasaMandateReportEntityModel implements Serializable
{
		String mrti, msgId, creditorBank, debtorBank, creationDate, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm, status, errorCode, dataSource, processStatus, reason;
		String debtorBranch, authStatus;

		public String getMrti() {
			return mrti;
		}

		public void setMrti(String mrti) {
			this.mrti = mrti;
		}

		public String getMsgId() {
			return msgId;
		}

		public void setMsgId(String msgId) {
			this.msgId = msgId;
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

		public String getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(String creationDate) {
			this.creationDate = creationDate;
		}

		public String getAuthType() {
			return authType;
		}

		public void setAuthType(String authType) {
			this.authType = authType;
		}

		public String getContRefNum() {
			return contRefNum;
		}

		public void setContRefNum(String contRefNum) {
			this.contRefNum = contRefNum;
		}

		public String getDbtrAuthReq() {
			return dbtrAuthReq;
		}

		public void setDbtrAuthReq(String dbtrAuthReq) {
			this.dbtrAuthReq = dbtrAuthReq;
		}

		public String getInstOcc() {
			return instOcc;
		}

		public void setInstOcc(String instOcc) {
			this.instOcc = instOcc;
		}

		public String getCdtrAbbShtNm() {
			return cdtrAbbShtNm;
		}

		public void setCdtrAbbShtNm(String cdtrAbbShtNm) {
			this.cdtrAbbShtNm = cdtrAbbShtNm;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getDataSource() {
			return dataSource;
		}

		public void setDataSource(String dataSource) {
			this.dataSource = dataSource;
		}
		
		public String getProcessStatus() {
			return processStatus;
		}

		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}

		public String getDebtorBranch() {
			return debtorBranch;
		}

		public void setDebtorBranch(String debtorBranch) {
			this.debtorBranch = debtorBranch;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getAuthStatus() {
			return authStatus;
		}

		public void setAuthStatus(String authStatus) {
			this.authStatus = authStatus;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((authStatus == null) ? 0 : authStatus.hashCode());
			result = prime * result + ((authType == null) ? 0 : authType.hashCode());
			result = prime * result + ((cdtrAbbShtNm == null) ? 0 : cdtrAbbShtNm.hashCode());
			result = prime * result + ((contRefNum == null) ? 0 : contRefNum.hashCode());
			result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
			result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
			result = prime * result + ((dataSource == null) ? 0 : dataSource.hashCode());
			result = prime * result + ((dbtrAuthReq == null) ? 0 : dbtrAuthReq.hashCode());
			result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
			result = prime * result + ((debtorBranch == null) ? 0 : debtorBranch.hashCode());
			result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
			result = prime * result + ((instOcc == null) ? 0 : instOcc.hashCode());
			result = prime * result + ((mrti == null) ? 0 : mrti.hashCode());
			result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
			result = prime * result + ((processStatus == null) ? 0 : processStatus.hashCode());
			result = prime * result + ((reason == null) ? 0 : reason.hashCode());
			result = prime * result + ((status == null) ? 0 : status.hashCode());
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
			PasaMandateReportEntityModel other = (PasaMandateReportEntityModel) obj;
			if (authStatus == null) {
				if (other.authStatus != null)
					return false;
			} else if (!authStatus.equals(other.authStatus))
				return false;
			if (authType == null) {
				if (other.authType != null)
					return false;
			} else if (!authType.equals(other.authType))
				return false;
			if (cdtrAbbShtNm == null) {
				if (other.cdtrAbbShtNm != null)
					return false;
			} else if (!cdtrAbbShtNm.equals(other.cdtrAbbShtNm))
				return false;
			if (contRefNum == null) {
				if (other.contRefNum != null)
					return false;
			} else if (!contRefNum.equals(other.contRefNum))
				return false;
			if (creationDate == null) {
				if (other.creationDate != null)
					return false;
			} else if (!creationDate.equals(other.creationDate))
				return false;
			if (creditorBank == null) {
				if (other.creditorBank != null)
					return false;
			} else if (!creditorBank.equals(other.creditorBank))
				return false;
			if (dataSource == null) {
				if (other.dataSource != null)
					return false;
			} else if (!dataSource.equals(other.dataSource))
				return false;
			if (dbtrAuthReq == null) {
				if (other.dbtrAuthReq != null)
					return false;
			} else if (!dbtrAuthReq.equals(other.dbtrAuthReq))
				return false;
			if (debtorBank == null) {
				if (other.debtorBank != null)
					return false;
			} else if (!debtorBank.equals(other.debtorBank))
				return false;
			if (debtorBranch == null) {
				if (other.debtorBranch != null)
					return false;
			} else if (!debtorBranch.equals(other.debtorBranch))
				return false;
			if (errorCode == null) {
				if (other.errorCode != null)
					return false;
			} else if (!errorCode.equals(other.errorCode))
				return false;
			if (instOcc == null) {
				if (other.instOcc != null)
					return false;
			} else if (!instOcc.equals(other.instOcc))
				return false;
			if (mrti == null) {
				if (other.mrti != null)
					return false;
			} else if (!mrti.equals(other.mrti))
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
			if (reason == null) {
				if (other.reason != null)
					return false;
			} else if (!reason.equals(other.reason))
				return false;
			if (status == null) {
				if (other.status != null)
					return false;
			} else if (!status.equals(other.status))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "PasaMandateReportEntityModel [mrti=" + mrti + ", msgId=" + msgId + ", creditorBank=" + creditorBank
					+ ", debtorBank=" + debtorBank + ", creationDate=" + creationDate + ", authType=" + authType
					+ ", contRefNum=" + contRefNum + ", dbtrAuthReq=" + dbtrAuthReq + ", instOcc=" + instOcc
					+ ", cdtrAbbShtNm=" + cdtrAbbShtNm + ", status=" + status + ", errorCode=" + errorCode
					+ ", dataSource=" + dataSource + ", processStatus=" + processStatus + ", reason=" + reason
					+ ", debtorBranch=" + debtorBranch + ", authStatus=" + authStatus + "]";
		}
}
