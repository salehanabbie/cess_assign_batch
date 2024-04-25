package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DimakatsoN
 */
@Embeddable
public class MdtOpsRepSeqNrPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "MEMBER_NO")
    private String memberNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "REPORT_NO")
    private String reportNo;

    public MdtOpsRepSeqNrPK() {
    }

    public MdtOpsRepSeqNrPK(Date processDate, String memberNo, String reportNo) {
        this.processDate = processDate;
        this.memberNo = memberNo;
        this.reportNo = reportNo;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((reportNo == null) ? 0 : reportNo.hashCode());
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
		MdtOpsRepSeqNrPK other = (MdtOpsRepSeqNrPK) obj;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (reportNo == null) {
			if (other.reportNo != null)
				return false;
		} else if (!reportNo.equals(other.reportNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtOpsRepSeqNrPK [processDate=" + processDate + ", memberNo="
				+ memberNo + ", reportNo=" + reportNo + "]";
	}

 
    
}
