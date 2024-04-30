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
@Table(name = "CAS_SYSCTRL_FILE_STATUS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findAll", query = "SELECT m FROM CasSysctrlFileStatusEntity m"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByStatus", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.status = :status"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByStatusDescription", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.statusDescription = :statusDescription"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlFileStatusEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlFileStatusEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasSysctrlFileStatusEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 50)
    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;
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

    public CasSysctrlFileStatusEntity() {
    }

    public CasSysctrlFileStatusEntity(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
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
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((statusDescription == null) ? 0 : statusDescription
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
		CasSysctrlFileStatusEntity other = (CasSysctrlFileStatusEntity) obj;
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusDescription == null) {
			if (other.statusDescription != null)
				return false;
		} else if (!statusDescription.equals(other.statusDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasSysctrlFileStatusEntity [status=" + status
				+ ", statusDescription=" + statusDescription + ", activeInd="
				+ activeInd + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

    
    
    
}
