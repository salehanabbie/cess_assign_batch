
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
 * @author SalehaR
 */
@Entity
@Table(name = "CAS_SYSCTRL_SCHEDULER",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findAll", query = "SELECT m FROM CasSysctrlSchedulerEntity m"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findBySchedulerKey", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.schedulerKey = :schedulerKey"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findBySchedulerName", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.schedulerName = :schedulerName"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findByRescheduleTime", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.rescheduleTime = :rescheduleTime"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlSchedulerEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlSchedulerEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasSysctrlSchedulerEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "SCHEDULER_KEY")
    private String schedulerKey;
    @Size(max = 100)
    @Column(name = "SCHEDULER_NAME")
    private String schedulerName;
    @Column(name = "RESCHEDULE_TIME")
    private String rescheduleTime;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
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

    public CasSysctrlSchedulerEntity() {
    }

    public CasSysctrlSchedulerEntity(String schedulerKey) {
        this.schedulerKey = schedulerKey;
    }

    public String getSchedulerKey() {
        return schedulerKey;
    }

    public void setSchedulerKey(String schedulerKey) {
        this.schedulerKey = schedulerKey;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getRescheduleTime() {
        return rescheduleTime;
    }

    public void setRescheduleTime(String rescheduleTime) {
        this.rescheduleTime = rescheduleTime;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
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
				+ ((rescheduleTime == null) ? 0 : rescheduleTime.hashCode());
		result = prime * result
				+ ((schedulerKey == null) ? 0 : schedulerKey.hashCode());
		result = prime * result
				+ ((schedulerName == null) ? 0 : schedulerName.hashCode());
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
		CasSysctrlSchedulerEntity other = (CasSysctrlSchedulerEntity) obj;
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
		if (rescheduleTime == null) {
			if (other.rescheduleTime != null)
				return false;
		} else if (!rescheduleTime.equals(other.rescheduleTime))
			return false;
		if (schedulerKey == null) {
			if (other.schedulerKey != null)
				return false;
		} else if (!schedulerKey.equals(other.schedulerKey))
			return false;
		if (schedulerName == null) {
			if (other.schedulerName != null)
				return false;
		} else if (!schedulerName.equals(other.schedulerName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasSysctrlSchedulerEntity [schedulerKey=" + schedulerKey
				+ ", schedulerName=" + schedulerName + ", rescheduleTime="
				+ rescheduleTime + ", activeInd=" + activeInd + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}

    
    
}
