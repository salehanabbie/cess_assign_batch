package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;

public class AudReportsModel  implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
    private String reportNr;
    private String reportName;
    private String reportRecord;
    private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy; 
    private Date modifiedDate;
    
	public AudReportsModel() {
		super();
	}

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

	public String getReportRecord() {
		return reportRecord;
	}

	public void setReportRecord(String reportRecord) {
		this.reportRecord = reportRecord;
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
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((reportName == null) ? 0 : reportName.hashCode());
		result = prime * result
				+ ((reportNr == null) ? 0 : reportNr.hashCode());
		result = prime * result
				+ ((reportRecord == null) ? 0 : reportRecord.hashCode());
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
		AudReportsModel other = (AudReportsModel) obj;
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
		if (reportName == null) {
			if (other.reportName != null)
				return false;
		} else if (!reportName.equals(other.reportName))
			return false;
		if (reportNr == null) {
			if (other.reportNr != null)
				return false;
		} else if (!reportNr.equals(other.reportNr))
			return false;
		if (reportRecord == null) {
			if (other.reportRecord != null)
				return false;
		} else if (!reportRecord.equals(other.reportRecord))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AudReportsModel [reportNr=" + reportNr + ", reportName="
				+ reportName + ", reportRecord=" + reportRecord
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
    
}
