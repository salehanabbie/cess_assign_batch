package com.bsva.commons.model;

import java.io.Serializable;

import java.util.Date;


/**
 * @author NosiphoS
 *
 */


public class ReportsNamesModel implements Serializable
{

	private static final long serialVersionUID = 1L;



	 private String reportNr;
	   
    private String reportName;

    private String activeInd;

    private String createdBy;
   
    private Date createdDate;

    private String modifiedBy;
  
    private Date modifiedDate;

    private String internalInd;


	public String getReportNr() {
		return reportNr;
	}

	public void setReportNr(String reportNr) {
		this.reportNr = reportNr;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	
	public String getInternalInd() {
		return internalInd;
	}
	public void setInternalInd(String internalInd) {
		this.internalInd = internalInd;
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
				+ ((internalInd == null) ? 0 : internalInd.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((reportName == null) ? 0 : reportName.hashCode());
		result = prime * result
				+ ((reportNr == null) ? 0 : reportNr.hashCode());
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
		ReportsNamesModel other = (ReportsNamesModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null){
			} else if (!activeInd.equals(other.activeInd))
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
		if (internalInd == null) {
			if (other.internalInd != null)
				return false;
		} else if (!internalInd.equals(other.internalInd))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)

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
			if (internalInd == null) {
				if (other.internalInd != null)
					return false;
			} else if (!internalInd.equals(other.internalInd))
				return false;
			if (modifiedBy == null) {
				if (other.modifiedBy != null)
					return false;
			} else if (!modifiedBy.equals(other.modifiedBy))
				return false;
		} else if (!reportNr.equals(other.reportNr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportsNamesModel [reportNr=" + reportNr + ", reportName="
				+ reportName + ", activeInd=" + activeInd + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate
				+ ", internalInd=" + internalInd + "]";
	}

}
