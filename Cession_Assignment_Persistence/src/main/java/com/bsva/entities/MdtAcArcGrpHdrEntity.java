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
@Table(name = "MDT_AC_ARC_GRP_HDR",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findAll", query = "SELECT m FROM MdtAcArcGrpHdrEntity m"),
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findByMsgId", query = "SELECT m FROM MdtAcArcGrpHdrEntity m WHERE m.msgId = :msgId"),
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findByCreateDateTime", query = "SELECT m FROM MdtAcArcGrpHdrEntity m WHERE m.createDateTime = :createDateTime"),
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findByAuthCode", query = "SELECT m FROM MdtAcArcGrpHdrEntity m WHERE m.authCode = :authCode"),
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findByCreatedBy", query = "SELECT m FROM MdtAcArcGrpHdrEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findByArchiveDate", query = "SELECT m FROM MdtAcArcGrpHdrEntity m WHERE m.archiveDate = :archiveDate"),
    @NamedQuery(name = "MdtAcArcGrpHdrEntity.findByArchiveDateCleanUp", query = "SELECT m FROM MdtAcArcGrpHdrEntity m WHERE m.archiveDate <= :archiveDate")})
public class MdtAcArcGrpHdrEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "MSG_ID")
    private String msgId;
    @Column(name = "CREATE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Size(max = 4)
    @Column(name = "AUTH_CODE")
    private String authCode;
    @Size(max = 25)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "ARCHIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    public MdtAcArcGrpHdrEntity() {
    }

    public MdtAcArcGrpHdrEntity(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result
				+ ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
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
		MdtAcArcGrpHdrEntity other = (MdtAcArcGrpHdrEntity) obj;
		if (archiveDate == null) {
			if (other.archiveDate != null)
				return false;
		} else if (!archiveDate.equals(other.archiveDate))
			return false;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcArcGrpHdrEntity [msgId=" + msgId + ", createDateTime="
				+ createDateTime + ", authCode=" + authCode + ", createdBy="
				+ createdBy + ", archiveDate=" + archiveDate + "]";
	}
    
    
 
}
