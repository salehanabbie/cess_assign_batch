package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @author DimakatsoN
 *
 */

public class IamSessionModel implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private BigDecimal systemSeqNo;
	 private String sessionKey;
	 private Date sessionDate;
	 private String userName;
	 private String userRole;
	 
	public BigDecimal getSystemSeqNo() {
		return systemSeqNo;
	}
	public void setSystemSeqNo(BigDecimal systemSeqNo) {
		this.systemSeqNo = systemSeqNo;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public Date getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserRole()
	{
		return userRole;
	}
	
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sessionDate == null) ? 0 : sessionDate.hashCode());
		result = prime * result
				+ ((sessionKey == null) ? 0 : sessionKey.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userRole == null) ? 0 : userRole.hashCode());
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
		IamSessionModel other = (IamSessionModel) obj;
		if (sessionDate == null) {
			if (other.sessionDate != null)
				return false;
		} else if (!sessionDate.equals(other.sessionDate))
			return false;
		if (sessionKey == null) {
			if (other.sessionKey != null)
				return false;
		} else if (!sessionKey.equals(other.sessionKey))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userRole == null) {
			if (other.userRole != null)
				return false;
		} else if (!userRole.equals(other.userRole))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IamSessionModel [systemSeqNo=" + systemSeqNo + ", sessionKey="
				+ sessionKey + ", sessionDate=" + sessionDate + ", userName="
				+ userName + ", userRole=" + userRole + "]";
	}
	
	
	
	 
	 
}
