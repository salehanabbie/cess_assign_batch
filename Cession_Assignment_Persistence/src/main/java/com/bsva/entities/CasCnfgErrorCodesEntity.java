package com.bsva.entities;


import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "CAS_CNFG_ERROR_CODES",schema = "CASOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findAll", query = "SELECT m FROM CasCnfgErrorCodesEntity m"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByErrorCode", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByErrorCodeLIKE", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.errorCode LIKE :errorCode"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByErrorCodeDesc", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.errorCodeDesc = :errorCodeDesc"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByActiveInd", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findBySeverity", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.severity = :severity"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByCreatedBy", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByCreatedDate", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByModifiedBy", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasCnfgErrorCodesEntity.findByModifiedDate", query = "SELECT m FROM CasCnfgErrorCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasCnfgErrorCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Size(max = 100)
    @Column(name = "ERROR_CODE_DESC")
    private String errorCodeDesc;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Column(name = "SEVERITY")
    private BigInteger severity;
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

    public CasCnfgErrorCodesEntity() {
    }

    public CasCnfgErrorCodesEntity(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCodeDesc() {
        return errorCodeDesc;
    }

    public void setErrorCodeDesc(String errorCodeDesc) {
        this.errorCodeDesc = errorCodeDesc;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public BigInteger getSeverity() {
        return severity;
    }

    public void setSeverity(BigInteger severity) {
        this.severity = severity;
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
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result
				+ ((errorCodeDesc == null) ? 0 : errorCodeDesc.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((severity == null) ? 0 : severity.hashCode());
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
		CasCnfgErrorCodesEntity other = (CasCnfgErrorCodesEntity) obj;
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
		if (errorCodeDesc == null) {
			if (other.errorCodeDesc != null)
				return false;
		} else if (!errorCodeDesc.equals(other.errorCodeDesc))
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
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasCnfgErrorCodesEntity [errorCode=" + errorCode + ", errorCodeDesc="
				+ errorCodeDesc + ", activeInd=" + activeInd + ", severity="
				+ severity + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

    
    
    
    
}
