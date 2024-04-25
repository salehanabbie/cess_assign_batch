package com.bsva.commons.model;

public class MatchPain012Model {
	String seqType, mrti, msgid;
	String branchNo;
	String collDay, dtadjruleInd, adjCat, debitval;
	

	public MatchPain012Model(String seqType, String mrti, String msgId, String branchNo, String collDay, String dtadjruleInd,String adjCat, String debitval) 
	{
		this.seqType = seqType;
		this.mrti = mrti;
		this.msgid = msgId;
		this.branchNo = branchNo;
		this.collDay = collDay;
		this.dtadjruleInd = dtadjruleInd;
		this.adjCat = adjCat;
		this.debitval = debitval;
	}
	
	public MatchPain012Model() {
		
	}
	
	public String getSeqType() {
		return seqType;
	}
	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}
	public String getMrti() {
		return mrti;
	}
	public void setMrti(String mrti) {
		this.mrti = mrti;
	}
	
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getCollDay() {
		return collDay;
	}
	public void setCollDay(String collDay) {
		this.collDay = collDay;
	}
	public String getDtadjruleInd() {
		return dtadjruleInd;
	}
	public void setDtadjruleInd(String dtadjruleInd) {
		this.dtadjruleInd = dtadjruleInd;
	}
	public String getAdjCat() {
		return adjCat;
	}
	public void setAdjCat(String adjCat) {
		this.adjCat = adjCat;
	}
	public String getDebitval() {
		return debitval;
	}
	public void setDebitval(String debitval) {
		this.debitval = debitval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjCat == null) ? 0 : adjCat.hashCode());
		result = prime * result + ((branchNo == null) ? 0 : branchNo.hashCode());
		result = prime * result + ((collDay == null) ? 0 : collDay.hashCode());
		result = prime * result + ((debitval == null) ? 0 : debitval.hashCode());
		result = prime * result + ((dtadjruleInd == null) ? 0 : dtadjruleInd.hashCode());
		result = prime * result + ((mrti == null) ? 0 : mrti.hashCode());
		result = prime * result + ((msgid == null) ? 0 : msgid.hashCode());
		result = prime * result + ((seqType == null) ? 0 : seqType.hashCode());
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
		MatchPain012Model other = (MatchPain012Model) obj;
		if (adjCat == null) {
			if (other.adjCat != null)
				return false;
		} else if (!adjCat.equals(other.adjCat))
			return false;
		if (branchNo == null) {
			if (other.branchNo != null)
				return false;
		} else if (!branchNo.equals(other.branchNo))
			return false;
		if (collDay == null) {
			if (other.collDay != null)
				return false;
		} else if (!collDay.equals(other.collDay))
			return false;
		if (debitval == null) {
			if (other.debitval != null)
				return false;
		} else if (!debitval.equals(other.debitval))
			return false;
		if (dtadjruleInd == null) {
			if (other.dtadjruleInd != null)
				return false;
		} else if (!dtadjruleInd.equals(other.dtadjruleInd))
			return false;
		if (mrti == null) {
			if (other.mrti != null)
				return false;
		} else if (!mrti.equals(other.mrti))
			return false;
		if (msgid == null) {
			if (other.msgid != null)
				return false;
		} else if (!msgid.equals(other.msgid))
			return false;
		if (seqType == null) {
			if (other.seqType != null)
				return false;
		} else if (!seqType.equals(other.seqType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MatchPain012Model [seqType=" + seqType + ", mrti=" + mrti + ", msgid=" + msgid + ", branchNo="
				+ branchNo + ", collDay=" + collDay + ", dtadjruleInd=" + dtadjruleInd + ", adjCat=" + adjCat
				+ ", debitval=" + debitval + "]";
	}

	
	
}
