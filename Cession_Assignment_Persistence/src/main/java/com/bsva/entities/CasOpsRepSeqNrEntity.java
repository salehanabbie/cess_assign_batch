package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author DimakatsoN
 */
@Entity
@Table(name = "CAS_OPS_REP_SEQ_NR",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findAll", query = "SELECT m FROM CasOpsRepSeqNrEntity m"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByProcessDate", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.casOpsRepSeqNrPK.processDate = :processDate"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByCreatedBy", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByCreatedDate", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByModifiedBy", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByModifiedDate", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByMemberNo", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.casOpsRepSeqNrPK.memberNo = :memberNo"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByLastSeqNo", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.lastSeqNo = :lastSeqNo"),
    @NamedQuery(name = "CasOpsRepSeqNrEntity.findByReportNo", query = "SELECT m FROM CasOpsRepSeqNrEntity m WHERE m.casOpsRepSeqNrPK.reportNo = :reportNo")})
public class CasOpsRepSeqNrEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasOpsRepSeqNrPK casOpsRepSeqNrPK;
    @Size(max = 8)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 8)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "LAST_SEQ_NO")
    private String lastSeqNo;

    public CasOpsRepSeqNrEntity() {
    }

    public CasOpsRepSeqNrEntity(CasOpsRepSeqNrPK casOpsRepSeqNrPK) {
        this.casOpsRepSeqNrPK = casOpsRepSeqNrPK;
    }

    public CasOpsRepSeqNrEntity(CasOpsRepSeqNrPK casOpsRepSeqNrPK, String lastSeqNo) {
        this.casOpsRepSeqNrPK = casOpsRepSeqNrPK;
        this.lastSeqNo = lastSeqNo;
    }

    public CasOpsRepSeqNrEntity(Date processDate, String memberNo, String reportNo) {
        this.casOpsRepSeqNrPK = new CasOpsRepSeqNrPK(processDate, memberNo, reportNo);
    }

    public CasOpsRepSeqNrPK getCasOpsRepSeqNrPK() {
        return casOpsRepSeqNrPK;
    }

    public void setCasOpsRepSeqNrEntityPK(CasOpsRepSeqNrPK casOpsRepSeqNrPK) {
        this.casOpsRepSeqNrPK = casOpsRepSeqNrPK;
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

    public String getLastSeqNo() {
        return lastSeqNo;
    }

    public void setLastSeqNo(String lastSeqNo) {
        this.lastSeqNo = lastSeqNo;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((lastSeqNo == null) ? 0 : lastSeqNo.hashCode());
		result = prime
				* result
				+ ((casOpsRepSeqNrPK == null) ? 0 : casOpsRepSeqNrPK.hashCode());
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
		CasOpsRepSeqNrEntity other = (CasOpsRepSeqNrEntity) obj;
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
		if (lastSeqNo == null) {
			if (other.lastSeqNo != null)
				return false;
		} else if (!lastSeqNo.equals(other.lastSeqNo))
			return false;
		if (casOpsRepSeqNrPK == null) {
			if (other.casOpsRepSeqNrPK != null)
				return false;
		} else if (!casOpsRepSeqNrPK.equals(other.casOpsRepSeqNrPK))
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
		return "CasOpsRepSeqNrEntity [casOpsRepSeqNrPK=" + casOpsRepSeqNrPK
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", lastSeqNo=" + lastSeqNo + "]";
	}

   
    
}
