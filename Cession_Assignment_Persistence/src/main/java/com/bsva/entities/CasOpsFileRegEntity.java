package com.bsva.entities;

import java.io.Serializable;
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
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_OPS_FILE_REG",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsFileRegEntity.findAll", query = "SELECT m FROM CasOpsFileRegEntity m"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileName", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName = :fileName"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileNameLike1", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName LIKE :fileName"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileNameLike2", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName LIKE :fileName AND m.fileName LIKE :fileName2"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileNameLike3", query = "SELECT m FROM  CasOpsFileRegEntity m WHERE m.fileName LIKE :fileName AND m.inOutInd = :inOutInd"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFilepath", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.filepath = :filepath"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByStatus", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.status = :status"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByReason", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.reason = :reason"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByProcessDate", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByNameSpace", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.nameSpace = :nameSpace"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByGrpHdrMsgId", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.grpHdrMsgId = :grpHdrMsgId"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByOnlineInd", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.onlineInd = :onlineInd"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByInOutInd", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.inOutInd = :inOutInd"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByInOutIndASC", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.inOutInd = :inOutInd ORDER BY m.fileName ASC"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByExtractMsgId", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.extractMsgId = :extractMsgId"),
	@NamedQuery(name = "CasOpsFileRegEntity.findByProcessDateTrunc", query = "SELECT m FROM CasOpsFileRegEntity m WHERE TRUNC(m.processDate) = TO_DATE(:processDate,'YYYY-MM-DD') and m.status IN (:status1, :status2)"),
	@NamedQuery(name = "CasOpsFileRegEntity.findByProcessDateTruncService", query = "SELECT m FROM CasOpsFileRegEntity m WHERE TRUNC(m.processDate) = TO_DATE(:processDate,'YYYY-MM-DD') and (m.status = :status1 OR m.status = :status2 OR m.status = :status3) and (m.fileName LIKE CONCAT('%',:serviceName,'%'))")
    })

public class CasOpsFileRegEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 100)
    @Column(name = "FILEPATH")
    private String filepath;
    @Size(max = 50)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 100)
    @Column(name = "REASON")
    private String reason;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Size(max = 100)
    @Column(name = "NAME_SPACE")
    private String nameSpace;
    @Size(max = 35)
    @Column(name = "GRP_HDR_MSG_ID")
    private String grpHdrMsgId;
    @Size(max = 1)
    @Column(name = "ONLINE_IND")
    private String onlineInd;
    @Size(max = 1)
    @Column(name = "IN_OUT_IND")
    private String inOutInd;
    @Size(max = 35)
    @Column(name = "EXTRACT_MSG_ID")
    private String extractMsgId;

    public CasOpsFileRegEntity() {
    }

    public CasOpsFileRegEntity(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getGrpHdrMsgId() {
        return grpHdrMsgId;
    }

    public void setGrpHdrMsgId(String grpHdrMsgId) {
        this.grpHdrMsgId = grpHdrMsgId;
    }

    public String getOnlineInd() {
        return onlineInd;
    }

    public void setOnlineInd(String onlineInd) {
        this.onlineInd = onlineInd;
    }

    public String getInOutInd() {
        return inOutInd;
    }

    public void setInOutInd(String inOutInd) {
        this.inOutInd = inOutInd;
    }

    public String getExtractMsgId() {
        return extractMsgId;
    }

    public void setExtractMsgId(String extractMsgId) {
        this.extractMsgId = extractMsgId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((extractMsgId == null) ? 0 : extractMsgId.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((filepath == null) ? 0 : filepath.hashCode());
		result = prime * result
				+ ((grpHdrMsgId == null) ? 0 : grpHdrMsgId.hashCode());
		result = prime * result
				+ ((inOutInd == null) ? 0 : inOutInd.hashCode());
		result = prime * result
				+ ((nameSpace == null) ? 0 : nameSpace.hashCode());
		result = prime * result
				+ ((onlineInd == null) ? 0 : onlineInd.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CasOpsFileRegEntity other = (CasOpsFileRegEntity) obj;
		if (extractMsgId == null) {
			if (other.extractMsgId != null)
				return false;
		} else if (!extractMsgId.equals(other.extractMsgId))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (filepath == null) {
			if (other.filepath != null)
				return false;
		} else if (!filepath.equals(other.filepath))
			return false;
		if (grpHdrMsgId == null) {
			if (other.grpHdrMsgId != null)
				return false;
		} else if (!grpHdrMsgId.equals(other.grpHdrMsgId))
			return false;
		if (inOutInd == null) {
			if (other.inOutInd != null)
				return false;
		} else if (!inOutInd.equals(other.inOutInd))
			return false;
		if (nameSpace == null) {
			if (other.nameSpace != null)
				return false;
		} else if (!nameSpace.equals(other.nameSpace))
			return false;
		if (onlineInd == null) {
			if (other.onlineInd != null)
				return false;
		} else if (!onlineInd.equals(other.onlineInd))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsFileRegEntity [fileName=" + fileName + ", filepath="
				+ filepath + ", status=" + status + ", reason=" + reason
				+ ", processDate=" + processDate + ", nameSpace=" + nameSpace
				+ ", grpHdrMsgId=" + grpHdrMsgId + ", onlineInd=" + onlineInd
				+ ", inOutInd=" + inOutInd + ", extractMsgId=" + extractMsgId
				+ "]";
	}

    
}
