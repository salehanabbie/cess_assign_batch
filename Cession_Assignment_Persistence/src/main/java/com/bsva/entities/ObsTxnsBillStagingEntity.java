package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author DimakatsoN
 */
@Entity
@Table(name = "OBS_TXNS_BILL_STAGING",schema ="BILLOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findAll", query = "SELECT o FROM ObsTxnsBillStagingEntity o"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByRecordId", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.recordId = :recordId"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByActionDate", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.actionDate = :actionDate"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByService", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.service = :service"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findBySubService", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.subService = :subService"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findBySystemName", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.systemName = :systemName"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByOriginator", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.originator = :originator"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByStatus", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.status = :status"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByTrxnStatus", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.trxnStatus = :trxnStatus"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByTrxnType", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.trxnType = :trxnType"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByVolume", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.volume = :volume"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByAmount", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.amount = :amount"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByFilename", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.filename = :filename"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByBillingWindow", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.billingWindow = :billingWindow"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByCreatedBy", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.createdBy = :createdBy"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByCreatedDate", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByModifiedBy", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ObsTxnsBillStagingEntity.findByModifiedDate", query = "SELECT o FROM ObsTxnsBillStagingEntity o WHERE o.modifiedDate = :modifiedDate")})
public class ObsTxnsBillStagingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "BILLOWNER.OBS_TXNS_BILL_STAGING_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_ID")
    private BigDecimal recordId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "SERVICE")
    private String service;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "SUB_SERVICE")
    private String subService;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SYSTEM_NAME")
    private String systemName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORIGINATOR")
    private int originator;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TRXN_STATUS")
    private String trxnStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TRXN_TYPE")
    private String trxnType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOLUME")
    private long volume;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FILENAME")
    private String filename;
    @Size(max = 2)
    @Column(name = "BILLING_WINDOW")
    private String billingWindow;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public ObsTxnsBillStagingEntity() {
    }

    public ObsTxnsBillStagingEntity(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public ObsTxnsBillStagingEntity(BigDecimal recordId, Date actionDate, String service, String subService, String systemName, int originator, String status, String trxnStatus, String trxnType, long volume, String filename, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate) {
        this.recordId = recordId;
        this.actionDate = actionDate;
        this.service = service;
        this.subService = subService;
        this.systemName = systemName;
        this.originator = originator;
        this.status = status;
        this.trxnStatus = trxnStatus;
        this.trxnType = trxnType;
        this.volume = volume;
        this.filename = filename;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public BigDecimal getRecordId() {
        return recordId;
    }

    public void setRecordId(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
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

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public int getOriginator() {
        return originator;
    }

    public void setOriginator(int originator) {
        this.originator = originator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrxnStatus() {
        return trxnStatus;
    }

    public void setTrxnStatus(String trxnStatus) {
        this.trxnStatus = trxnStatus;
    }

    public String getTrxnType() {
        return trxnType;
    }

    public void setTrxnType(String trxnType) {
        this.trxnType = trxnType;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBillingWindow() {
        return billingWindow;
    }

    public void setBillingWindow(String billingWindow) {
        this.billingWindow = billingWindow;
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
		result = prime * result + ((actionDate == null) ? 0 : actionDate.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((billingWindow == null) ? 0 : billingWindow.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + originator;
		result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subService == null) ? 0 : subService.hashCode());
		result = prime * result + ((systemName == null) ? 0 : systemName.hashCode());
		result = prime * result + ((trxnStatus == null) ? 0 : trxnStatus.hashCode());
		result = prime * result + ((trxnType == null) ? 0 : trxnType.hashCode());
		result = prime * result + (int) (volume ^ (volume >>> 32));
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
		ObsTxnsBillStagingEntity other = (ObsTxnsBillStagingEntity) obj;
		if (actionDate == null) {
			if (other.actionDate != null)
				return false;
		} else if (!actionDate.equals(other.actionDate))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (billingWindow == null) {
			if (other.billingWindow != null)
				return false;
		} else if (!billingWindow.equals(other.billingWindow))
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
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
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
		if (originator != other.originator)
			return false;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
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
		if (trxnStatus == null) {
			if (other.trxnStatus != null)
				return false;
		} else if (!trxnStatus.equals(other.trxnStatus))
			return false;
		if (trxnType == null) {
			if (other.trxnType != null)
				return false;
		} else if (!trxnType.equals(other.trxnType))
			return false;
		if (volume != other.volume)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObsTxnsBillStagingEntity [recordId=" + recordId + ", actionDate=" + actionDate + ", service=" + service
				+ ", subService=" + subService + ", systemName=" + systemName + ", originator=" + originator
				+ ", status=" + status + ", trxnStatus=" + trxnStatus + ", trxnType=" + trxnType + ", volume=" + volume
				+ ", amount=" + amount + ", filename=" + filename + ", billingWindow=" + billingWindow + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

  
    

}
