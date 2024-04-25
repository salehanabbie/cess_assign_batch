package com.bsva.entities;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MDT_AC_OPS_TXNS_BILLING",schema ="MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findAll", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findBySystemSeqNo", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.MdtAcOpsTxnsBillingPK.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByOriginator", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.originator = :originator"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByService", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.service = :service"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findBySubService", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.subService = :subService"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByTxnType", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.txnType = :txnType"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByTxnStatus", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByFileName", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.MdtAcOpsTxnsBillingPK.fileName = :fileName"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByStatus", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.status = :status"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByVolume", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.volume = :volume"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByBillExpStatus", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.billExpStatus = :billExpStatus"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findBySystemName", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.systemName = :systemName"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByCreatedBy", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByCreatedDate", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByModifiedBy", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByModifiedDate", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByActionDate", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.MdtAcOpsTxnsBillingPK.actionDate = :actionDate"),
    @NamedQuery(name = "MdtAcOpsTxnsBillingEntity.findByRespDate", query = "SELECT m FROM MdtAcOpsTxnsBillingEntity m WHERE m.respDate = :respDate")})
public class MdtAcOpsTxnsBillingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MdtAcOpsTxnsBillingPK MdtAcOpsTxnsBillingPK;
    @Size(max = 6)
    @Column(name = "ORIGINATOR")
    private String originator;
    @Size(max = 10)
    @Column(name = "SERVICE")
    private String service;
    @Size(max = 5)
    @Column(name = "SUB_SERVICE")
    private String subService;
    @Size(max = 3)
    @Column(name = "TXN_TYPE")
    private String txnType;
    @Size(max = 1)
    @Column(name = "TXN_STATUS")
    private String txnStatus;
    @Size(max = 1)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "VOLUME")
    private Long volume;
    @Size(max = 1)
    @Column(name = "BILL_EXP_STATUS")
    private String billExpStatus;
    @Size(max = 30)
    @Column(name = "SYSTEM_NAME")
    private String systemName;
    @Size(max = 25)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 50)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "RESP_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respDate;


    public MdtAcOpsTxnsBillingEntity() {
    }

    public MdtAcOpsTxnsBillingEntity(MdtAcOpsTxnsBillingPK MdtAcOpsTxnsBillingPK) {
        this.MdtAcOpsTxnsBillingPK = MdtAcOpsTxnsBillingPK;
    }

    public MdtAcOpsTxnsBillingEntity(BigDecimal systemSeqNo, String fileName, Date actionDate) {
        this.MdtAcOpsTxnsBillingPK = new MdtAcOpsTxnsBillingPK(systemSeqNo, fileName, actionDate);
    }

    public MdtAcOpsTxnsBillingPK getMdtAcOpsTxnsBillingPK() {
        return MdtAcOpsTxnsBillingPK;
    }

    public void setMdtAcOpsTxnsBillingPK(MdtAcOpsTxnsBillingPK MdtAcOpsTxnsBillingPK) {
        this.MdtAcOpsTxnsBillingPK = MdtAcOpsTxnsBillingPK;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSubService() {
        return subService;
    }

    public void setSubService(String subService) {
        this.subService = subService;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getBillExpStatus() {
        return billExpStatus;
    }

    public void setBillExpStatus(String billExpStatus) {
        this.billExpStatus = billExpStatus;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
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

    public Date getRespDate() {
        return respDate;
    }

    public void setRespDate(Date respDate) {
        this.respDate = respDate;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MdtAcOpsTxnsBillingPK == null) ? 0 : MdtAcOpsTxnsBillingPK.hashCode());
		result = prime * result + ((billExpStatus == null) ? 0 : billExpStatus.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((originator == null) ? 0 : originator.hashCode());
		result = prime * result + ((respDate == null) ? 0 : respDate.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subService == null) ? 0 : subService.hashCode());
		result = prime * result + ((systemName == null) ? 0 : systemName.hashCode());
		result = prime * result + ((txnStatus == null) ? 0 : txnStatus.hashCode());
		result = prime * result + ((txnType == null) ? 0 : txnType.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
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
		MdtAcOpsTxnsBillingEntity other = (MdtAcOpsTxnsBillingEntity) obj;
		if (MdtAcOpsTxnsBillingPK == null) {
			if (other.MdtAcOpsTxnsBillingPK != null)
				return false;
		} else if (!MdtAcOpsTxnsBillingPK.equals(other.MdtAcOpsTxnsBillingPK))
			return false;
		if (billExpStatus == null) {
			if (other.billExpStatus != null)
				return false;
		} else if (!billExpStatus.equals(other.billExpStatus))
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
		if (originator == null) {
			if (other.originator != null)
				return false;
		} else if (!originator.equals(other.originator))
			return false;
		if (respDate == null) {
			if (other.respDate != null)
				return false;
		} else if (!respDate.equals(other.respDate))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subService == null) {
			if (other.subService != null)
				return false;
		} else if (!subService.equals(other.subService))
			return false;
		if (systemName == null) {
			if (other.systemName != null)
				return false;
		} else if (!systemName.equals(other.systemName))
			return false;
		if (txnStatus == null) {
			if (other.txnStatus != null)
				return false;
		} else if (!txnStatus.equals(other.txnStatus))
			return false;
		if (txnType == null) {
			if (other.txnType != null)
				return false;
		} else if (!txnType.equals(other.txnType))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcOpsTxnsBillingEntity [MdtAcOpsTxnsBillingPK=" + MdtAcOpsTxnsBillingPK + ", originator="
				+ originator + ", service=" + service + ", subService=" + subService + ", txnType=" + txnType
				+ ", txnStatus=" + txnStatus + ", status=" + status + ", volume=" + volume + ", billExpStatus="
				+ billExpStatus + ", systemName=" + systemName + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", respDate="
				+ respDate + "]";
	}



	   
}
