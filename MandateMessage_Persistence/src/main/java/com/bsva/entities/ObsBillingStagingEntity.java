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
 * @author SalehaR
 */
@Entity
@Table(name = "OBS_BILLING_STAGING", schema="BILLOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObsBillingStagingEntity.findAll", query = "SELECT o FROM ObsBillingStagingEntity o"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByRecordId", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.recordId = :recordId"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByRespBankBranch", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.respBankBranch = :respBankBranch"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByRespBankAccNo", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.respBankAccNo = :respBankAccNo"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByRespBankAccType", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.respBankAccType = :respBankAccType"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByActionDate", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.actionDate = :actionDate"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByService", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.service = :service"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByRespBankAccName", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.respBankAccName = :respBankAccName"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByRespBankInstitution", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.respBankInstitution = :respBankInstitution"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByCreatedBy", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.createdBy = :createdBy"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByCreatedDate", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByModifiedBy", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByModifiedDate", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "ObsBillingStagingEntity.findBySubService", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.subService = :subService"),
    @NamedQuery(name = "ObsBillingStagingEntity.findBySystemName", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.systemName = :systemName"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByOriginator", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.originator = :originator"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByDestination", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.destination = :destination"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByStatus", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.status = :status"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByTrxnStatus", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.trxnStatus = :trxnStatus"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByTrxnType", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.trxnType = :trxnType"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByAuthInd", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.authInd = :authInd"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByTrackingCode", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.trackingCode = :trackingCode"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByVolume", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.volume = :volume"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByBillingWindow", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.billingWindow = :billingWindow"),
    @NamedQuery(name = "ObsBillingStagingEntity.findByAuthCode", query = "SELECT o FROM ObsBillingStagingEntity o WHERE o.authCode = :authCode")})
