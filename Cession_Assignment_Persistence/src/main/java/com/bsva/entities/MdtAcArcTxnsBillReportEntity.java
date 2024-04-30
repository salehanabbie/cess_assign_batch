package com.bsva.entities;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DimakatsoN
 */
@Entity
@Table(name = "MDT_AC_ARC_TXNS_BILL_REPORT",schema ="MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findAll", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findBySystemSeqNo", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByOriginator", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.originator = :originator"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findBySubService", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.subService = :subService"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByTxnType", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.txnType = :txnType"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByFileName", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.fileName = :fileName"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByProcessDate", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByDeliveryTime", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.deliveryTime = :deliveryTime"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByMandateReqTranId", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.mandateReqTranId = :mandateReqTranId"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByArchiveDate", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.archiveDate = :archiveDate"),
    @NamedQuery(name = "MdtAcArcTxnsBillReportEntity.findByTxnStatus", query = "SELECT m FROM MdtAcArcTxnsBillReportEntity m WHERE m.txnStatus = :txnStatus")})
public class MdtAcArcTxnsBillReportEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Size(max = 6)
    @Column(name = "ORIGINATOR")
    private String originator;
    @Size(max = 5)
    @Column(name = "SUB_SERVICE")
    private String subService;
    @Size(max = 3)
    @Column(name = "TXN_TYPE")
    private String txnType;
    @Size(max = 40)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Column(name = "DELIVERY_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryTime;
    @Size(max = 40)
    @Column(name = "MANDATE_REQ_TRAN_ID")
    private String mandateReqTranId;
    @Column(name = "ARCHIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;
    @Size(max = 4)
    @Column(name = "TXN_STATUS")
    private String txnStatus;

    public MdtAcArcTxnsBillReportEntity() {
    }

	public BigDecimal getSystemSeqNo() {
		return systemSeqNo;
	}

	public void setSystemSeqNo(BigDecimal systemSeqNo) {
		this.systemSeqNo = systemSeqNo;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getSubService() {
		return subService;
	}

	public void setSubService(String subService) {
		this.subService = subService;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getMandateReqTranId() {
		return mandateReqTranId;
	}

	public void setMandateReqTranId(String mandateReqTranId) {
		this.mandateReqTranId = mandateReqTranId;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archiveDate == null) ? 0 : archiveDate.hashCode());
		result = prime * result + ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((mandateReqTranId == null) ? 0 : mandateReqTranId.hashCode());
		result = prime * result + ((originator == null) ? 0 : originator.hashCode());
		result = prime * result + ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result + ((subService == null) ? 0 : subService.hashCode());
		result = prime * result + ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((txnStatus == null) ? 0 : txnStatus.hashCode());
		result = prime * result + ((txnType == null) ? 0 : txnType.hashCode());
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
		MdtAcArcTxnsBillReportEntity other = (MdtAcArcTxnsBillReportEntity) obj;
		if (archiveDate == null) {
			if (other.archiveDate != null)
				return false;
		} else if (!archiveDate.equals(other.archiveDate))
			return false;
		if (deliveryTime == null) {
			if (other.deliveryTime != null)
				return false;
		} else if (!deliveryTime.equals(other.deliveryTime))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (mandateReqTranId == null) {
			if (other.mandateReqTranId != null)
				return false;
		} else if (!mandateReqTranId.equals(other.mandateReqTranId))
			return false;
		if (originator == null) {
			if (other.originator != null)
				return false;
		} else if (!originator.equals(other.originator))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (subService == null) {
			if (other.subService != null)
				return false;
		} else if (!subService.equals(other.subService))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (txnStatus == null) {
			if (other.txnStatus != null)
				return false;
		} else if (!txnStatus.equals(other.txnStatus))
			return false;
		if (txnType == null) {
			if (other.txnType != null)
				return false;
		} else if (!txnType.equals(other.txnType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcArcTxnsBillReportEntity [systemSeqNo=" + systemSeqNo + ", originator=" + originator
				+ ", subService=" + subService + ", txnType=" + txnType + ", fileName=" + fileName + ", processDate="
				+ processDate + ", deliveryTime=" + deliveryTime + ", mandateReqTranId=" + mandateReqTranId
				+ ", archiveDate=" + archiveDate + ", txnStatus=" + txnStatus + "]";
	}

  
   
}
