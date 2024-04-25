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
public class MdtAcArcTxnsBillingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private long systemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    public MdtAcArcTxnsBillingPK() {
    }

    public MdtAcArcTxnsBillingPK(long systemSeqNo, String fileName, Date actionDate) {
        this.systemSeqNo = systemSeqNo;
        this.fileName = fileName;
        this.actionDate = actionDate;
    }

    public long getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(long systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionDate == null) ? 0 : actionDate.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + (int) (systemSeqNo ^ (systemSeqNo >>> 32));
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
		MdtAcArcTxnsBillingPK other = (MdtAcArcTxnsBillingPK) obj;
		if (actionDate == null) {
			if (other.actionDate != null)
				return false;
		} else if (!actionDate.equals(other.actionDate))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (systemSeqNo != other.systemSeqNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcArcTxnsBillingPK [systemSeqNo=" + systemSeqNo + ", fileName=" + fileName + ", actionDate="
				+ actionDate + "]";
	}

   
}
