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
 * @author ElelwaniR
 */
@Entity
@Table(name = "MDT_OPS_SERVICES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtOpsServicesEntity.findAll", query = "SELECT m FROM MdtOpsServicesEntity m"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByRecordId", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.recordId = :recordId"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdIn", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdIn = :serviceIdIn"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdInNotNull", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdIn IS NOT NULL"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdInDesc", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdInDesc = :serviceIdInDesc"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdOut", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdOut = :serviceIdOut"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdOutAscend", query = "SELECT m FROM MdtOpsServicesEntity m ORDER BY m.serviceIdOut ASC "),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdOutNotNull", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdOut IS NOT NULL"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdOutDesc", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdOutDesc = :serviceIdOutDesc"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByProcessDate", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByActiveInd", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByProcessStatus", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByCreatedBy", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByCreatedDate", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByModifiedBy", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.modifiedBy = :modifiedBy"), 
    @NamedQuery(name = "MdtOpsServicesEntity.findByServiceIdOutSlaTime", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.serviceIdOutSlaTime = :serviceIdOutSlaTime"),
    @NamedQuery(name = "MdtOpsServicesEntity.findByMsgTypeId", query = "SELECT m FROM MdtOpsServicesEntity m WHERE m.msgTypeId = :msgTypeId")})
public class MdtOpsServicesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_ID")
    private BigDecimal recordId;
    @Size(max = 5)
    @Column(name = "SERVICE_ID_IN")
    private String serviceIdIn;
    @Size(max = 75)
    @Column(name = "SERVICE_ID_IN_DESC")
    private String serviceIdInDesc;
    @Size(max = 5)
    @Column(name = "SERVICE_ID_OUT")
    private String serviceIdOut;
    @Size(max = 75)
    @Column(name = "SERVICE_ID_OUT_DESC")
    private String serviceIdOutDesc;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 1)
    @Column(name = "PROCESS_STATUS")
    private String processStatus;
    @Size(max = 25)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 25)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Size(max = 20)
    @Column(name = "SERVICE_ID_OUT_SLA_TIME")
    private String serviceIdOutSlaTime;
    @Size(max = 1)
    @Column(name = "MSG_TYPE_ID")
    private String msgTypeId;

    public MdtOpsServicesEntity() {
    }

    public MdtOpsServicesEntity(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public BigDecimal getRecordId() {
        return recordId;
    }

    public void setRecordId(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public String getServiceIdIn() {
        return serviceIdIn;
    }

    public void setServiceIdIn(String serviceIdIn) {
        this.serviceIdIn = serviceIdIn;
    }

    public String getServiceIdInDesc() {
        return serviceIdInDesc;
    }

    public void setServiceIdInDesc(String serviceIdInDesc) {
        this.serviceIdInDesc = serviceIdInDesc;
    }

    public String getServiceIdOut() {
        return serviceIdOut;
    }

    public void setServiceIdOut(String serviceIdOut) {
        this.serviceIdOut = serviceIdOut;
    }

    public String getServiceIdOutDesc() {
        return serviceIdOutDesc;
    }

    public void setServiceIdOutDesc(String serviceIdOutDesc) {
        this.serviceIdOutDesc = serviceIdOutDesc;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public String getServiceIdOutSlaTime() {
        return serviceIdOutSlaTime;
    }

    public void setServiceIdOutSlaTime(String serviceIdOutSlaTime) {
        this.serviceIdOutSlaTime = serviceIdOutSlaTime;
    }
    
    public String getMsgTypeId() {
        return msgTypeId;
    }

    public void setMsgTypeId(String msgTypeId) {
        this.msgTypeId = msgTypeId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((msgTypeId == null) ? 0 : msgTypeId.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result
				+ ((serviceIdIn == null) ? 0 : serviceIdIn.hashCode());
		result = prime * result
				+ ((serviceIdInDesc == null) ? 0 : serviceIdInDesc.hashCode());
		result = prime * result
				+ ((serviceIdOut == null) ? 0 : serviceIdOut.hashCode());
		result = prime
				* result
				+ ((serviceIdOutDesc == null) ? 0 : serviceIdOutDesc.hashCode());
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
		MdtOpsServicesEntity other = (MdtOpsServicesEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (msgTypeId == null) {
			if (other.msgTypeId != null)
				return false;
		} else if (!msgTypeId.equals(other.msgTypeId))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
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
		if (serviceIdIn == null) {
			if (other.serviceIdIn != null)
				return false;
		} else if (!serviceIdIn.equals(other.serviceIdIn))
			return false;
		if (serviceIdInDesc == null) {
			if (other.serviceIdInDesc != null)
				return false;
		} else if (!serviceIdInDesc.equals(other.serviceIdInDesc))
			return false;
		if (serviceIdOut == null) {
			if (other.serviceIdOut != null)
				return false;
		} else if (!serviceIdOut.equals(other.serviceIdOut))
			return false;
		if (serviceIdOutDesc == null) {
			if (other.serviceIdOutDesc != null)
				return false;
		} else if (!serviceIdOutDesc.equals(other.serviceIdOutDesc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtOpsServicesEntity [recordId=" + recordId + ", serviceIdIn="
				+ serviceIdIn + ", serviceIdInDesc=" + serviceIdInDesc
				+ ", serviceIdOut=" + serviceIdOut + ", serviceIdOutDesc="
				+ serviceIdOutDesc + ", processDate=" + processDate
				+ ", activeInd=" + activeInd + ", processStatus="
				+ processStatus + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", msgTypeId=" + msgTypeId
				+ "]";
	}

   

 
}
