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
@Table(name = "CAS_CNFG_FREQUENCY_CODES",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findAll", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByFrequencyCode", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.frequencyCode = :frequencyCode"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByFrequencyCodeLIKE", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.frequencyCode LIKE :frequencyCode"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByFrequencyCodeDescription", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.frequencyCodeDescription = :frequencyCodeDescription"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByActiveInd", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByCreatedBy", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByCreatedDate", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByModifiedBy", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasCnfgFrequencyCodesEntity.findByModifiedDate", query = "SELECT m FROM CasCnfgFrequencyCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasCnfgFrequencyCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "FREQUENCY_CODE")
    private String frequencyCode;
    @Size(max = 100)
    @Column(name = "FREQUENCY_CODE_DESCRIPTION")
    private String frequencyCodeDescription;
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

    public CasCnfgFrequencyCodesEntity() {
    }

    public CasCnfgFrequencyCodesEntity(String frequencyCode) {
        this.frequencyCode = frequencyCode;
    }

    public String getFrequencyCode() {
        return frequencyCode;
    }

    public void setFrequencyCode(String frequencyCode) {
        this.frequencyCode = frequencyCode;
    }

    public String getFrequencyCodeDescription() {
        return frequencyCodeDescription;
    }

    public void setFrequencyCodeDescription(String frequencyCodeDescription) {
        this.frequencyCodeDescription = frequencyCodeDescription;
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
				+ ((frequencyCode == null) ? 0 : frequencyCode.hashCode());
		result = prime
				* result
				+ ((frequencyCodeDescription == null) ? 0
						: frequencyCodeDescription.hashCode());
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
		CasCnfgFrequencyCodesEntity other = (CasCnfgFrequencyCodesEntity) obj;
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
		if (frequencyCode == null) {
			if (other.frequencyCode != null)
				return false;
		} else if (!frequencyCode.equals(other.frequencyCode))
			return false;
		if (frequencyCodeDescription == null) {
			if (other.frequencyCodeDescription != null)
				return false;
		} else if (!frequencyCodeDescription
				.equals(other.frequencyCodeDescription))
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
		return "CasCnfgFrequencyCodesEntity [frequencyCode=" + frequencyCode
				+ ", frequencyCodeDescription=" + frequencyCodeDescription
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

    
    
}