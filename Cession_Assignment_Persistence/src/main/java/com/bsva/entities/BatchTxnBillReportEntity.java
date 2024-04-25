	package com.bsva.entities;
	
	import java.io.Serializable;
import java.util.Objects;
	
	/**
	 * @author SalehaR
	 *
	 */
	public class BatchTxnBillReportEntity implements Serializable {
	 String processDate, serviceId, txnType, fileName, mrti, originator, delTime;

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMrti() {
		return mrti;
	}

	public void setMrti(String mrti) {
		this.mrti = mrti;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getDelTime() {
		return delTime;
	}

	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(delTime, fileName, mrti, originator, processDate, serviceId, txnType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchTxnBillReportEntity other = (BatchTxnBillReportEntity) obj;
		return Objects.equals(delTime, other.delTime) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(mrti, other.mrti) && Objects.equals(originator, other.originator)
				&& Objects.equals(processDate, other.processDate) && Objects.equals(serviceId, other.serviceId)
				&& Objects.equals(txnType, other.txnType);
	}

	@Override
	public String toString() {
		return "BatchTxnBillReportEntity [processDate=" + processDate + ", serviceId=" + serviceId + ", txnType="
				+ txnType + ", fileName=" + fileName + ", mrti=" + mrti + ", originator=" + originator + ", delTime="
				+ delTime + "]";
	}

}