public class ObsBillingStagingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "BILLOWNER.OBS_BILLING_STAGING_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_ID")
    private BigDecimal recordId;
    @Column(name = "RESP_BANK_BRANCH")
    private BigDecimal respBankBranch;
    @Size(max = 20)
    @Column(name = "RESP_BANK_ACC_NO")
    private String respBankAccNo;
    @Size(max = 20)
    @Column(name = "RESP_BANK_ACC_TYPE")
    private String respBankAccType;
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
    @Size(max = 30)
    @Column(name = "RESP_BANK_ACC_NAME")
    private String respBankAccName;
    @Size(max = 20)
    @Column(name = "RESP_BANK_INSTITUTION")
    private String respBankInstitution;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 20)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
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
    private BigDecimal originator;
    @Column(name = "DESTINATION")
    private BigDecimal destination;
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
    @Size(min = 1, max = 1)
    @Column(name = "AUTH_IND")
    private String authInd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TRACKING_CODE")
    private String trackingCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOLUME")
    private BigDecimal volume;
    @Size(max = 2)
    @Column(name = "BILLING_WINDOW")
    private String billingWindow;
    @Size(max = 4)
    @Column(name = "AUTH_CODE")
    private String authCode;

    public ObsBillingStagingEntity() {
    }

    public ObsBillingStagingEntity(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public ObsBillingStagingEntity(BigDecimal recordId, Date actionDate, String service, String createdBy, Date createdDate, String subService, String systemName, BigDecimal originator, String status, String trxnStatus, String trxnType, String authInd, String trackingCode, BigDecimal volume, String authCode) {
        this.recordId = recordId;
        this.actionDate = actionDate;
        this.service = service;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.subService = subService;
        this.systemName = systemName;
        this.originator = originator;
        this.status = status;
        this.trxnStatus = trxnStatus;
        this.trxnType = trxnType;
        this.authInd = authInd;
        this.trackingCode = trackingCode;
        this.volume = volume;
        this.authCode = authCode;
    }

    public BigDecimal getRecordId() {
        return recordId;
    }

    public void setRecordId(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public BigDecimal getRespBankBranch() {
        return respBankBranch;
    }

    public void setRespBankBranch(BigDecimal respBankBranch) {
        this.respBankBranch = respBankBranch;
    }

    public String getRespBankAccNo() {
        return respBankAccNo;
    }

    public void setRespBankAccNo(String respBankAccNo) {
        this.respBankAccNo = respBankAccNo;
    }

    public String getRespBankAccType() {
        return respBankAccType;
    }

    public void setRespBankAccType(String respBankAccType) {
        this.respBankAccType = respBankAccType;
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

    public String getRespBankAccName() {
        return respBankAccName;
    }

    public void setRespBankAccName(String respBankAccName) {
        this.respBankAccName = respBankAccName;
    }

    public String getRespBankInstitution() {
        return respBankInstitution;
    }

    public void setRespBankInstitution(String respBankInstitution) {
        this.respBankInstitution = respBankInstitution;
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

    public BigDecimal getOriginator() {
        return originator;
    }

    public void setOriginator(BigDecimal originator) {
        this.originator = originator;
    }

    public BigDecimal getDestination() {
        return destination;
    }

    public void setDestination(BigDecimal destination) {
        this.destination = destination;
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

    public String getAuthInd() {
        return authInd;
    }

    public void setAuthInd(String authInd) {
        this.authInd = authInd;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    
    public String getBillingWindow() {
        return billingWindow;
    }

    public void setBillingWindow(String billingWindow) {
        this.billingWindow = billingWindow;
    }
    
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionDate == null) ? 0 : actionDate.hashCode());
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result + ((authInd == null) ? 0 : authInd.hashCode());
		result = prime * result
				+ ((billingWindow == null) ? 0 : billingWindow.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((originator == null) ? 0 : originator.hashCode());
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result
				+ ((respBankAccName == null) ? 0 : respBankAccName.hashCode());
		result = prime * result
				+ ((respBankAccNo == null) ? 0 : respBankAccNo.hashCode());
		result = prime * result
				+ ((respBankAccType == null) ? 0 : respBankAccType.hashCode());
		result = prime * result
				+ ((respBankBranch == null) ? 0 : respBankBranch.hashCode());
		result = prime
				* result
				+ ((respBankInstitution == null) ? 0 : respBankInstitution
						.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((subService == null) ? 0 : subService.hashCode());
		result = prime * result
				+ ((systemName == null) ? 0 : systemName.hashCode());
		result = prime * result
				+ ((trackingCode == null) ? 0 : trackingCode.hashCode());
		result = prime * result
				+ ((trxnStatus == null) ? 0 : trxnStatus.hashCode());
		result = prime * result
				+ ((trxnType == null) ? 0 : trxnType.hashCode());
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
		ObsBillingStagingEntity other = (ObsBillingStagingEntity) obj;
		if (actionDate == null) {
			if (other.actionDate != null)
				return false;
		} else if (!actionDate.equals(other.actionDate))
			return false;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (authInd == null) {
			if (other.authInd != null)
				return false;
		} else if (!authInd.equals(other.authInd))
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
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
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
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		if (respBankAccName == null) {
			if (other.respBankAccName != null)
				return false;
		} else if (!respBankAccName.equals(other.respBankAccName))
			return false;
		if (respBankAccNo == null) {
			if (other.respBankAccNo != null)
				return false;
		} else if (!respBankAccNo.equals(other.respBankAccNo))
			return false;
		if (respBankAccType == null) {
			if (other.respBankAccType != null)
				return false;
		} else if (!respBankAccType.equals(other.respBankAccType))
			return false;
		if (respBankBranch == null) {
			if (other.respBankBranch != null)
				return false;
		} else if (!respBankBranch.equals(other.respBankBranch))
			return false;
		if (respBankInstitution == null) {
			if (other.respBankInstitution != null)
				return false;
		} else if (!respBankInstitution.equals(other.respBankInstitution))
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
		if (trackingCode == null) {
			if (other.trackingCode != null)
				return false;
		} else if (!trackingCode.equals(other.trackingCode))
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
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObsBillingStagingEntity [recordId=" + recordId
				+ ", respBankBranch=" + respBankBranch + ", respBankAccNo="
				+ respBankAccNo + ", respBankAccType=" + respBankAccType
				+ ", actionDate=" + actionDate + ", service=" + service
				+ ", respBankAccName=" + respBankAccName
				+ ", respBankInstitution=" + respBankInstitution
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", subService=" + subService + ", systemName="
				+ systemName + ", originator=" + originator + ", destination="
				+ destination + ", status=" + status + ", trxnStatus="
				+ trxnStatus + ", trxnType=" + trxnType + ", authInd="
				+ authInd + ", trackingCode=" + trackingCode + ", volume="
				+ volume + ", billingWindow=" + billingWindow + ", authCode="
				+ authCode + "]";
	}
}
