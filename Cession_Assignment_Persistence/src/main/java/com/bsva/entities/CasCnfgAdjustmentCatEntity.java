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
@Table(name = "CAS_CNFG_ADJUSTMENT_CAT",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findAll", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByAdjustmentCategory", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.adjustmentCategory = :adjustmentCategory"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByAdjustmentCategoryDesc", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.adjustmentCategoryDesc = :adjustmentCategoryDesc"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByActiveInd", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByCreatedBy", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByCreatedDate", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByModifiedBy", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasCnfgAdjustmentCatEntity.findByModifiedDate", query = "SELECT m FROM CasCnfgAdjustmentCatEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasCnfgAdjustmentCatEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ADJUSTMENT_CATEGORY")
    private String adjustmentCategory;
    @Size(max = 100)
    @Column(name = "ADJUSTMENT_CATEGORY_DESC")
    private String adjustmentCategoryDesc;
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

    public CasCnfgAdjustmentCatEntity() {
    }

    public CasCnfgAdjustmentCatEntity(String adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
    }

    public String getAdjustmentCategory() {
        return adjustmentCategory;
    }

    public void setAdjustmentCategory(String adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
    }

    public String getAdjustmentCategoryDesc() {
        return adjustmentCategoryDesc;
    }

    public void setAdjustmentCategoryDesc(String adjustmentCategoryDesc) {
        this.adjustmentCategoryDesc = adjustmentCategoryDesc;
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
				+ ((adjustmentCategory == null) ? 0 : adjustmentCategory
						.hashCode());
		result = prime
				* result
				+ ((adjustmentCategoryDesc == null) ? 0
						: adjustmentCategoryDesc.hashCode());
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
		CasCnfgAdjustmentCatEntity other = (CasCnfgAdjustmentCatEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (adjustmentCategory == null) {
			if (other.adjustmentCategory != null)
				return false;
		} else if (!adjustmentCategory.equals(other.adjustmentCategory))
			return false;
		if (adjustmentCategoryDesc == null) {
			if (other.adjustmentCategoryDesc != null)
				return false;
		} else if (!adjustmentCategoryDesc.equals(other.adjustmentCategoryDesc))
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
		return "CasCnfgAdjustmentCatEntity [adjustmentCategory=" + adjustmentCategory
				+ ", adjustmentCategoryDesc=" + adjustmentCategoryDesc
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

    
    
}