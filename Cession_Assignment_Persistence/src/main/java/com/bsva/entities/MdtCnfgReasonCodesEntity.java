

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
@Table(name = "MDT_CNFG_REASON_CODES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findAll", query = "SELECT m FROM MdtCnfgReasonCodesEntity m"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByReasonCode", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.reasonCode = :reasonCode"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByReasonCodeLIKE", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.reasonCode LIKE :reasonCode"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByReasonCodeDescription", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.reasonCodeDescription = :reasonCodeDescription"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtCnfgReasonCodesEntity.findByName", query = "SELECT m FROM MdtCnfgReasonCodesEntity m WHERE m.name = :name")})
public class MdtCnfgReasonCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "REASON_CODE")
    private String reasonCode;
    @Size(max = 100)
    @Column(name = "REASON_CODE_DESCRIPTION")
    private String reasonCodeDescription;
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
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;

    public MdtCnfgReasonCodesEntity() {
    }

    public MdtCnfgReasonCodesEntity(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCodeDescription() {
        return reasonCodeDescription;
    }

    public void setReasonCodeDescription(String reasonCodeDescription) {
        this.reasonCodeDescription = reasonCodeDescription;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((reasonCode == null) ? 0 : reasonCode.hashCode());
		result = prime
				* result
				+ ((reasonCodeDescription == null) ? 0 : reasonCodeDescription
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
		MdtCnfgReasonCodesEntity other = (MdtCnfgReasonCodesEntity) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reasonCode == null) {
			if (other.reasonCode != null)
				return false;
		} else if (!reasonCode.equals(other.reasonCode))
			return false;
		if (reasonCodeDescription == null) {
			if (other.reasonCodeDescription != null)
				return false;
		} else if (!reasonCodeDescription.equals(other.reasonCodeDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtCnfgReasonCodesEntity [reasonCode=" + reasonCode
				+ ", reasonCodeDescription=" + reasonCodeDescription
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", name=" + name + "]";
	}

    
}
