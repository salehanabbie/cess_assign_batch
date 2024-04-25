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
@Table(name = "MDT_CNFG_CANCELLATION_CODES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findAll", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByCancellationCode", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.cancellationCode = :cancellationCode"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByCancellationCodeLIKE", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.cancellationCode LIKE :cancellationCode"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByCancellationCodeDescription", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.cancellationCodeDescription = :cancellationCodeDescription"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgCancellationCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgCancellationCodesEntity m WHERE m.modifiedDate = :modifiedDate")})    
public class MdtCnfgCancellationCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CANCELLATION_CODE")
    private String cancellationCode;
    @Size(max = 100)
    @Column(name = "CANCELLATION_CODE_DESCRIPTION")
    private String cancellationCodeDescription;
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
    
    public MdtCnfgCancellationCodesEntity() {
    }

    public MdtCnfgCancellationCodesEntity(String cancellationCode) {
        this.cancellationCode = cancellationCode;
    }

    public String getCancellationCode() {
        return cancellationCode;
    }

    public void setCancellationCode(String cancellationCode) {
        this.cancellationCode = cancellationCode;
    }

    public String getCancellationCodeDescription() {
        return cancellationCodeDescription;
    }

    public void setCancellationCodeDescription(String cancellationCodeDescription) {
        this.cancellationCodeDescription = cancellationCodeDescription;
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
		result = prime
				* result
				+ ((cancellationCode == null) ? 0 : cancellationCode.hashCode());
		result = prime
				* result
				+ ((cancellationCodeDescription == null) ? 0
						: cancellationCodeDescription.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
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
		MdtCnfgCancellationCodesEntity other = (MdtCnfgCancellationCodesEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (cancellationCode == null) {
			if (other.cancellationCode != null)
				return false;
		} else if (!cancellationCode.equals(other.cancellationCode))
			return false;
		if (cancellationCodeDescription == null) {
			if (other.cancellationCodeDescription != null)
				return false;
		} else if (!cancellationCodeDescription
				.equals(other.cancellationCodeDescription))
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
		return true;
	}

	@Override
	public String toString() {
		return "MdtCnfgCancellationCodesEntity [cancellationCode="
				+ cancellationCode + ", cancellationCodeDescription="
				+ cancellationCodeDescription + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

    
    
}

