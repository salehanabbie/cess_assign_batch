package com.bsva.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DimakatsoN
 */
@Entity
@Table(name = "SYS_CIS_BRANCH",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCisBranchEntity.findAll", query = "SELECT s FROM SysCisBranchEntity s"),
    @NamedQuery(name = "SysCisBranchEntity.findByBranchNo", query = "SELECT s FROM SysCisBranchEntity s WHERE s.branchNo = :branchNo"),
    @NamedQuery(name = "SysCisBranchEntity.findByBranchName", query = "SELECT s FROM SysCisBranchEntity s WHERE s.branchName = :branchName"),
    @NamedQuery(name = "SysCisBranchEntity.findByDivision", query = "SELECT s FROM SysCisBranchEntity s WHERE s.division = :division"),
    @NamedQuery(name = "SysCisBranchEntity.findByMemberNo", query = "SELECT s FROM SysCisBranchEntity s WHERE s.memberNo = :memberNo"),
    @NamedQuery(name = "SysCisBranchEntity.findByAcDebtor", query = "SELECT s FROM SysCisBranchEntity s WHERE s.acDebtor = :acDebtor"),
    @NamedQuery(name = "SysCisBranchEntity.findByAcCreditor", query = "SELECT s FROM SysCisBranchEntity s WHERE s.acCreditor = :acCreditor")})
public class SysCisBranchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "BRANCH_NO")
    private String branchNo;
    @Size(max = 30)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Size(max = 30)
    @Column(name = "DIVISION")
    private String division;
    @Size(max = 6)
    @Column(name = "MEMBER_NO")
    private String memberNo;
    @Size(max = 1)
    @Column(name = "AC_DEBTOR")
    private String acDebtor;
    @Size(max = 1)
    @Column(name = "AC_CREDITOR")
    private String acCreditor;

    public SysCisBranchEntity() {
    }

    public SysCisBranchEntity(String branchNo) {
        this.branchNo = branchNo;
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
		SysCisBranchEntity other = (SysCisBranchEntity) obj;
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
		return "SysCisBranchEntity [branchNo=" + branchNo + ", branchName="
				+ branchName + ", division=" + division + ", memberNo="
				+ memberNo + ", acDebtor=" + acDebtor + ", acCreditor="
				+ acCreditor + "]";
	}

  
    
}
