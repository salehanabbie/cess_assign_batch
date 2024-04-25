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
@Table(name = "MDT_CNFG_AMENDMENT_CODES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findAll", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByAmendmentCodes", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.amendmentCodes = :amendmentCodes"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByAmendmentCodesLIKE", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.amendmentCodes LIKE :amendmentCodes"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByAmendmentCodesDescription", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.amendmentCodesDescription = :amendmentCodesDescription"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgAmendmentCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgAmendmentCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtCnfgAmendmentCodesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "AMENDMENT_CODES")
    private String amendmentCodes;
    @Size(max = 100)
    @Column(name = "AMENDMENT_CODES_DESCRIPTION")
    private String amendmentCodesDescription;
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

    public MdtCnfgAmendmentCodesEntity() {
    }

    public MdtCnfgAmendmentCodesEntity(String amendmentCodes) {
        this.amendmentCodes = amendmentCodes;
    }

    public String getAmendmentCodes() {
        return amendmentCodes;
    }

    public void setAmendmentCodes(String amendmentCodes) {
        this.amendmentCodes = amendmentCodes;
    }

    public String getAmendmentCodesDescription() {
        return amendmentCodesDescription;
    }

    public void setAmendmentCodesDescription(String amendmentCodesDescription) {
        this.amendmentCodesDescription = amendmentCodesDescription;
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
				+ ((amendmentCodes == null) ? 0 : amendmentCodes.hashCode());
		result = prime
				* result
				+ ((amendmentCodesDescription == null) ? 0
						: amendmentCodesDescription.hashCode());
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
		MdtCnfgAmendmentCodesEntity other = (MdtCnfgAmendmentCodesEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (amendmentCodes == null) {
			if (other.amendmentCodes != null)
				return false;
		} else if (!amendmentCodes.equals(other.amendmentCodes))
			return false;
		if (amendmentCodesDescription == null) {
			if (other.amendmentCodesDescription != null)
				return false;
		} else if (!amendmentCodesDescription
				.equals(other.amendmentCodesDescription))
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
		return "MdtCnfgAmendmentCodesEntity [amendmentCodes=" + amendmentCodes
				+ ", amendmentCodesDescription=" + amendmentCodesDescription
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

    
    
    
    
}