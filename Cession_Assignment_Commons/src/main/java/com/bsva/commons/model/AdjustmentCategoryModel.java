package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class AdjustmentCategoryModel implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String adjustmentCategory;
    private String adjustmentCategoryDesc;
    private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
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
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
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
		AdjustmentCategoryModel other = (AdjustmentCategoryModel) obj;
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
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AdjustmentCategoryModel [adjustmentCategory="
				+ adjustmentCategory + ", adjustmentCategoryDesc="
				+ adjustmentCategoryDesc + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ "]";
	}
	

}
