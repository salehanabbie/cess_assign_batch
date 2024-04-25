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
@Table(name = "MDT_AC_OPS_FILE_SIZE_LIMIT", schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findAll", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m"),
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findByMemberId", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m WHERE m.mdtAcOpsFileSizeLimitPK.memberId = :memberId"),
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findBySubService", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m WHERE m.mdtAcOpsFileSizeLimitPK.subService = :subService"),
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findByProcessDate", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findByLimit", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m WHERE m.limit = :limit"),
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findByActiveId", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m WHERE m.activeId = :activeId"),
    @NamedQuery(name = "MdtAcOpsFileSizeLimitEntity.findByDirection", query = "SELECT m FROM MdtAcOpsFileSizeLimitEntity m WHERE m.direction = :direction")})
public class MdtAcOpsFileSizeLimitEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MdtAcOpsFileSizeLimitPK mdtAcOpsFileSizeLimitPK;
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

    public MdtAcOpsFileSizeLimitEntity() {
    }

    public MdtAcOpsFileSizeLimitEntity(MdtAcOpsFileSizeLimitPK mdtAcOpsFileSizeLimitPK) {
        this.mdtAcOpsFileSizeLimitPK = mdtAcOpsFileSizeLimitPK;
    }

    public MdtAcOpsFileSizeLimitEntity(String memberId, String subService) {
        this.mdtAcOpsFileSizeLimitPK = new MdtAcOpsFileSizeLimitPK(memberId, subService);
    }

    public MdtAcOpsFileSizeLimitPK getMdtAcOpsFileSizeLimitPK() {
        return mdtAcOpsFileSizeLimitPK;
    }

    public void setMdtAcOpsFileSizeLimitPK(MdtAcOpsFileSizeLimitPK mdtAcOpsFileSizeLimitPK) {
        this.mdtAcOpsFileSizeLimitPK = mdtAcOpsFileSizeLimitPK;
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
		result = prime * result + ((mdtAcOpsFileSizeLimitPK == null) ? 0 : mdtAcOpsFileSizeLimitPK.hashCode());
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
		MdtAcOpsFileSizeLimitEntity other = (MdtAcOpsFileSizeLimitEntity) obj;
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
		if (mdtAcOpsFileSizeLimitPK == null) {
			if (other.mdtAcOpsFileSizeLimitPK != null)
				return false;
		} else if (!mdtAcOpsFileSizeLimitPK.equals(other.mdtAcOpsFileSizeLimitPK))
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
		return "MdtAcOpsFileSizeLimitEntity [mdtAcOpsFileSizeLimitPK=" + mdtAcOpsFileSizeLimitPK + ", processDate="
				+ processDate + ", limit=" + limit + ", activeId=" + activeId + ", direction=" + direction + "]";
	}

    
    

}
