package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

public class WebCreditorModel implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;

	private String credId;
	private String credBank;
	private String credName;
	private String contactNumber;
	private String contactPers;
	private Date createDateTime;
	private String activeInd;
	private String createdBy;
	private Date modifiedDateTime;
	private String modifiedBy;
	private String credMemberNo;
	private String credBranchNo;
	private String credAcountNo;


	public WebCreditorModel(String credId) {

		this.credId = credId;
	}

	public WebCreditorModel() {
		// TODO Auto-generated constructor stub
	}

	public String getCredId() {
		return credId;
	}

	public void setCredId(String credId) {
		this.credId = credId;
	}

	public String getCredBank() {
		return credBank;
	}

	public void setCredBank(String credBank) {
		this.credBank = credBank;
	}

	public String getCredName() {
		return credName;
	}

	public void setCredName(String credName) {
		this.credName = credName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactPers() {
		return contactPers;
	}

	public void setContactPers(String contactPers) {
		this.contactPers = contactPers;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCredMemberNo() {
		return credMemberNo;
	}

	public void setCredMemberNo(String credMemberNo) {
		this.credMemberNo = credMemberNo;
	}

	public String getCredAcountNo() {
		return credAcountNo;
	}

	public void setCredAcountNo(String credAcountNo) {
		this.credAcountNo = credAcountNo;
	}

	public String getCredBranchNo() {
		return credBranchNo;
	}

	public void setCredBranchNo(String credBranchNo) {
		this.credBranchNo = credBranchNo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result
				+ ((contactPers == null) ? 0 : contactPers.hashCode());
		result = prime * result
				+ ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result
				+ ((credAcountNo == null) ? 0 : credAcountNo.hashCode());
		result = prime * result
				+ ((credBank == null) ? 0 : credBank.hashCode());
		result = prime * result
				+ ((credBranchNo == null) ? 0 : credBranchNo.hashCode());
		result = prime * result + ((credId == null) ? 0 : credId.hashCode());
		result = prime * result
				+ ((credMemberNo == null) ? 0 : credMemberNo.hashCode());
		result = prime * result
				+ ((credName == null) ? 0 : credName.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime
				* result
				+ ((modifiedDateTime == null) ? 0 : modifiedDateTime.hashCode());
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
		WebCreditorModel other = (WebCreditorModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (contactPers == null) {
			if (other.contactPers != null)
				return false;
		} else if (!contactPers.equals(other.contactPers))
			return false;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (credAcountNo == null) {
			if (other.credAcountNo != null)
				return false;
		} else if (!credAcountNo.equals(other.credAcountNo))
			return false;
		if (credBank == null) {
			if (other.credBank != null)
				return false;
		} else if (!credBank.equals(other.credBank))
			return false;
		if (credBranchNo == null) {
			if (other.credBranchNo != null)
				return false;
		} else if (!credBranchNo.equals(other.credBranchNo))
			return false;
		if (credId == null) {
			if (other.credId != null)
				return false;
		} else if (!credId.equals(other.credId))
			return false;
		if (credMemberNo == null) {
			if (other.credMemberNo != null)
				return false;
		} else if (!credMemberNo.equals(other.credMemberNo))
			return false;
		if (credName == null) {
			if (other.credName != null)
				return false;
		} else if (!credName.equals(other.credName))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDateTime == null) {
			if (other.modifiedDateTime != null)
				return false;
		} else if (!modifiedDateTime.equals(other.modifiedDateTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return credName;
	}

	
	
}
