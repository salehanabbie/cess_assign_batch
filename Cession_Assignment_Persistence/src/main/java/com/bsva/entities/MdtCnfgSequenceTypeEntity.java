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
 * @author SalehaR
 */
@Entity
@Table(name = "MDT_CNFG_SEQUENCE_TYPE",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findAll", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findBySeqTypeCode", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.seqTypeCode = :seqTypeCode"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findBySeqTypeCodeLIKE", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.seqTypeCode LIKE :seqTypeCode"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findBySeqTypeDesc", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.seqTypeDesc = :seqTypeDesc"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgSequenceTypeEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgSequenceTypeEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtCnfgSequenceTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "SEQ_TYPE_CODE")
    private String seqTypeCode;
    @Size(max = 45)
    @Column(name = "SEQ_TYPE_DESC")
    private String seqTypeDesc;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 25)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 25)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public MdtCnfgSequenceTypeEntity() {
    }

    public MdtCnfgSequenceTypeEntity(String seqTypeCode) {
        this.seqTypeCode = seqTypeCode;
    }

    public String getSeqTypeCode() {
        return seqTypeCode;
    }

    public void setSeqTypeCode(String seqTypeCode) {
        this.seqTypeCode = seqTypeCode;
    }

    public String getSeqTypeDesc() {
        return seqTypeDesc;
    }

    public void setSeqTypeDesc(String seqTypeDesc) {
        this.seqTypeDesc = seqTypeDesc;
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
				+ ((seqTypeCode == null) ? 0 : seqTypeCode.hashCode());
		result = prime * result
				+ ((seqTypeDesc == null) ? 0 : seqTypeDesc.hashCode());
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
		MdtCnfgSequenceTypeEntity other = (MdtCnfgSequenceTypeEntity) obj;
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
		if (seqTypeCode == null) {
			if (other.seqTypeCode != null)
				return false;
		} else if (!seqTypeCode.equals(other.seqTypeCode))
			return false;
		if (seqTypeDesc == null) {
			if (other.seqTypeDesc != null)
				return false;
		} else if (!seqTypeDesc.equals(other.seqTypeDesc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtCnfgSequenceTypeEntity [seqTypeCode=" + seqTypeCode
				+ ", seqTypeDesc=" + seqTypeDesc + ", activeInd=" + activeInd
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

    
    
    
}
