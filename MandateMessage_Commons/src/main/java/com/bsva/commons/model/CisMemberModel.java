package com.bsva.commons.model;

import java.io.Serializable;

public class CisMemberModel implements Serializable {


	private static final long serialVersionUID = 1L;
	
    private String memberNo;
    private String memberName;
    private String abbrevMemberName;
    private String mnemonicMemberName;
    private String memberBranchStartRange;
    private String memberBranchEndRange;
    private String country;
    private String currency;
    private String abbrevCurrency;
    private String samosBicCodeLive;

    
    

	public String getSamosBicCodeLive() {
		return samosBicCodeLive;
	}
	public void setSamosBicCodeLive(String samosBicCodeLive) {
		this.samosBicCodeLive = samosBicCodeLive;
	}

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
	public String getAbbrevMemberName() {
		return abbrevMemberName;
	}
	public void setAbbrevMemberName(String abbrevMemberName) {
		this.abbrevMemberName = abbrevMemberName;
	}
	public String getMnemonicMemberName() {
		return mnemonicMemberName;
	}
	public void setMnemonicMemberName(String mnemonicMemberName) {
		this.mnemonicMemberName = mnemonicMemberName;
	}
	public String getMemberBranchStartRange() {
		return memberBranchStartRange;
	}
	public void setMemberBranchStartRange(String memberBranchStartRange) {
		this.memberBranchStartRange = memberBranchStartRange;
	}
	public String getMemberBranchEndRange() {
		return memberBranchEndRange;
	}
	public void setMemberBranchEndRange(String memberBranchEndRange) {
		this.memberBranchEndRange = memberBranchEndRange;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAbbrevCurrency() {
		return abbrevCurrency;
	}
	public void setAbbrevCurrency(String abbrevCurrency) {
		this.abbrevCurrency = abbrevCurrency;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abbrevCurrency == null) ? 0 : abbrevCurrency.hashCode());
		result = prime
				* result
				+ ((abbrevMemberName == null) ? 0 : abbrevMemberName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime
				* result
				+ ((memberBranchEndRange == null) ? 0 : memberBranchEndRange
						.hashCode());
		result = prime
				* result
				+ ((memberBranchStartRange == null) ? 0
						: memberBranchStartRange.hashCode());
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime
				* result
				+ ((mnemonicMemberName == null) ? 0 : mnemonicMemberName
						.hashCode());
		result = prime
				* result
				+ ((samosBicCodeLive == null) ? 0 : samosBicCodeLive.hashCode());
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
		CisMemberModel other = (CisMemberModel) obj;
		if (abbrevCurrency == null) {
			if (other.abbrevCurrency != null)
				return false;
		} else if (!abbrevCurrency.equals(other.abbrevCurrency))
			return false;
		if (abbrevMemberName == null) {
			if (other.abbrevMemberName != null)
				return false;
		} else if (!abbrevMemberName.equals(other.abbrevMemberName))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (memberBranchEndRange == null) {
			if (other.memberBranchEndRange != null)
				return false;
		} else if (!memberBranchEndRange.equals(other.memberBranchEndRange))
			return false;
		if (memberBranchStartRange == null) {
			if (other.memberBranchStartRange != null)
				return false;
		} else if (!memberBranchStartRange.equals(other.memberBranchStartRange))
			return false;
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
		if (mnemonicMemberName == null) {
			if (other.mnemonicMemberName != null)
				return false;
		} else if (!mnemonicMemberName.equals(other.mnemonicMemberName))
			return false;
		if (samosBicCodeLive == null) {
			if (other.samosBicCodeLive != null)
				return false;
		} else if (!samosBicCodeLive.equals(other.samosBicCodeLive))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CisMemberModel [memberNo=" + memberNo + ", memberName="
				+ memberName + ", abbrevMemberName=" + abbrevMemberName
				+ ", mnemonicMemberName=" + mnemonicMemberName
				+ ", memberBranchStartRange=" + memberBranchStartRange
				+ ", memberBranchEndRange=" + memberBranchEndRange
				+ ", country=" + country + ", currency=" + currency
				+ ", abbrevCurrency=" + abbrevCurrency + ", samosBicCodeLive="
				+ samosBicCodeLive + "]";
	}

}
