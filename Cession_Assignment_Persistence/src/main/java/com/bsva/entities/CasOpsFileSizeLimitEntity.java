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
 * @author DimakatsoN
 */
@Entity
@Table(name = "CAS_OPS_FILE_SIZE_LIMIT", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findAll", query = "SELECT m FROM CasOpsFileSizeLimitEntity m"),
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findByMemberId", query = "SELECT m FROM CasOpsFileSizeLimitEntity m WHERE m.mdtAcOpsFileSizeLimitPK.memberId = :memberId"),
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findBySubService", query = "SELECT m FROM CasOpsFileSizeLimitEntity m WHERE m.mdtAcOpsFileSizeLimitPK.subService = :subService"),
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findByProcessDate", query = "SELECT m FROM CasOpsFileSizeLimitEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findByLimit", query = "SELECT m FROM CasOpsFileSizeLimitEntity m WHERE m.limit = :limit"),
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findByActiveId", query = "SELECT m FROM CasOpsFileSizeLimitEntity m WHERE m.activeId = :activeId"),
    @NamedQuery(name = "CasOpsFileSizeLimitEntity.findByDirection", query = "SELECT m FROM CasOpsFileSizeLimitEntity m WHERE m.direction = :direction")})
public class CasOpsFileSizeLimitEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasOpsFileSizeLimitPK casOpsFileSizeLimitPK;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Size(max = 5)
    @Column(name = "LIMIT")
    private String limit;
    @Size(max = 1)
    @Column(name = "ACTIVE_ID")
    private String activeId;
    @Size(max = 6)
    @Column(name = "DIRECTION")
    private String direction;

    public CasOpsFileSizeLimitEntity() {
    }

    public CasOpsFileSizeLimitEntity(CasOpsFileSizeLimitPK casOpsFileSizeLimitPK) {
        this.casOpsFileSizeLimitPK = casOpsFileSizeLimitPK;
    }

    public CasOpsFileSizeLimitEntity(String memberId, String subService) {
        this.casOpsFileSizeLimitPK = new CasOpsFileSizeLimitPK(memberId, subService);
    }

    public CasOpsFileSizeLimitPK getCasOpsFileSizeLimitPK() {
        return casOpsFileSizeLimitPK;
    }

    public void setCasOpsFileSizeLimitPK(CasOpsFileSizeLimitPK casOpsFileSizeLimitPK) {
        this.casOpsFileSizeLimitPK = casOpsFileSizeLimitPK;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeId == null) ? 0 : activeId.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((casOpsFileSizeLimitPK == null) ? 0 : casOpsFileSizeLimitPK.hashCode());
		result = prime * result + ((processDate == null) ? 0 : processDate.hashCode());
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
		CasOpsFileSizeLimitEntity other = (CasOpsFileSizeLimitEntity) obj;
		if (activeId == null) {
			if (other.activeId != null)
				return false;
		} else if (!activeId.equals(other.activeId))
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
		if (casOpsFileSizeLimitPK == null) {
			if (other.casOpsFileSizeLimitPK != null)
				return false;
		} else if (!casOpsFileSizeLimitPK.equals(other.casOpsFileSizeLimitPK))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsFileSizeLimitEntity [casAcOpsFileSizeLimitPK=" + casOpsFileSizeLimitPK + ", processDate="
				+ processDate + ", limit=" + limit + ", activeId=" + activeId + ", direction=" + direction + "]";
	}

    
    

}
