package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author SalehaR
 *
 */
public class AudSystemProcessModel  implements Serializable
{
	BigDecimal systemSeqNo;
	String process, userId;
	Date processDate;
	
	public BigDecimal getSystemSeqNo() 
	{
		return systemSeqNo;
	}
	
	public void setSystemSeqNo(BigDecimal systemSeqNo) 
	{
		this.systemSeqNo = systemSeqNo;
	}
	
	public String getProcess() {
		return process;
	}
	
	public void setProcess(String process) {
		this.process = process;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Date getProcessDate() {
		return processDate;
	}
	
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((process == null) ? 0 : process.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		AudSystemProcessModel other = (AudSystemProcessModel) obj;
		if (process == null) {
			if (other.process != null)
				return false;
		} else if (!process.equals(other.process))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AudSystemProcessModel [systemSeqNo=" + systemSeqNo
				+ ", process=" + process + ", userId=" + userId
				+ ", processDate=" + processDate + "]";
	}
	
	
	
	
	
	
	
	

}
