package com.bsva.models;
import java.io.Serializable;

public class WebCisBranchModel implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String branchNo;
    private String branchName;
    private String division;
    private String abbrDivisionName;
    private String agencyBranchNo;
    private String memberNo;
    private String institutionType;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    
	public String getBranchNo()
	{
		return branchNo;
	}
	
	public void setBranchNo(String branchNo) 
	{
		this.branchNo = branchNo;
	}
	
	public String getBranchName()
	{
		return branchName;
	}
	
	public void setBranchName(String branchName) 
	{
		this.branchName = branchName;
	}
	
	public String getDivision() 
	{
		return division;
	}
	
	public void setDivision(String division)
	{
		this.division = division;
	}
	
	public String getAbbrDivisionName() 
	{
		return abbrDivisionName;
	}
	
	public void setAbbrDivisionName(String abbrDivisionName) 
	{
		this.abbrDivisionName = abbrDivisionName;
	}
	
	public String getAgencyBranchNo() 
	{
		return agencyBranchNo;
	}
	
	public void setAgencyBranchNo(String agencyBranchNo) 
	{
		this.agencyBranchNo = agencyBranchNo;
	}
	
	public String getMemberNo() 
	{
		return memberNo;
	}
	
	public void setMemberNo(String memberNo) 
	{
		this.memberNo = memberNo;
	}
	
	public String getInstitutionType()
	{
		return institutionType;
	}
	
	public void setInstitutionType(String institutionType) 
	{
		this.institutionType = institutionType;
	}
	
	public String getAddressLine1() 
	{
		return addressLine1;
	}
	
	public void setAddressLine1(String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}
	
	public String getAddressLine2()
	{
		return addressLine2;
	}
	
	public void setAddressLine2(String addressLine2) 
	{
		this.addressLine2 = addressLine2;
	}
	
	public String getAddressLine3() 
	{
		return addressLine3;
	}
	
	public void setAddressLine3(String addressLine3) 
	{
		this.addressLine3 = addressLine3;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((abbrDivisionName == null) ? 0 : abbrDivisionName.hashCode());
		result = prime * result
				+ ((addressLine1 == null) ? 0 : addressLine1.hashCode());
		result = prime * result
				+ ((addressLine2 == null) ? 0 : addressLine2.hashCode());
		result = prime * result
				+ ((addressLine3 == null) ? 0 : addressLine3.hashCode());
		result = prime * result
				+ ((agencyBranchNo == null) ? 0 : agencyBranchNo.hashCode());
		result = prime * result
				+ ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result
				+ ((branchNo == null) ? 0 : branchNo.hashCode());
		result = prime * result
				+ ((division == null) ? 0 : division.hashCode());
		result = prime * result
				+ ((institutionType == null) ? 0 : institutionType.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebCisBranchModel other = (WebCisBranchModel) obj;
		if (abbrDivisionName == null) {
			if (other.abbrDivisionName != null)
				return false;
		} else if (!abbrDivisionName.equals(other.abbrDivisionName))
			return false;
		if (addressLine1 == null) {
			if (other.addressLine1 != null)
				return false;
		} else if (!addressLine1.equals(other.addressLine1))
			return false;
		if (addressLine2 == null) {
			if (other.addressLine2 != null)
				return false;
		} else if (!addressLine2.equals(other.addressLine2))
			return false;
		if (addressLine3 == null) {
			if (other.addressLine3 != null)
				return false;
		} else if (!addressLine3.equals(other.addressLine3))
			return false;
		if (agencyBranchNo == null) {
			if (other.agencyBranchNo != null)
				return false;
		} else if (!agencyBranchNo.equals(other.agencyBranchNo))
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (branchNo == null) {
			if (other.branchNo != null)
				return false;
		} else if (!branchNo.equals(other.branchNo))
			return false;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (institutionType == null) {
			if (other.institutionType != null)
				return false;
		} else if (!institutionType.equals(other.institutionType))
			return false;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return branchNo;
	}
}
