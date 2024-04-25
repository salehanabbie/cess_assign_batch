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
@Table(name = "MDT_OPS_REP_SEQ_NR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findAll", query = "SELECT m FROM MdtOpsRepSeqNrEntity m"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByProcessDate", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.mdtOpsRepSeqNrPK.processDate = :processDate"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByCreatedBy", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByCreatedDate", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByModifiedBy", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByModifiedDate", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByMemberNo", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.mdtOpsRepSeqNrPK.memberNo = :memberNo"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByLastSeqNo", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.lastSeqNo = :lastSeqNo"),
    @NamedQuery(name = "MdtOpsRepSeqNrEntity.findByReportNo", query = "SELECT m FROM MdtOpsRepSeqNrEntity m WHERE m.mdtOpsRepSeqNrPK.reportNo = :reportNo")})
public class MdtOpsRepSeqNrEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MdtOpsRepSeqNrPK mdtOpsRepSeqNrPK;
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

    public MdtOpsRepSeqNrEntity() {
    }

    public MdtOpsRepSeqNrEntity(MdtOpsRepSeqNrPK mdtOpsRepSeqNrPK) {
        this.mdtOpsRepSeqNrPK = mdtOpsRepSeqNrPK;
    }

    public MdtOpsRepSeqNrEntity(MdtOpsRepSeqNrPK mdtOpsRepSeqNrPK, String lastSeqNo) {
        this.mdtOpsRepSeqNrPK = mdtOpsRepSeqNrPK;
        this.lastSeqNo = lastSeqNo;
    }

    public MdtOpsRepSeqNrEntity(Date processDate, String memberNo, String reportNo) {
        this.mdtOpsRepSeqNrPK = new MdtOpsRepSeqNrPK(processDate, memberNo, reportNo);
    }

    public MdtOpsRepSeqNrPK getMdtOpsRepSeqNrPK() {
        return mdtOpsRepSeqNrPK;
    }

    public void setMdtOpsRepSeqNrEntityPK(MdtOpsRepSeqNrPK mdtOpsRepSeqNrPK) {
        this.mdtOpsRepSeqNrPK = mdtOpsRepSeqNrPK;
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
				+ ((mdtOpsRepSeqNrPK == null) ? 0 : mdtOpsRepSeqNrPK.hashCode());
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
		MdtOpsRepSeqNrEntity other = (MdtOpsRepSeqNrEntity) obj;
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
		if (mdtOpsRepSeqNrPK == null) {
			if (other.mdtOpsRepSeqNrPK != null)
				return false;
		} else if (!mdtOpsRepSeqNrPK.equals(other.mdtOpsRepSeqNrPK))
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
		return "MdtOpsRepSeqNrEntity [mdtOpsRepSeqNrPK=" + mdtOpsRepSeqNrPK
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", lastSeqNo=" + lastSeqNo + "]";
	}

   
    
}
