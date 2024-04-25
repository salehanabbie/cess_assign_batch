package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author DimakatsoN
 *
 */

public class WebSysCisBankModel implements Serializable {
	
	
	  private String memberName;
	  private BigDecimal maxNrRecords;
	  private BigDecimal nrOfDaysProc;
	  private String pubHolProc;
	  private String memberNo;
	  private String acDebtor;
	  private String acCreditor;
	  
	  
	public WebSysCisBankModel() {
		super();
		// TODO Auto-generated constructor stub
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
		WebSysCisBankModel other = (WebSysCisBankModel) obj;
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
	public String toString() 
	{
		return memberNo + " - " + memberName;
//		return "WebSysCisBankModel [memberName=" + memberName
//				+ ", maxNrRecords=" + maxNrRecords + ", nrOfDaysProc="
//				+ nrOfDaysProc + ", pubHolProc=" + pubHolProc + ", memberNo="
//				+ memberNo + ", acDebtor=" + acDebtor + ", acCreditor="
//				+ acCreditor + "]";
	}
	
}
