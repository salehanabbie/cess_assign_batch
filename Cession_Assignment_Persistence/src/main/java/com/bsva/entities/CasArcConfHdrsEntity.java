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
 * @author SalehaR
 */
@Entity
@Table(name = "CAS_ARC_CONF_HDRS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasArcConfHdrsEntity.findAll", query = "SELECT m FROM CasArcConfHdrsEntity m"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findBySystemSeqNo", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByHdrMsgId", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.hdrMsgId = :hdrMsgId"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByCreateDateTime", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.createDateTime = :createDateTime"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByInstgAgt", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.instgAgt = :instgAgt"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByInstdAgt", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.instdAgt = :instdAgt"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByOrgnlMsgId", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.orgnlMsgId = :orgnlMsgId"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByOrgnlMsgName", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.orgnlMsgName = :orgnlMsgName"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByOrgnlCreateDateTime", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.orgnlCreateDateTime = :orgnlCreateDateTime"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByProcessStatus", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByGroupStatus", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.groupStatus = :groupStatus"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByService", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.service = :service"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByGroupError", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.groupError = :groupError"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByArchiveDate", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.archiveDate = :archiveDate"),
    @NamedQuery(name = "CasArcConfHdrsEntity.findByArchiveDateCleanUp", query = "SELECT m FROM CasArcConfHdrsEntity m WHERE m.archiveDate <= :archiveDate")})

public class CasArcConfHdrsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "HDR_MSG_ID")
    private String hdrMsgId;
    @Column(name = "CREATE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Size(max = 11)
    @Column(name = "INSTG_AGT")
    private String instgAgt;
    @Size(max = 11)
    @Column(name = "INSTD_AGT")
    private String instdAgt;
    @Size(max = 35)
    @Column(name = "ORGNL_MSG_ID")
    private String orgnlMsgId;
    @Size(max = 35)
    @Column(name = "ORGNL_MSG_NAME")
    private String orgnlMsgName;
    @Column(name = "ORGNL_CREATE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orgnlCreateDateTime;
    @Size(max = 1)
    @Column(name = "PROCESS_STATUS")
    private String processStatus;
    @Size(max = 4)
    @Column(name = "GROUP_STATUS")
    private String groupStatus;
    @Size(max = 5)
    @Column(name = "SERVICE")
    private String service;
    @Size(max = 6)
    @Column(name = "GROUP_ERROR")
    private String groupError;
    @Column(name = "ARCHIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    public CasArcConfHdrsEntity() {
    }

    public CasArcConfHdrsEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public CasArcConfHdrsEntity(BigDecimal systemSeqNo, String hdrMsgId) {
        this.systemSeqNo = systemSeqNo;
        this.hdrMsgId = hdrMsgId;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public String getHdrMsgId() {
        return hdrMsgId;
    }

    public void setHdrMsgId(String hdrMsgId) {
        this.hdrMsgId = hdrMsgId;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getInstgAgt() {
        return instgAgt;
    }

    public void setInstgAgt(String instgAgt) {
        this.instgAgt = instgAgt;
    }

    public String getInstdAgt() {
        return instdAgt;
    }

    public void setInstdAgt(String instdAgt) {
        this.instdAgt = instdAgt;
    }

    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    public String getOrgnlMsgName() {
        return orgnlMsgName;
    }

    public void setOrgnlMsgName(String orgnlMsgName) {
        this.orgnlMsgName = orgnlMsgName;
    }

    public Date getOrgnlCreateDateTime() {
        return orgnlCreateDateTime;
    }

    public void setOrgnlCreateDateTime(Date orgnlCreateDateTime) {
        this.orgnlCreateDateTime = orgnlCreateDateTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getGroupError() {
        return groupError;
    }

    public void setGroupError(String groupError) {
        this.groupError = groupError;
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
				+ ((archiveDate == null) ? 0 : archiveDate.hashCode());
		result = prime * result
				+ ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result
				+ ((groupError == null) ? 0 : groupError.hashCode());
		result = prime * result
				+ ((groupStatus == null) ? 0 : groupStatus.hashCode());
		result = prime * result
				+ ((hdrMsgId == null) ? 0 : hdrMsgId.hashCode());
		result = prime * result
				+ ((instdAgt == null) ? 0 : instdAgt.hashCode());
		result = prime * result
				+ ((instgAgt == null) ? 0 : instgAgt.hashCode());
		result = prime
				* result
				+ ((orgnlCreateDateTime == null) ? 0 : orgnlCreateDateTime
						.hashCode());
		result = prime * result
				+ ((orgnlMsgId == null) ? 0 : orgnlMsgId.hashCode());
		result = prime * result
				+ ((orgnlMsgName == null) ? 0 : orgnlMsgName.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
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
		CasArcConfHdrsEntity other = (CasArcConfHdrsEntity) obj;
		if (archiveDate == null) {
			if (other.archiveDate != null)
				return false;
		} else if (!archiveDate.equals(other.archiveDate))
			return false;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (groupError == null) {
			if (other.groupError != null)
				return false;
		} else if (!groupError.equals(other.groupError))
			return false;
		if (groupStatus == null) {
			if (other.groupStatus != null)
				return false;
		} else if (!groupStatus.equals(other.groupStatus))
			return false;
		if (hdrMsgId == null) {
			if (other.hdrMsgId != null)
				return false;
		} else if (!hdrMsgId.equals(other.hdrMsgId))
			return false;
		if (instdAgt == null) {
			if (other.instdAgt != null)
				return false;
		} else if (!instdAgt.equals(other.instdAgt))
			return false;
		if (instgAgt == null) {
			if (other.instgAgt != null)
				return false;
		} else if (!instgAgt.equals(other.instgAgt))
			return false;
		if (orgnlCreateDateTime == null) {
			if (other.orgnlCreateDateTime != null)
				return false;
		} else if (!orgnlCreateDateTime.equals(other.orgnlCreateDateTime))
			return false;
		if (orgnlMsgId == null) {
			if (other.orgnlMsgId != null)
				return false;
		} else if (!orgnlMsgId.equals(other.orgnlMsgId))
			return false;
		if (orgnlMsgName == null) {
			if (other.orgnlMsgName != null)
				return false;
		} else if (!orgnlMsgName.equals(other.orgnlMsgName))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasArcConfHdrsEntity [systemSeqNo=" + systemSeqNo
				+ ", hdrMsgId=" + hdrMsgId + ", createDateTime="
				+ createDateTime + ", instgAgt=" + instgAgt + ", instdAgt="
				+ instdAgt + ", orgnlMsgId=" + orgnlMsgId + ", orgnlMsgName="
				+ orgnlMsgName + ", orgnlCreateDateTime=" + orgnlCreateDateTime
				+ ", processStatus=" + processStatus + ", groupStatus="
				+ groupStatus + ", service=" + service + ", groupError="
				+ groupError + ", archiveDate=" + archiveDate + "]";
	}
    
    
    
	
}
