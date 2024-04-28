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
 * @author SalehaR
 */
@Entity
@Table(name = "CAS_OPS_CONF_DETAILS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsConfDetailsEntity.findAll", query = "SELECT m FROM CasOpsConfDetailsEntity m"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findBySystemSeqNo", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByConfHdrSeqNo", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.confHdrSeqNo = :confHdrSeqNo"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByErrorCode", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByTxnId", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByTxnStatus", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByErrorType", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.errorType = :errorType"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByRecordId", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.recordId = :recordId"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByMandateRefNumber", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.mandateRefNumber = :mandateRefNumber"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByInstId", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByProcessStatus", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByExtractService", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.extractService = :extractService"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByOrgnlMsgType", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.orgnlMsgType = :orgnlMsgType"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByExtractMsgId", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.extractMsgId = :extractMsgId"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByLocalInstrCd", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.localInstrCd = :localInstrCd"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByMsgId", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.msgId = :msgId"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByInFileName", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.inFileName = :inFileName"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByExtractFileName", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.extractFileName = :extractFileName"),
    @NamedQuery(name = "CasOpsConfDetailsEntity.findByExtractProcessStatusOutService", query = "SELECT m FROM CasOpsConfDetailsEntity m WHERE m.processStatus = :processStatus and m.extractService = :extractService and m.instId = :memberId")})
    
public class CasOpsConfDetailsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.CAS_OPS_CONF_DETAILS_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONF_HDR_SEQ_NO")
    private BigDecimal confHdrSeqNo;
    @Size(max = 6)
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Size(max = 35)
    @Column(name = "TXN_ID")
    private String txnId;
    @Size(max = 4)
    @Column(name = "TXN_STATUS")
    private String txnStatus;
    @Size(max = 3)
    @Column(name = "ERROR_TYPE")
    private String errorType;
    @Size(max = 2)
    @Column(name = "RECORD_ID")
    private String recordId;
    @Size(max = 35)
    @Column(name = "MANDATE_REF_NUMBER")
    private String mandateRefNumber;
    @Size(max = 11)
    @Column(name = "INST_ID")
    private String instId;
    @Size(max = 1)
    @Column(name = "PROCESS_STATUS")
    private String processStatus;
    @Size(max = 5)
    @Column(name = "EXTRACT_SERVICE")
    private String extractService;
    @Size(max = 35)
    @Column(name = "ORGNL_MSG_TYPE")
    private String orgnlMsgType;
    @Size(max = 35)
    @Column(name = "EXTRACT_MSG_ID")
    private String extractMsgId;
    @Size(max = 4)
    @Column(name = "LOCAL_INSTR_CD")
    private String localInstrCd;
    @Size(min = 1, max = 35)
    @Column(name = "MSG_ID")
    private String msgId;
    @Size(max = 40)
    @Column(name = "IN_FILE_NAME")
    private String inFileName;
    @Size(max = 40)
    @Column(name = "EXTRACT_FILE_NAME")
    private String extractFileName;

    

    public CasOpsConfDetailsEntity() {
    }

    public CasOpsConfDetailsEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public CasOpsConfDetailsEntity(BigDecimal systemSeqNo, BigDecimal confHdrSeqNo) {
        this.systemSeqNo = systemSeqNo;
        this.confHdrSeqNo = confHdrSeqNo;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public BigDecimal getConfHdrSeqNo() {
        return confHdrSeqNo;
    }

    public void setConfHdrSeqNo(BigDecimal confHdrSeqNo) {
        this.confHdrSeqNo = confHdrSeqNo;
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

    public String getExtractService() {
        return extractService;
    }

    public void setExtractService(String extractService) {
        this.extractService = extractService;
    }
    
    public String getOrgnlMsgType() {
        return orgnlMsgType;
    }

    public void setOrgnlMsgType(String orgnlMsgType) {
        this.orgnlMsgType = orgnlMsgType;
    }
    
    public String getExtractMsgId() {
        return extractMsgId;
    }

    public void setExtractMsgId(String extractMsgId) {
        this.extractMsgId = extractMsgId;
    }
    
    public String getLocalInstrCd() {
        return localInstrCd;
    }

    public void setLocalInstrCd(String localInstrCd) {
        this.localInstrCd = localInstrCd;
    }

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getInFileName() {
		return inFileName;
	}

	public void setInFileName(String inFileName) {
		this.inFileName = inFileName;
	}

	public String getExtractFileName() {
		return extractFileName;
	}

	public void setExtractFileName(String extractFileName) {
		this.extractFileName = extractFileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confHdrSeqNo == null) ? 0 : confHdrSeqNo.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result + ((extractFileName == null) ? 0 : extractFileName.hashCode());
		result = prime * result + ((extractMsgId == null) ? 0 : extractMsgId.hashCode());
		result = prime * result + ((extractService == null) ? 0 : extractService.hashCode());
		result = prime * result + ((inFileName == null) ? 0 : inFileName.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((localInstrCd == null) ? 0 : localInstrCd.hashCode());
		result = prime * result + ((mandateRefNumber == null) ? 0 : mandateRefNumber.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + ((orgnlMsgType == null) ? 0 : orgnlMsgType.hashCode());
		result = prime * result + ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result + ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
		result = prime * result + ((txnStatus == null) ? 0 : txnStatus.hashCode());
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
		CasOpsConfDetailsEntity other = (CasOpsConfDetailsEntity) obj;
		if (confHdrSeqNo == null) {
			if (other.confHdrSeqNo != null)
				return false;
		} else if (!confHdrSeqNo.equals(other.confHdrSeqNo))
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
		if (extractFileName == null) {
			if (other.extractFileName != null)
				return false;
		} else if (!extractFileName.equals(other.extractFileName))
			return false;
		if (extractMsgId == null) {
			if (other.extractMsgId != null)
				return false;
		} else if (!extractMsgId.equals(other.extractMsgId))
			return false;
		if (extractService == null) {
			if (other.extractService != null)
				return false;
		} else if (!extractService.equals(other.extractService))
			return false;
		if (inFileName == null) {
			if (other.inFileName != null)
				return false;
		} else if (!inFileName.equals(other.inFileName))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (localInstrCd == null) {
			if (other.localInstrCd != null)
				return false;
		} else if (!localInstrCd.equals(other.localInstrCd))
			return false;
		if (mandateRefNumber == null) {
			if (other.mandateRefNumber != null)
				return false;
		} else if (!mandateRefNumber.equals(other.mandateRefNumber))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (orgnlMsgType == null) {
			if (other.orgnlMsgType != null)
				return false;
		} else if (!orgnlMsgType.equals(other.orgnlMsgType))
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
		return "CasOpsConfDetailsEntity [systemSeqNo=" + systemSeqNo + ", confHdrSeqNo=" + confHdrSeqNo
				+ ", errorCode=" + errorCode + ", txnId=" + txnId + ", txnStatus=" + txnStatus + ", errorType="
				+ errorType + ", recordId=" + recordId + ", mandateRefNumber=" + mandateRefNumber + ", instId=" + instId
				+ ", processStatus=" + processStatus + ", extractService=" + extractService + ", orgnlMsgType="
				+ orgnlMsgType + ", extractMsgId=" + extractMsgId + ", localInstrCd=" + localInstrCd + ", msgId="
				+ msgId + ", inFileName=" + inFileName + ", extractFileName=" + extractFileName + "]";
	}

}	    