package com.bsva.entities;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 * Modified by: SalehaR - 2016/09/13
 */
@Entity
@Table(name = "CAS_ARC_STATUS_DETAILS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasArcStatusDetailsEntity.findAll", query = "SELECT m FROM CasArcStatusDetailsEntity m"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findBySystemSeqNo", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByStatusHdrSeqNo", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.statusHdrSeqNo = :statusHdrSeqNo"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByErrorCode", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByTxnId", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByEndToEndId", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.endToEndId = :endToEndId"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByTxnStatus", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByErrorType", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.errorType = :errorType"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByRecordId", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.recordId = :recordId"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByOrgnlTxnSeqNo", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.orgnlTxnSeqNo = :orgnlTxnSeqNo"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByMandateRefNumber", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.mandateRefNumber = :mandateRefNumber"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByInstId", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByProcessStatus", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByDebtorBranchNo", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.debtorBranchNo = :debtorBranchNo"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByCrAbbShortName", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.crAbbShortName = :crAbbShortName"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByArchiveDate", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.archiveDate = :archiveDate"),
    @NamedQuery(name = "CasArcStatusDetailsEntity.findByArchiveDateCleanUp", query = "SELECT m FROM CasArcStatusDetailsEntity m WHERE m.archiveDate <= :archiveDate")})
public class CasArcStatusDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private Long systemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_HDR_SEQ_NO")
    private long statusHdrSeqNo;
    @Size(max = 6)
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Size(max = 35)
    @Column(name = "TXN_ID")
    private String txnId;
    @Size(max = 35)
    @Column(name = "END_TO_END_ID")
    private String endToEndId;
    @Size(max = 4)
    @Column(name = "TXN_STATUS")
    private String txnStatus;
    @Size(max = 3)
    @Column(name = "ERROR_TYPE")
    private String errorType;
    @Size(max = 2)
    @Column(name = "RECORD_ID")
    private String recordId;
    @Column(name = "ORGNL_TXN_SEQ_NO")
    private Long orgnlTxnSeqNo;
    @Size(max = 35)
    @Column(name = "MANDATE_REF_NUMBER")
    private String mandateRefNumber;
    @Size(max = 11)
    @Column(name = "INST_ID")
    private String instId;
    @Size(max = 1)
    @Column(name = "PROCESS_STATUS")
    private String processStatus;
    @Size(max = 6)
    @Column(name = "DEBTOR_BRANCH_NO")
    private String debtorBranchNo;
    @Size(max = 10)
    @Column(name = "CR_ABB_SHORT_NAME")
    private String crAbbShortName;
    @Column(name = "ARCHIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    public CasArcStatusDetailsEntity() {
    }

    public CasArcStatusDetailsEntity(Long systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public CasArcStatusDetailsEntity(Long systemSeqNo, long statusHdrSeqNo) {
        this.systemSeqNo = systemSeqNo;
        this.statusHdrSeqNo = statusHdrSeqNo;
    }

    public Long getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(Long systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public long getStatusHdrSeqNo() {
        return statusHdrSeqNo;
    }

    public void setStatusHdrSeqNo(long statusHdrSeqNo) {
        this.statusHdrSeqNo = statusHdrSeqNo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Long getOrgnlTxnSeqNo() {
        return orgnlTxnSeqNo;
    }

    public void setOrgnlTxnSeqNo(Long orgnlTxnSeqNo) {
        this.orgnlTxnSeqNo = orgnlTxnSeqNo;
    }

    public String getMandateRefNumber() {
        return mandateRefNumber;
    }

    public void setMandateRefNumber(String mandateRefNumber) {
        this.mandateRefNumber = mandateRefNumber;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getDebtorBranchNo() {
        return debtorBranchNo;
    }

    public void setDebtorBranchNo(String debtorBranchNo) {
        this.debtorBranchNo = debtorBranchNo;
    }

    public String getCrAbbShortName() {
        return crAbbShortName;
    }

    public void setCrAbbShortName(String crAbbShortName) {
        this.crAbbShortName = crAbbShortName;
    }
    
    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((crAbbShortName == null) ? 0 : crAbbShortName.hashCode());
		result = prime * result
				+ ((debtorBranchNo == null) ? 0 : debtorBranchNo.hashCode());
		result = prime * result
				+ ((endToEndId == null) ? 0 : endToEndId.hashCode());
		result = prime * result
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result
				+ ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime
				* result
				+ ((mandateRefNumber == null) ? 0 : mandateRefNumber.hashCode());
		result = prime * result
				+ ((orgnlTxnSeqNo == null) ? 0 : orgnlTxnSeqNo.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result
				+ (int) (statusHdrSeqNo ^ (statusHdrSeqNo >>> 32));
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
		result = prime * result
				+ ((txnStatus == null) ? 0 : txnStatus.hashCode());
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
		CasArcStatusDetailsEntity other = (CasArcStatusDetailsEntity) obj;
		if (crAbbShortName == null) {
			if (other.crAbbShortName != null)
				return false;
		} else if (!crAbbShortName.equals(other.crAbbShortName))
			return false;
		if (debtorBranchNo == null) {
			if (other.debtorBranchNo != null)
				return false;
		} else if (!debtorBranchNo.equals(other.debtorBranchNo))
			return false;
		if (endToEndId == null) {
			if (other.endToEndId != null)
				return false;
		} else if (!endToEndId.equals(other.endToEndId))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorType == null) {
			if (other.errorType != null)
				return false;
		} else if (!errorType.equals(other.errorType))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (mandateRefNumber == null) {
			if (other.mandateRefNumber != null)
				return false;
		} else if (!mandateRefNumber.equals(other.mandateRefNumber))
			return false;
		if (orgnlTxnSeqNo == null) {
			if (other.orgnlTxnSeqNo != null)
				return false;
		} else if (!orgnlTxnSeqNo.equals(other.orgnlTxnSeqNo))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		if (statusHdrSeqNo != other.statusHdrSeqNo)
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (txnId == null) {
			if (other.txnId != null)
				return false;
		} else if (!txnId.equals(other.txnId))
			return false;
		if (txnStatus == null) {
			if (other.txnStatus != null)
				return false;
		} else if (!txnStatus.equals(other.txnStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasArcStatusDetailsEntity [systemSeqNo=" + systemSeqNo
				+ ", statusHdrSeqNo=" + statusHdrSeqNo + ", errorCode="
				+ errorCode + ", txnId=" + txnId + ", endToEndId=" + endToEndId
				+ ", txnStatus=" + txnStatus + ", errorType=" + errorType
				+ ", recordId=" + recordId + ", orgnlTxnSeqNo=" + orgnlTxnSeqNo
				+ ", mandateRefNumber=" + mandateRefNumber + ", instId="
				+ instId + ", processStatus=" + processStatus
				+ ", debtorBranchNo=" + debtorBranchNo + ", crAbbShortName="
				+ crAbbShortName + "]";
	}

	
    
    
   
}
