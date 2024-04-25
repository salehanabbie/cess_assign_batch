
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
@Table(name = "MDT_CNFG_REPORT_NAMES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findAll", query = "SELECT m FROM MdtCnfgReportNamesEntity m"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByReportNr", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.reportNr = :reportNr"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByReportNrLIKE", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.reportNr LIKE :reportNr"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByReportName", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.reportName = :reportName"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtCnfgReportNamesEntity.findByInternalInd", query = "SELECT m FROM MdtCnfgReportNamesEntity m WHERE m.internalInd = :internalInd")})
public class MdtCnfgReportNamesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "REPORT_NR")
    private String reportNr;
    @Size(max = 80)
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
    @Size(max = 1)
    @Column(name = "INTERNAL_IND")
    private String internalInd;

    public MdtCnfgReportNamesEntity() {
    }

    public MdtCnfgReportNamesEntity(String reportNr) {
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

    public String getInternalInd() {
        return internalInd;
    }

    public void setInternalInd(String internalInd) {
        this.internalInd = internalInd;
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
        if (!(object instanceof MdtCnfgReportNamesEntity)) {
            return false;
        }
        MdtCnfgReportNamesEntity other = (MdtCnfgReportNamesEntity) object;
        if ((this.reportNr == null && other.reportNr != null) || (this.reportNr != null && !this.reportNr.equals(other.reportNr))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "MdtCnfgReportNamesEntity [reportNr=" + reportNr
				+ ", reportName=" + reportName + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", internalInd=" + internalInd + "]";
	}


}
