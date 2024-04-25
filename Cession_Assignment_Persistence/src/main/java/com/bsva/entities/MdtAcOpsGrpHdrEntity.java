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
 * @author DimakatsoN
 */
@Entity
@Table(name = "MDT_AC_OPS_GRP_HDR",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsGrpHdrEntity.findAll", query = "SELECT m FROM MdtAcOpsGrpHdrEntity m"),
    @NamedQuery(name = "MdtAcOpsGrpHdrEntity.findByMsgId", query = "SELECT m FROM MdtAcOpsGrpHdrEntity m WHERE m.msgId = :msgId"),
    @NamedQuery(name = "MdtAcOpsGrpHdrEntity.findByCreateDateTime", query = "SELECT m FROM MdtAcOpsGrpHdrEntity m WHERE m.createDateTime = :createDateTime"),
    @NamedQuery(name = "MdtAcOpsGrpHdrEntity.findByAuthCode", query = "SELECT m FROM MdtAcOpsGrpHdrEntity m WHERE m.authCode = :authCode"),
    @NamedQuery(name = "MdtAcOpsGrpHdrEntity.findByCreatedBy", query = "SELECT m FROM MdtAcOpsGrpHdrEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtAcOpsGrpHdrEntity.findByExcessPeriod", query = "SELECT m FROM MdtAcOpsGrpHdrEntity m WHERE m.createDateTime <= :createDateTime")})
public class MdtAcOpsGrpHdrEntity implements Serializable {
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

    public MdtAcOpsGrpHdrEntity() {
    }

    public MdtAcOpsGrpHdrEntity(String msgId) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MdtAcOpsGrpHdrEntity other = (MdtAcOpsGrpHdrEntity) obj;
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
		return "MdtAcOpsGrpHdrEntity [msgId=" + msgId + ", createDateTime="
				+ createDateTime + ", authCode=" + authCode + ", createdBy="
				+ createdBy + "]";
	}
    
    

  
    
}
