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
@Table(name = "CAS_CNFG_DEBIT_VALUE_TYPE",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findAll", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByDebValueTypeCode", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.debValueTypeCode = :debValueTypeCode"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByDebValueTypeCodeLIKE", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.debValueTypeCode like :debValueTypeCode"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByDebValueTypeDescription", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.debValueTypeDescription = :debValueTypeDescription"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByActiveInd", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByAcDebValueTypeDesc", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.acDebValueTypeDesc = :acDebValueTypeDesc"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByCreatedBy", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByCreatedDate", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByModifiedBy", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasCnfgDebitValueTypeEntity.findByModifiedDate", query = "SELECT m FROM CasCnfgDebitValueTypeEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasCnfgDebitValueTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "DEB_VALUE_TYPE_CODE")
    private String debValueTypeCode;
    @Size(max = 100)
    @Column(name = "DEB_VALUE_TYPE_DESCRIPTION")
    private String debValueTypeDescription;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 100)
    @Column(name = "AC_DEB_VALUE_TYPE_DESC")
    private String acDebValueTypeDesc;
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

    public CasCnfgDebitValueTypeEntity() {
    }

    public CasCnfgDebitValueTypeEntity(String debValueTypeCode) {
        this.debValueTypeCode = debValueTypeCode;
    }

    public String getDebValueTypeCode() {
        return debValueTypeCode;
    }

    public void setDebValueTypeCode(String debValueTypeCode) {
        this.debValueTypeCode = debValueTypeCode;
    }

    public String getDebValueTypeDescription() {
        return debValueTypeDescription;
    }

    public void setDebValueTypeDescription(String debValueTypeDescription) {
        this.debValueTypeDescription = debValueTypeDescription;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public String getAcDebValueTypeDesc() {
        return acDebValueTypeDesc;
    }

    public void setAcDebValueTypeDesc(String acDebValueTypeDesc) {
        this.acDebValueTypeDesc = acDebValueTypeDesc;
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
		result = prime
				* result
				+ ((acDebValueTypeDesc == null) ? 0 : acDebValueTypeDesc
						.hashCode());
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime
				* result
				+ ((debValueTypeCode == null) ? 0 : debValueTypeCode.hashCode());
		result = prime
				* result
				+ ((debValueTypeDescription == null) ? 0
						: debValueTypeDescription.hashCode());
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
		CasCnfgDebitValueTypeEntity other = (CasCnfgDebitValueTypeEntity) obj;
		if (acDebValueTypeDesc == null) {
			if (other.acDebValueTypeDesc != null)
				return false;
		} else if (!acDebValueTypeDesc.equals(other.acDebValueTypeDesc))
			return false;
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
		if (debValueTypeCode == null) {
			if (other.debValueTypeCode != null)
				return false;
		} else if (!debValueTypeCode.equals(other.debValueTypeCode))
			return false;
		if (debValueTypeDescription == null) {
			if (other.debValueTypeDescription != null)
				return false;
		} else if (!debValueTypeDescription
				.equals(other.debValueTypeDescription))
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
		return "CasCnfgDebitValueTypeEntity [debValueTypeCode="
				+ debValueTypeCode + ", debValueTypeDescription="
				+ debValueTypeDescription + ", activeInd=" + activeInd
				+ ", acDebValueTypeDesc=" + acDebValueTypeDesc + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}

    
    
}
