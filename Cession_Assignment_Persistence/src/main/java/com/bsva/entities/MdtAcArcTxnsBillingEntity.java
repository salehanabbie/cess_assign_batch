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
@Table(name = "MDT_AC_ARC_TXNS_BILLING", schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findAll", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findBySystemSeqNo", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.MdtAcArcTxnsBillingPK.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByOriginator", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.originator = :originator"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByService", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.service = :service"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findBySubService", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.subService = :subService"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByTxnType", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.txnType = :txnType"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByTxnStatus", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByFileName", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.MdtAcArcTxnsBillingPK.fileName = :fileName"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByStatus", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.status = :status"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByVolume", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.volume = :volume"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByBillExpStatus", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.billExpStatus = :billExpStatus"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findBySystemName", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.systemName = :systemName"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByCreatedBy", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByCreatedDate", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByModifiedBy", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByModifiedDate", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByActionDate", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.MdtAcArcTxnsBillingPK.actionDate = :actionDate"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByRespDate", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.respDate = :respDate"),
    @NamedQuery(name = "MdtAcArcTxnsBillingEntity.findByArchiveDate", query = "SELECT m FROM MdtAcArcTxnsBillingEntity m WHERE m.archiveDate = :archiveDate")})
public class MdtAcArcTxnsBillingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MdtAcArcTxnsBillingPK MdtAcArcTxnsBillingPK;
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

    public MdtAcArcTxnsBillingEntity() {
    }

    public MdtAcArcTxnsBillingEntity(MdtAcArcTxnsBillingPK MdtAcArcTxnsBillingPK) {
        this.MdtAcArcTxnsBillingPK = MdtAcArcTxnsBillingPK;
    }

    public MdtAcArcTxnsBillingEntity(long systemSeqNo, String fileName, Date actionDate) {
        this.MdtAcArcTxnsBillingPK = new MdtAcArcTxnsBillingPK(systemSeqNo, fileName, actionDate);
    }

    public MdtAcArcTxnsBillingPK getMdtAcArcTxnsBillingPK() {
        return MdtAcArcTxnsBillingPK;
    }

    public void setMdtAcArcTxnsBillingPK(MdtAcArcTxnsBillingPK MdtAcArcTxnsBillingPK) {
        this.MdtAcArcTxnsBillingPK = MdtAcArcTxnsBillingPK;
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

 
}
