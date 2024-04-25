package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author DimakatsoN
 * update by SalehaR 03/11/2015
 * modified by SalehaR - 17/12/2015
 * modified by SalehaR - 13/09/2016
 */

@Entity
@Table(name = "MDT_AC_OPS_STATUS_DETAILS",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findAll", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findBySystemSeqNo", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByStatusHdrSeqNo", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.statusHdrSeqNo = :statusHdrSeqNo"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByErrorCode", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByTxnId", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByEndToEndId", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.endToEndId = :endToEndId"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByTxnStatus", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByErrorType", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.errorType = :errorType"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByRecordId", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.recordId = :recordId"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByOrgnlTxnSeqNo", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.orgnlTxnSeqNo = :orgnlTxnSeqNo"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByMandateRefNumber", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.mandateRefNumber = :mandateRefNumber"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByInstId", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByProcessStatus", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByDebtorBranchNo", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.debtorBranchNo = :debtorBranchNo"),
    @NamedQuery(name = "MdtAcOpsStatusDetailsEntity.findByCrAbbShortName", query = "SELECT m FROM MdtAcOpsStatusDetailsEntity m WHERE m.crAbbShortName = :crAbbShortName")})
public class MdtAcOpsStatusDetailsEntity implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.MDT_AC_OPS_STATUS_DETAILS_SEQ") )
    @GeneratedValue(generator = "generator")
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_HDR_SEQ_NO")
    private BigDecimal statusHdrSeqNo;
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
    private BigDecimal orgnlTxnSeqNo;
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

    public MdtAcOpsStatusDetailsEntity() {
    }

    public MdtAcOpsStatusDetailsEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public MdtAcOpsStatusDetailsEntity(BigDecimal systemSeqNo, BigDecimal statusHdrSeqNo) {
        this.systemSeqNo = systemSeqNo;
        this.statusHdrSeqNo = statusHdrSeqNo;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public BigDecimal getStatusHdrSeqNo() {
        return statusHdrSeqNo;
    }

    public void setStatusHdrSeqNo(BigDecimal statusHdrSeqNo) {
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

    public BigDecimal getOrgnlTxnSeqNo() {
        return orgnlTxnSeqNo;
    }

    public void setOrgnlTxnSeqNo(BigDecimal orgnlTxnSeqNo) {
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((crAbbShortName == null) ? 0 : crAbbShortName.hashCode());
			result = prime
					* result
					+ ((debtorBranchNo == null) ? 0 : debtorBranchNo.hashCode());
			result = prime * result
					+ ((endToEndId == null) ? 0 : endToEndId.hashCode());
			result = prime * result
					+ ((errorCode == null) ? 0 : errorCode.hashCode());
			result = prime * result
					+ ((errorType == null) ? 0 : errorType.hashCode());
			result = prime * result
					+ ((instId == null) ? 0 : instId.hashCode());
			result = prime
					* result
					+ ((mandateRefNumber == null) ? 0 : mandateRefNumber
							.hashCode());
			result = prime * result
					+ ((orgnlTxnSeqNo == null) ? 0 : orgnlTxnSeqNo.hashCode());
			result = prime * result
					+ ((processStatus == null) ? 0 : processStatus.hashCode());
			result = prime * result
					+ ((recordId == null) ? 0 : recordId.hashCode());
			result = prime
					* result
					+ ((statusHdrSeqNo == null) ? 0 : statusHdrSeqNo.hashCode());
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
			MdtAcOpsStatusDetailsEntity other = (MdtAcOpsStatusDetailsEntity) obj;
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
			if (statusHdrSeqNo == null) {
				if (other.statusHdrSeqNo != null)
					return false;
			} else if (!statusHdrSeqNo.equals(other.statusHdrSeqNo))
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
			return "MdtAcOpsStatusDetailsEntity [systemSeqNo=" + systemSeqNo
					+ ", statusHdrSeqNo=" + statusHdrSeqNo + ", errorCode="
					+ errorCode + ", txnId=" + txnId + ", endToEndId="
					+ endToEndId + ", txnStatus=" + txnStatus + ", errorType="
					+ errorType + ", recordId=" + recordId + ", orgnlTxnSeqNo="
					+ orgnlTxnSeqNo + ", mandateRefNumber=" + mandateRefNumber
					+ ", instId=" + instId + ", processStatus=" + processStatus
					+ ", debtorBranchNo=" + debtorBranchNo
					+ ", crAbbShortName=" + crAbbShortName + "]";
		}
	    
	    
    
	
}
