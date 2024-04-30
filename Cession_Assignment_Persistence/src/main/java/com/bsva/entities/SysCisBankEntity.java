package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "SYS_CIS_BANK",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCisBankEntity.findAll", query = "SELECT s FROM SysCisBankEntity s"),
    @NamedQuery(name = "SysCisBankEntity.findByMemberNo", query = "SELECT s FROM SysCisBankEntity s WHERE s.memberNo = :memberNo"),
    @NamedQuery(name = "SysCisBankEntity.findByMemberName", query = "SELECT s FROM SysCisBankEntity s WHERE s.memberName = :memberName"),
    @NamedQuery(name = "SysCisBankEntity.findByMaxNrRecords", query = "SELECT s FROM SysCisBankEntity s WHERE s.maxNrRecords = :maxNrRecords"),
    @NamedQuery(name = "SysCisBankEntity.findByNrOfDaysProc", query = "SELECT s FROM SysCisBankEntity s WHERE s.nrOfDaysProc = :nrOfDaysProc"),
    @NamedQuery(name = "SysCisBankEntity.findByPubHolProc", query = "SELECT s FROM SysCisBankEntity s WHERE s.pubHolProc = :pubHolProc"),
    @NamedQuery(name = "SysCisBankEntity.findByAcDebtor", query = "SELECT s FROM SysCisBankEntity s WHERE s.acDebtor = :acDebtor"),
    @NamedQuery(name = "SysCisBankEntity.findByAcCreditor", query = "SELECT s FROM SysCisBankEntity s WHERE s.acCreditor = :acCreditor")})
public class SysCisBankEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "MEMBER_NO")
    private String memberNo;
    @Size(max = 30)
    @Column(name = "MEMBER_NAME")
    private String memberName;
    @Column(name = "MAX_NR_RECORDS")
    private BigDecimal maxNrRecords;
    @Column(name = "NR_OF_DAYS_PROC")
    private BigDecimal nrOfDaysProc;
    @Size(max = 1)
    @Column(name = "PUB_HOL_PROC")
    private String pubHolProc;
    @Size(max = 1)
    @Column(name = "AC_CREDITOR")
    private String acCreditor;
    @Size(max = 1)
    @Column(name = "AC_DEBTOR")
    private String acDebtor;
   

    public SysCisBankEntity() {
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

	public BigDecimal getMaxNrRecords() {
		return maxNrRecords;
	}

	public void setMaxNrRecords(BigDecimal maxNrRecords) {
		this.maxNrRecords = maxNrRecords;
	}

	public BigDecimal getNrOfDaysProc() {
		return nrOfDaysProc;
	}

	public void setNrOfDaysProc(BigDecimal nrOfDaysProc) {
		this.nrOfDaysProc = nrOfDaysProc;
	}

	public String getPubHolProc() {
		return pubHolProc;
	}

	public void setPubHolProc(String pubHolProc) {
		this.pubHolProc = pubHolProc;
	}
	
	public String getAcCreditor() {
		return acCreditor;
	}

	public void setAcCreditor(String acCreditor) {
		this.acCreditor = acCreditor;
	}

	public String getAcDebtor() {
		return acDebtor;
	}

	public void setAcDebtor(String acDebtor) {
		this.acDebtor = acDebtor;
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
				+ ((maxNrRecords == null) ? 0 : maxNrRecords.hashCode());
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((nrOfDaysProc == null) ? 0 : nrOfDaysProc.hashCode());
		result = prime * result
				+ ((pubHolProc == null) ? 0 : pubHolProc.hashCode());
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
		SysCisBankEntity other = (SysCisBankEntity) obj;
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
		if (maxNrRecords == null) {
			if (other.maxNrRecords != null)
				return false;
		} else if (!maxNrRecords.equals(other.maxNrRecords))
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
		if (nrOfDaysProc == null) {
			if (other.nrOfDaysProc != null)
				return false;
		} else if (!nrOfDaysProc.equals(other.nrOfDaysProc))
			return false;
		if (pubHolProc == null) {
			if (other.pubHolProc != null)
				return false;
		} else if (!pubHolProc.equals(other.pubHolProc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysCisBankEntity [memberNo=" + memberNo + ", memberName="
				+ memberName + ", maxNrRecords=" + maxNrRecords
				+ ", nrOfDaysProc=" + nrOfDaysProc + ", pubHolProc="
				+ pubHolProc + ", acCreditor=" + acCreditor + ", acDebtor="
				+ acDebtor + "]";
	}

	
    
  
    
    
}
