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
@Table(name = "MDT_CNFG_VAL_RULE",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgValRuleEntity.findAll", query = "SELECT m FROM MdtCnfgValRuleEntity m"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByRuleNr", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.ruleNr = :ruleNr"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByRuleDesc", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.ruleDesc = :ruleDesc"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtCnfgValRuleEntity.findByErrorCode", query = "SELECT m FROM MdtCnfgValRuleEntity m WHERE m.errorCode = :errorCode")})
public class MdtCnfgValRuleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "RULE_NR")
    private String ruleNr;
    @Size(max = 200)
    @Column(name = "RULE_DESC")
    private String ruleDesc;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 10)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 10)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Size(max = 6)
    @Column(name = "ERROR_CODE")
    private String errorCode;

    public MdtCnfgValRuleEntity() {
    }

    public MdtCnfgValRuleEntity(String ruleNr) {
        this.ruleNr = ruleNr;
    }

    public String getRuleNr() {
        return ruleNr;
    }

    public void setRuleNr(String ruleNr) {
        this.ruleNr = ruleNr;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
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
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((ruleDesc == null) ? 0 : ruleDesc.hashCode());
		result = prime * result + ((ruleNr == null) ? 0 : ruleNr.hashCode());
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
		MdtCnfgValRuleEntity other = (MdtCnfgValRuleEntity) obj;
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
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
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
		if (ruleDesc == null) {
			if (other.ruleDesc != null)
				return false;
		} else if (!ruleDesc.equals(other.ruleDesc))
			return false;
		if (ruleNr == null) {
			if (other.ruleNr != null)
				return false;
		} else if (!ruleNr.equals(other.ruleNr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtCnfgValRuleEntity [ruleNr=" + ruleNr + ", ruleDesc=" + ruleDesc
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", errorCode=" + errorCode
				+ "]";
	}

    
    
}

