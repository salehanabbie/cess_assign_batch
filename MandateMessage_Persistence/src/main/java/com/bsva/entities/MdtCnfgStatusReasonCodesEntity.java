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
@Table(name = "MDT_CNFG_STATUS_REASON_CODES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findAll", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByStatusReasonCode", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.statusReasonCode = :statusReasonCode"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByStatusReasonCodeLIKE", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.statusReasonCode LIKE :statusReasonCode"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByStatusReasonCodeDescription", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.statusReasonCodeDescription = :statusReasonCodeDescription"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgStatusReasonCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgStatusReasonCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtCnfgStatusReasonCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "STATUS_REASON_CODE")
    private String statusReasonCode;
    @Size(max = 100)
    @Column(name = "STATUS_REASON_CODE_DESCRIPTION")
    private String statusReasonCodeDescription;
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

    public MdtCnfgStatusReasonCodesEntity() {
    }

    public MdtCnfgStatusReasonCodesEntity(String statusReasonCode) {
        this.statusReasonCode = statusReasonCode;
    }

    public String getStatusReasonCode() {
        return statusReasonCode;
    }

    public void setStatusReasonCode(String statusReasonCode) {
        this.statusReasonCode = statusReasonCode;
    }

    public String getStatusReasonCodeDescription() {
        return statusReasonCodeDescription;
    }

    public void setStatusReasonCodeDescription(String statusReasonCodeDescription) {
        this.statusReasonCodeDescription = statusReasonCodeDescription;
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
		result = prime
				* result
				+ ((statusReasonCode == null) ? 0 : statusReasonCode.hashCode());
		result = prime
				* result
				+ ((statusReasonCodeDescription == null) ? 0
						: statusReasonCodeDescription.hashCode());
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
		MdtCnfgStatusReasonCodesEntity other = (MdtCnfgStatusReasonCodesEntity) obj;
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
		if (statusReasonCode == null) {
			if (other.statusReasonCode != null)
				return false;
		} else if (!statusReasonCode.equals(other.statusReasonCode))
			return false;
		if (statusReasonCodeDescription == null) {
			if (other.statusReasonCodeDescription != null)
				return false;
		} else if (!statusReasonCodeDescription
				.equals(other.statusReasonCodeDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtCnfgStatusReasonCodesEntity [statusReasonCode=" + statusReasonCode
				+ ", statusReasonCodeDescription="
				+ statusReasonCodeDescription + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

    
    
}
