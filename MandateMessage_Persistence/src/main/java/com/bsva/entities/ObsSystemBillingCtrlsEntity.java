package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SalehaR
 */
@Entity
@Table(name = "OBS_SYSTEM_BILLING_CTRLS", schema="BILLOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findAll", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findByProcessDate", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.obsSystemBillingCtrlsPK.processDate = :processDate"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findBySystemName", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.obsSystemBillingCtrlsPK.systemName = :systemName"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findByProcessStatus", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.processStatus = :processStatus"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findByCreatedBy", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.createdBy = :createdBy"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findByCreatedDate", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findByModifiedBy", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ObsSystemBillingCtrlsEntity.findByModifiedDate", query = "SELECT o FROM ObsSystemBillingCtrlsEntity o WHERE o.modifiedDate = :modifiedDate")})
public class ObsSystemBillingCtrlsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ObsSystemBillingCtrlsPK obsSystemBillingCtrlsPK;
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

    public ObsSystemBillingCtrlsEntity() {
    }

    public ObsSystemBillingCtrlsEntity(ObsSystemBillingCtrlsPK obsSystemBillingCtrlsPK) {
        this.obsSystemBillingCtrlsPK = obsSystemBillingCtrlsPK;
    }

    public ObsSystemBillingCtrlsEntity(Date processDate, String systemName) {
        this.obsSystemBillingCtrlsPK = new ObsSystemBillingCtrlsPK(processDate, systemName);
    }

    public ObsSystemBillingCtrlsPK getObsSystemBillingCtrlsPK() {
        return obsSystemBillingCtrlsPK;
    }

    public void setObsSystemBillingCtrlsPK(ObsSystemBillingCtrlsPK obsSystemBillingCtrlsPK) {
        this.obsSystemBillingCtrlsPK = obsSystemBillingCtrlsPK;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime
				* result
				+ ((obsSystemBillingCtrlsPK == null) ? 0
						: obsSystemBillingCtrlsPK.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
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
		ObsSystemBillingCtrlsEntity other = (ObsSystemBillingCtrlsEntity) obj;
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
		if (obsSystemBillingCtrlsPK == null) {
			if (other.obsSystemBillingCtrlsPK != null)
				return false;
		} else if (!obsSystemBillingCtrlsPK
				.equals(other.obsSystemBillingCtrlsPK))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObsSystemBillingCtrlsEntity [obsSystemBillingCtrlsPK="
				+ obsSystemBillingCtrlsPK + ", processStatus=" + processStatus
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
