package com.bsva.entities;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SalehaR
 */
@Entity
@Table(name = "MDT_OPS_REF_SEQ_NR",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findAll", query = "SELECT m FROM MdtOpsRefSeqNrEntity m"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByServiceId", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.mdtOpsRefSeqNrPK.serviceId = :serviceId"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByLastSeqNr", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.lastSeqNr = :lastSeqNr"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByCreatedBy", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByCreatedDate", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByModifiedBy", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByModifiedDate", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByLastFileNr", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.lastFileNr = :lastFileNr"),
    @NamedQuery(name = "MdtOpsRefSeqNrEntity.findByMemberNo", query = "SELECT m FROM MdtOpsRefSeqNrEntity m WHERE m.mdtOpsRefSeqNrPK.memberNo = :memberNo")})
public class MdtOpsRefSeqNrEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MdtOpsRefSeqNrPK mdtOpsRefSeqNrPK;
    @Size(max = 6)
    @Column(name = "LAST_SEQ_NR")
    private String lastSeqNr;
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
    @Size(max = 6)
    @Column(name = "LAST_FILE_NR")
    private String lastFileNr;

    public MdtOpsRefSeqNrEntity() {
    }

    public MdtOpsRefSeqNrEntity(MdtOpsRefSeqNrPK mdtOpsRefSeqNrPK) {
        this.mdtOpsRefSeqNrPK = mdtOpsRefSeqNrPK;
    }

    public MdtOpsRefSeqNrEntity(String serviceId, String memberNo) {
        this.mdtOpsRefSeqNrPK = new MdtOpsRefSeqNrPK(serviceId, memberNo);
    }

    public MdtOpsRefSeqNrPK getMdtOpsRefSeqNrPK() {
        return mdtOpsRefSeqNrPK;
    }

    public void setMdtOpsRefSeqNrPK(MdtOpsRefSeqNrPK mdtOpsRefSeqNrPK) {
        this.mdtOpsRefSeqNrPK = mdtOpsRefSeqNrPK;
    }

    public String getLastSeqNr() {
        return lastSeqNr;
    }

    public void setLastSeqNr(String lastSeqNr) {
        this.lastSeqNr = lastSeqNr;
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

    public String getLastFileNr() {
        return lastFileNr;
    }

    public void setLastFileNr(String lastFileNr) {
        this.lastFileNr = lastFileNr;
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
				+ ((lastFileNr == null) ? 0 : lastFileNr.hashCode());
		result = prime * result
				+ ((lastSeqNr == null) ? 0 : lastSeqNr.hashCode());
		result = prime
				* result
				+ ((mdtOpsRefSeqNrPK == null) ? 0 : mdtOpsRefSeqNrPK.hashCode());
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
		MdtOpsRefSeqNrEntity other = (MdtOpsRefSeqNrEntity) obj;
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
		if (lastFileNr == null) {
			if (other.lastFileNr != null)
				return false;
		} else if (!lastFileNr.equals(other.lastFileNr))
			return false;
		if (lastSeqNr == null) {
			if (other.lastSeqNr != null)
				return false;
		} else if (!lastSeqNr.equals(other.lastSeqNr))
			return false;
		if (mdtOpsRefSeqNrPK == null) {
			if (other.mdtOpsRefSeqNrPK != null)
				return false;
		} else if (!mdtOpsRefSeqNrPK.equals(other.mdtOpsRefSeqNrPK))
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
		return "MdtOpsRefSeqNrEntity [mdtOpsRefSeqNrPK=" + mdtOpsRefSeqNrPK
				+ ", lastSeqNr=" + lastSeqNr + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", lastFileNr="
				+ lastFileNr + "]";
	}

  
}
