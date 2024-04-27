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
@Table(name = "CAS_CNFG_LOCAL_INSTR_CODES",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findAll", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByActiveInd", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByLocalInstrumentCode", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.localInstrumentCode = :localInstrumentCode"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByLocalInstrumentCodeLIKE", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.localInstrumentCode LIKE :localInstrumentCode"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByLocalInstrumentDescription", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.localInstrumentDescription = :localInstrumentDescription"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByCreatedBy", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByCreatedDate", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByModifiedBy", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasCnfgLocalInstrCodesEntity.findByModifiedDate", query = "SELECT m FROM CasCnfgLocalInstrCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasCnfgLocalInstrCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "LOCAL_INSTRUMENT_CODE")
    private String localInstrumentCode;
    @Size(max = 100)
    @Column(name = "LOCAL_INSTRUMENT_DESCRIPTION")
    private String localInstrumentDescription;
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

    public CasCnfgLocalInstrCodesEntity() {
    }

    public CasCnfgLocalInstrCodesEntity(String localInstrumentCode) {
        this.localInstrumentCode = localInstrumentCode;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public String getLocalInstrumentCode() {
        return localInstrumentCode;
    }

    public void setLocalInstrumentCode(String localInstrumentCode) {
        this.localInstrumentCode = localInstrumentCode;
    }

    public String getLocalInstrumentDescription() {
        return localInstrumentDescription;
    }

    public void setLocalInstrumentDescription(String localInstrumentDescription) {
        this.localInstrumentDescription = localInstrumentDescription;
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
		result = prime
				* result
				+ ((localInstrumentCode == null) ? 0 : localInstrumentCode
						.hashCode());
		result = prime
				* result
				+ ((localInstrumentDescription == null) ? 0
						: localInstrumentDescription.hashCode());
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
		CasCnfgLocalInstrCodesEntity other = (CasCnfgLocalInstrCodesEntity) obj;
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
		if (localInstrumentCode == null) {
			if (other.localInstrumentCode != null)
				return false;
		} else if (!localInstrumentCode.equals(other.localInstrumentCode))
			return false;
		if (localInstrumentDescription == null) {
			if (other.localInstrumentDescription != null)
				return false;
		} else if (!localInstrumentDescription
				.equals(other.localInstrumentDescription))
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
		return "CasCnfgLocalInstrCodesEntity [activeInd=" + activeInd
				+ ", localInstrumentCode=" + localInstrumentCode
				+ ", localInstrumentDescription=" + localInstrumentDescription
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

    
    
}
