package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "MDT_AC_OPS_TRANS_CTRL_MSG",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findAll", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByCtrlMsgId", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.ctrlMsgId = :ctrlMsgId"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByServiceId", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.serviceId = :serviceId"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByMsgType", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.msgType = :msgType"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByProcessDate", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByMemberIdFm", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.memberIdFm = :memberIdFm"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByMemberIdTo", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.memberIdTo = :memberIdTo"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByNrOfFiles", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.nrOfFiles = :nrOfFiles"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByNrOfRecords", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.nrOfRecords = :nrOfRecords"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByValueOfRecords", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.valueOfRecords = :valueOfRecords"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findBySystemStatus", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.systemStatus = :systemStatus"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByNrOfFilesReceived", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.nrOfFilesReceived = :nrOfFilesReceived"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByNrOfRecordsReceived", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.nrOfRecordsReceived = :nrOfRecordsReceived"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByValidRecordsReceived", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.validRecordsReceived = :validRecordsReceived"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByActiveInd", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtAcOpsTransCtrlMsgEntity.findByValueOfRecordsCurr", query = "SELECT m FROM MdtAcOpsTransCtrlMsgEntity m WHERE m.valueOfRecordsCurr = :valueOfRecordsCurr")})
public class MdtAcOpsTransCtrlMsgEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.MDT_AC_OPS_TRANS_CTRL_MSG_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_MSG_ID")
    private BigDecimal ctrlMsgId;
    @Size(max = 5)
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Size(max = 30)
    @Column(name = "MSG_TYPE")
    private String msgType;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Size(max = 6)
    @Column(name = "MEMBER_ID_FM")
    private String memberIdFm;
    @Size(max = 6)
    @Column(name = "MEMBER_ID_TO")
    private String memberIdTo;
    @Column(name = "NR_OF_FILES")
    private Integer nrOfFiles;
    @Column(name = "NR_OF_RECORDS")
    private Integer nrOfRecords;
    @Column(name = "VALUE_OF_RECORDS")
    private BigDecimal valueOfRecords;
    @Size(max = 10)
    @Column(name = "SYSTEM_STATUS")
    private String systemStatus;
    @Column(name = "NR_OF_FILES_RECEIVED")
    private Integer nrOfFilesReceived;
    @Column(name = "NR_OF_RECORDS_RECEIVED")
    private Integer nrOfRecordsReceived;
    @Column(name = "VALID_RECORDS_RECEIVED")
    private Integer validRecordsReceived;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 3)
    @Column(name = "VALUE_OF_RECORDS_CURR")
    private String valueOfRecordsCurr;

    public MdtAcOpsTransCtrlMsgEntity() {
    }

    public MdtAcOpsTransCtrlMsgEntity(BigDecimal ctrlMsgId) {
        this.ctrlMsgId = ctrlMsgId;
    }

    public BigDecimal getCtrlMsgId() {
        return ctrlMsgId;
    }

    public void setCtrlMsgId(BigDecimal ctrlMsgId) {
        this.ctrlMsgId = ctrlMsgId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getMemberIdFm() {
        return memberIdFm;
    }

    public void setMemberIdFm(String memberIdFm) {
        this.memberIdFm = memberIdFm;
    }

    public String getMemberIdTo() {
        return memberIdTo;
    }

    public void setMemberIdTo(String memberIdTo) {
        this.memberIdTo = memberIdTo;
    }

    public Integer getNrOfFiles() {
        return nrOfFiles;
    }

    public void setNrOfFiles(Integer nrOfFiles) {
        this.nrOfFiles = nrOfFiles;
    }

    public Integer getNrOfRecords() {
        return nrOfRecords;
    }

    public void setNrOfRecords(Integer nrOfRecords) {
        this.nrOfRecords = nrOfRecords;
    }

    public BigDecimal getValueOfRecords() {
        return valueOfRecords;
    }

    public void setValueOfRecords(BigDecimal valueOfRecords) {
        this.valueOfRecords = valueOfRecords;
    }

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public Integer getNrOfFilesReceived() {
        return nrOfFilesReceived;
    }

    public void setNrOfFilesReceived(Integer nrOfFilesReceived) {
        this.nrOfFilesReceived = nrOfFilesReceived;
    }

    public Integer getNrOfRecordsReceived() {
        return nrOfRecordsReceived;
    }

    public void setNrOfRecordsReceived(Integer nrOfRecordsReceived) {
        this.nrOfRecordsReceived = nrOfRecordsReceived;
    }

    public Integer getValidRecordsReceived() {
        return validRecordsReceived;
    }

    public void setValidRecordsReceived(Integer validRecordsReceived) {
        this.validRecordsReceived = validRecordsReceived;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public String getValueOfRecordsCurr() {
        return valueOfRecordsCurr;
    }

    public void setValueOfRecordsCurr(String valueOfRecordsCurr) {
        this.valueOfRecordsCurr = valueOfRecordsCurr;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((ctrlMsgId == null) ? 0 : ctrlMsgId.hashCode());
		result = prime * result
				+ ((memberIdFm == null) ? 0 : memberIdFm.hashCode());
		result = prime * result
				+ ((memberIdTo == null) ? 0 : memberIdTo.hashCode());
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		result = prime * result
				+ ((nrOfFiles == null) ? 0 : nrOfFiles.hashCode());
		result = prime
				* result
				+ ((nrOfFilesReceived == null) ? 0 : nrOfFilesReceived
						.hashCode());
		result = prime * result
				+ ((nrOfRecords == null) ? 0 : nrOfRecords.hashCode());
		result = prime
				* result
				+ ((nrOfRecordsReceived == null) ? 0 : nrOfRecordsReceived
						.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result
				+ ((systemStatus == null) ? 0 : systemStatus.hashCode());
		result = prime
				* result
				+ ((validRecordsReceived == null) ? 0 : validRecordsReceived
						.hashCode());
		result = prime * result
				+ ((valueOfRecords == null) ? 0 : valueOfRecords.hashCode());
		result = prime
				* result
				+ ((valueOfRecordsCurr == null) ? 0 : valueOfRecordsCurr
						.hashCode());
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
		MdtAcOpsTransCtrlMsgEntity other = (MdtAcOpsTransCtrlMsgEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (ctrlMsgId == null) {
			if (other.ctrlMsgId != null)
				return false;
		} else if (!ctrlMsgId.equals(other.ctrlMsgId))
			return false;
		if (memberIdFm == null) {
			if (other.memberIdFm != null)
				return false;
		} else if (!memberIdFm.equals(other.memberIdFm))
			return false;
		if (memberIdTo == null) {
			if (other.memberIdTo != null)
				return false;
		} else if (!memberIdTo.equals(other.memberIdTo))
			return false;
		if (msgType == null) {
			if (other.msgType != null)
				return false;
		} else if (!msgType.equals(other.msgType))
			return false;
		if (nrOfFiles == null) {
			if (other.nrOfFiles != null)
				return false;
		} else if (!nrOfFiles.equals(other.nrOfFiles))
			return false;
		if (nrOfFilesReceived == null) {
			if (other.nrOfFilesReceived != null)
				return false;
		} else if (!nrOfFilesReceived.equals(other.nrOfFilesReceived))
			return false;
		if (nrOfRecords == null) {
			if (other.nrOfRecords != null)
				return false;
		} else if (!nrOfRecords.equals(other.nrOfRecords))
			return false;
		if (nrOfRecordsReceived == null) {
			if (other.nrOfRecordsReceived != null)
				return false;
		} else if (!nrOfRecordsReceived.equals(other.nrOfRecordsReceived))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (systemStatus == null) {
			if (other.systemStatus != null)
				return false;
		} else if (!systemStatus.equals(other.systemStatus))
			return false;
		if (validRecordsReceived == null) {
			if (other.validRecordsReceived != null)
				return false;
		} else if (!validRecordsReceived.equals(other.validRecordsReceived))
			return false;
		if (valueOfRecords == null) {
			if (other.valueOfRecords != null)
				return false;
		} else if (!valueOfRecords.equals(other.valueOfRecords))
			return false;
		if (valueOfRecordsCurr == null) {
			if (other.valueOfRecordsCurr != null)
				return false;
		} else if (!valueOfRecordsCurr.equals(other.valueOfRecordsCurr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcOpsTransCtrlMsgEntity [ctrlMsgId=" + ctrlMsgId
				+ ", serviceId=" + serviceId + ", msgType=" + msgType
				+ ", processDate=" + processDate + ", memberIdFm=" + memberIdFm
				+ ", memberIdTo=" + memberIdTo + ", nrOfFiles=" + nrOfFiles
				+ ", nrOfRecords=" + nrOfRecords + ", valueOfRecords="
				+ valueOfRecords + ", systemStatus=" + systemStatus
				+ ", nrOfFilesReceived=" + nrOfFilesReceived
				+ ", nrOfRecordsReceived=" + nrOfRecordsReceived
				+ ", validRecordsReceived=" + validRecordsReceived
				+ ", activeInd=" + activeInd + ", valueOfRecordsCurr="
				+ valueOfRecordsCurr + "]";
	}

    
    
}
