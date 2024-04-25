package com.bsva.models;

import java.io.Serializable;
/***
 * 
 * @author DimakatsoN
 *
 */

public class WebSysCisBranchModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchNo;
    private String branchName;
    private String division;
    private String memberNo;
    private String acDebtor;
    private String acCreditor;
    
    
	public WebSysCisBranchModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getBranchNo() {
		return branchNo;
	}


	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}


	public String getAcDebtor() {
		return acDebtor;
	}


	public void setAcDebtor(String acDebtor) {
		this.acDebtor = acDebtor;
	}


	public String getAcCreditor() {
		return acCreditor;
	}


	public void setAcCreditor(String acCreditor) {
		this.acCreditor = acCreditor;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acCreditor == null) ? 0 : acCreditor.hashCode());
		result = prime * result
				+ ((acDebtor == null) ? 0 : acDebtor.hashCode());
		result = prime * result
				+ ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result
				+ ((branchNo == null) ? 0 : branchNo.hashCode());
		result = prime * result
				+ ((division == null) ? 0 : division.hashCode());
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
		WebSysCisBranchModel other = (WebSysCisBranchModel) obj;
		if (acCreditor == null) {
			if (other.acCreditor != null)
				return false;
		} else if (!acCreditor.equals(other.acCreditor))
			return false;
		if (acDebtor == null) {
			if (other.acDebtor != null)
				return false;
		} else if (!acDebtor.equals(other.acDebtor))
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
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "WebSysCisBranchModel [branchNo=" + branchNo + ", branchName="
				+ branchName + ", division=" + division + ", memberNo="
				+ memberNo + ", acDebtor=" + acDebtor + ", acCreditor="
				+ acCreditor + "]";
	}
    
    
    

}
