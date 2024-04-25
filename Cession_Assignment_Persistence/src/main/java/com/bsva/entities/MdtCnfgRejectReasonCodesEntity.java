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
@Table(name = "MDT_CNFG_REJECT_REASON_CODES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findAll", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByRejectReasonCode", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.rejectReasonCode = :rejectReasonCode"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByRejectReasonCodeLIKE", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.rejectReasonCode LIKE :rejectReasonCode"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByRejectReasonDesc", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.rejectReasonDesc = :rejectReasonDesc"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgRejectReasonCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgRejectReasonCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtCnfgRejectReasonCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "REJECT_REASON_CODE")
    private String rejectReasonCode;
    @Size(max = 100)
    @Column(name = "REJECT_REASON_DESC")
    private String rejectReasonDesc;
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

    public MdtCnfgRejectReasonCodesEntity() {
    }

    public MdtCnfgRejectReasonCodesEntity(String rejectReasonCode) {
        this.rejectReasonCode = rejectReasonCode;
    }

    public String getRejectReasonCode() {
        return rejectReasonCode;
    }

    public void setRejectReasonCode(String rejectReasonCode) {
        this.rejectReasonCode = rejectReasonCode;
    }

    public String getRejectReasonDesc() {
        return rejectReasonDesc;
    }

    public void setRejectReasonDesc(String rejectReasonDesc) {
        this.rejectReasonDesc = rejectReasonDesc;
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
        int hash = 0;
        hash += (rejectReasonCode != null ? rejectReasonCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdtCnfgRejectReasonCodesEntity)) {
            return false;
        }
        MdtCnfgRejectReasonCodesEntity other = (MdtCnfgRejectReasonCodesEntity) object;
        if ((this.rejectReasonCode == null && other.rejectReasonCode != null) || (this.rejectReasonCode != null && !this.rejectReasonCode.equals(other.rejectReasonCode))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "MdtCnfgRejectReasonCodesEntity [rejectReasonCode="
				+ rejectReasonCode + ", rejectReasonDesc=" + rejectReasonDesc
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

}
