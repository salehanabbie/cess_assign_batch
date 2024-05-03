package com.bsva.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "CAS_ARC_TXNS_BILLING", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasArcTxnsBillingEntity.findAll", query = "SELECT m FROM CasArcTxnsBillingEntity m"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findBySystemSeqNo", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.casArcTxnsBillingPK.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByOriginator", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.originator = :originator"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByService", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.service = :service"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findBySubService", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.subService = :subService"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByTxnType", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.txnType = :txnType"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByTxnStatus", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByFileName", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.casArcTxnsBillingPK.fileName = :fileName"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByStatus", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.status = :status"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByVolume", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.volume = :volume"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByBillExpStatus", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.billExpStatus = :billExpStatus"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findBySystemName", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.systemName = :systemName"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByCreatedBy", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByCreatedDate", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByModifiedBy", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByModifiedDate", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByActionDate", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.casArcTxnsBillingPK.actionDate = :actionDate"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByRespDate", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.respDate = :respDate"),
    @NamedQuery(name = "CasArcTxnsBillingEntity.findByArchiveDate", query = "SELECT m FROM CasArcTxnsBillingEntity m WHERE m.archiveDate = :archiveDate")})
public class CasArcTxnsBillingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasArcTxnsBillingPK casArcTxnsBillingPK;
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
    @Column(name = "ARCHIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    public CasArcTxnsBillingEntity() {
    }

    public CasArcTxnsBillingEntity(CasArcTxnsBillingPK casArcTxnsBillingPK) {
        this.casArcTxnsBillingPK = casArcTxnsBillingPK;
    }

    public CasArcTxnsBillingEntity(long systemSeqNo, String fileName, Date actionDate) {
        this.casArcTxnsBillingPK = new CasArcTxnsBillingPK(systemSeqNo, fileName, actionDate);
    }

    public CasArcTxnsBillingPK getCasArcTxnsBillingPK() {
        return casArcTxnsBillingPK;
    }

    public void setCasArcTxnsBillingPK(CasArcTxnsBillingPK casArcTxnsBillingPK) {
        this.casArcTxnsBillingPK = casArcTxnsBillingPK;
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

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CasArcTxnsBillingEntity that = (CasArcTxnsBillingEntity) o;
        return Objects.equals(casArcTxnsBillingPK, that.casArcTxnsBillingPK) &&
            Objects.equals(originator, that.originator) && Objects.equals(service, that.service) &&
            Objects.equals(subService, that.subService) && Objects.equals(txnType, that.txnType) &&
            Objects.equals(txnStatus, that.txnStatus) && Objects.equals(status, that.status) &&
            Objects.equals(volume, that.volume) &&
            Objects.equals(billExpStatus, that.billExpStatus) &&
            Objects.equals(systemName, that.systemName) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(respDate, that.respDate) &&
            Objects.equals(archiveDate, that.archiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(casArcTxnsBillingPK, originator, service, subService, txnType,
            txnStatus, status, volume, billExpStatus, systemName, createdBy, createdDate,
            modifiedBy, modifiedDate, respDate, archiveDate);
    }

    @Override
    public String toString() {
        return "CasArcTxnsBillingEntity{" + "casArcTxnsBillingPK=" + casArcTxnsBillingPK +
            ", originator='" + originator + '\'' + ", service='" + service + '\'' +
            ", subService='" + subService + '\'' + ", txnType='" + txnType + '\'' +
            ", txnStatus='" + txnStatus + '\'' + ", status='" + status + '\'' + ", volume=" +
            volume + ", billExpStatus='" + billExpStatus + '\'' + ", systemName='" + systemName +
            '\'' + ", createdBy='" + createdBy + '\'' + ", createdDate=" + createdDate +
            ", modifiedBy='" + modifiedBy + '\'' + ", modifiedDate=" + modifiedDate +
            ", respDate=" + respDate + ", archiveDate=" + archiveDate + '}';
    }
}
