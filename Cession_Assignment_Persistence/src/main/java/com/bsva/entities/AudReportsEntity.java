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
 * @author ElelwaniR
 */
@Entity
@Table(name = "AUD_REPORTS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudReportsEntity.findAll", query = "SELECT a FROM AudReportsEntity a"),
    @NamedQuery(name = "AudReportsEntity.findByReportNr", query = "SELECT a FROM AudReportsEntity a WHERE a.reportNr = :reportNr"),
    @NamedQuery(name = "AudReportsEntity.findByReportName", query = "SELECT a FROM AudReportsEntity a WHERE a.reportName = :reportName"),
    @NamedQuery(name = "AudReportsEntity.findByActiveInd", query = "SELECT a FROM AudReportsEntity a WHERE a.activeInd = :activeInd"),
    @NamedQuery(name = "AudReportsEntity.findByCreatedBy", query = "SELECT a FROM AudReportsEntity a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "AudReportsEntity.findByCreatedDate", query = "SELECT a FROM AudReportsEntity a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AudReportsEntity.findByModifiedBy", query = "SELECT a FROM AudReportsEntity a WHERE a.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "AudReportsEntity.findByModifiedDate", query = "SELECT a FROM AudReportsEntity a WHERE a.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "AudReportsEntity.findByReportRecord", query = "SELECT a FROM AudReportsEntity a WHERE a.reportRecord = :reportRecord")})
public class AudReportsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "REPORT_NR")
    private String reportNr;
    @Size(max = 60)
    @Column(name = "REPORT_NAME")
    private String reportName;
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
    @Size(max = 20)
    @Column(name = "REPORT_RECORD")
    private String reportRecord;

    public AudReportsEntity() {
    }

    public AudReportsEntity(String reportNr) {
        this.reportNr = reportNr;
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

    public String getReportRecord() {
        return reportRecord;
    }

    public void setReportRecord(String reportRecord) {
        this.reportRecord = reportRecord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportNr != null ? reportNr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudReportsEntity)) {
            return false;
        }
        AudReportsEntity other = (AudReportsEntity) object;
        if ((this.reportNr == null && other.reportNr != null) || (this.reportNr != null && !this.reportNr.equals(other.reportNr))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "AudReportsEntity [reportNr=" + reportNr + ", reportName="
				+ reportName + ", activeInd=" + activeInd + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate
				+ ", reportRecord=" + reportRecord + "]";
	}


}
