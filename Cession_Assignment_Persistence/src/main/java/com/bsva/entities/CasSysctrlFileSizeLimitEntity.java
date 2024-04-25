package com.bsva.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author DimakatsoN
 */
@Entity
@Table(name = "CAS_SYSCTRL_FILE_SIZE_LIMIT", schema = "CASOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findAll", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByMemberId", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.mdtSysctrlFileSizeLimitPK.memberId = :memberId"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findBySubService", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.mdtSysctrlFileSizeLimitPK.subService = :subService"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByLimit", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.limit = :limit"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByActiveId", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.activeId = :activeId"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByDirection", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.direction = :direction"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlFileSizeLimitEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlFileSizeLimitEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasSysctrlFileSizeLimitEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasSysctrlFileSizeLimitPK casSysctrlFileSizeLimitPK;
    @Size(max = 5)
    @Column(name = "LIMIT")
    private String limit;
    @Size(max = 1)
    @Column(name = "ACTIVE_ID")
    private String activeId;
    @Size(max = 6)
    @Column(name = "DIRECTION")
    private String direction;
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

    public CasSysctrlFileSizeLimitEntity() {
    }

    public CasSysctrlFileSizeLimitEntity(CasSysctrlFileSizeLimitPK casSysctrlFileSizeLimitPK) {
        this.casSysctrlFileSizeLimitPK = casSysctrlFileSizeLimitPK;
    }

    public CasSysctrlFileSizeLimitEntity(String memberId, String subService) {
        this.casSysctrlFileSizeLimitPK = new CasSysctrlFileSizeLimitPK(memberId, subService);
    }

    public CasSysctrlFileSizeLimitPK getMdtSysctrlFileSizeLimitPK() {
        return casSysctrlFileSizeLimitPK;
    }

    public void setMdtSysctrlFileSizeLimitPK(CasSysctrlFileSizeLimitPK casSysctrlFileSizeLimitPK) {
        this.casSysctrlFileSizeLimitPK = casSysctrlFileSizeLimitPK;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
		result = prime * result + ((activeId == null) ? 0 : activeId.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((casSysctrlFileSizeLimitPK == null) ? 0 : casSysctrlFileSizeLimitPK.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
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
		CasSysctrlFileSizeLimitEntity other = (CasSysctrlFileSizeLimitEntity) obj;
		if (activeId == null) {
			if (other.activeId != null)
				return false;
		} else if (!activeId.equals(other.activeId))
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
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (limit == null) {
			if (other.limit != null)
				return false;
		} else if (!limit.equals(other.limit))
			return false;
		if (casSysctrlFileSizeLimitPK == null) {
			if (other.casSysctrlFileSizeLimitPK != null)
				return false;
		} else if (!casSysctrlFileSizeLimitPK.equals(other.casSysctrlFileSizeLimitPK))
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
		return "CasSysctrlFileSizeLimitEntity [mdtSysctrlFileSizeLimitPK=" + casSysctrlFileSizeLimitPK + ", limit="
				+ limit + ", activeId=" + activeId + ", direction=" + direction + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate
				+ "]";
	}

  
}
