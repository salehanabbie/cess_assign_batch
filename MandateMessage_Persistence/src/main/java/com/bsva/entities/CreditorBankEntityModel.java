package com.bsva.entities;

import java.io.Serializable;

/**
 * 
 * @author DimakatsoN
 *
 */

public class CreditorBankEntityModel implements Serializable {
	
	private String memberNo;
	private String memberName;
	
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
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
		CreditorBankEntityModel other = (CreditorBankEntityModel) obj;
		if (memberName == null) {
			if (other.memberName != null)
				return false;
		} else if (!memberName.equals(other.memberName))
			return false;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CreditorBankEntityModel [memberNo=" + memberNo
				+ ", memberName=" + memberName + "]";
	}
	
	
	
	

}
