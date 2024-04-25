package com.bsva.entities;

/**
 * @author SalehaR
 *
 */
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


@Entity
@Table(name = "MDT_CNFG_ACCOUNT_TYPE",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findAll", query = "SELECT m FROM MdtCnfgAccountTypeEntity m"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByAccountTypeCode", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.accountTypeCode = :accountTypeCode"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByAccountTypeDescription", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.accountTypeDescription = :accountTypeDescription"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgAccountTypeEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgAccountTypeEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtCnfgAccountTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(max=1)
    @Column(name = "ACCOUNT_TYPE_CODE")
    private String accountTypeCode;
    @Size(max = 35)
    @Column(name = "ACCOUNT_TYPE_DESCRIPTION")
    private String accountTypeDescription;
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

    public MdtCnfgAccountTypeEntity() {
    }

    public MdtCnfgAccountTypeEntity(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getAccountTypeDescription() {
        return accountTypeDescription;
    }

    public void setAccountTypeDescription(String accountTypeDescription) {
        this.accountTypeDescription = accountTypeDescription;
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
				+ ((accountTypeCode == null) ? 0 : accountTypeCode.hashCode());
		result = prime
				* result
				+ ((accountTypeDescription == null) ? 0
						: accountTypeDescription.hashCode());
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
		MdtCnfgAccountTypeEntity other = (MdtCnfgAccountTypeEntity) obj;
		if (accountTypeCode == null) {
			if (other.accountTypeCode != null)
				return false;
		} else if (!accountTypeCode.equals(other.accountTypeCode))
			return false;
		if (accountTypeDescription == null) {
			if (other.accountTypeDescription != null)
				return false;
		} else if (!accountTypeDescription.equals(other.accountTypeDescription))
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
		return "MdtCnfgAccountTypeEntity [accountTypeCode=" + accountTypeCode
				+ ", accountTypeDescription=" + accountTypeDescription
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

  
    
}

