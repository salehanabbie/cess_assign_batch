
package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
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
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_SYSCTRL_SERVICES", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlServicesEntity.findAll", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByRecordId", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.recordId = :recordId"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByActiveInd", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByCreatedBy", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByCreatedDate", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByModifiedBy", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByModifiedDate", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByServiceIdIn", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.serviceIdIn = :serviceIdIn"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByServiceIdInDesc", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.serviceIdInDesc = :serviceIdInDesc"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByServiceIdOut", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.serviceIdOut = :serviceIdOut"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByServiceIdOutDesc", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.serviceIdOutDesc = :serviceIdOutDesc"),
    @NamedQuery(name = "CasSysctrlServicesEntity.findByMsgTypeId", query = "SELECT m FROM " +
        "CasSysctrlServicesEntity m WHERE m.msgTypeId = :msgTypeId")})
public class CasSysctrlServicesEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these
  // annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "RECORD_ID")
  private BigDecimal recordId;
  @Size(max = 1)
  @Column(name = "ACTIVE_IND")
  private String activeInd;
  @Size(max = 75)
  @Column(name = "CREATED_BY")
  private String createdBy;
  @Column(name = "CREATED_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Size(max = 75)
  @Column(name = "MODIFIED_BY")
  private String modifiedBy;
  @Column(name = "MODIFIED_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDate;
  @Size(max = 5)
  @Column(name = "SERVICE_ID_IN")
  private String serviceIdIn;
  @Size(max = 75)
  @Column(name = "SERVICE_ID_IN_DESC")
  private String serviceIdInDesc;
  @Size(max = 5)
  @Column(name = "SERVICE_ID_OUT")
  private String serviceIdOut;
  @Size(max = 75)
  @Column(name = "SERVICE_ID_OUT_DESC")
  private String serviceIdOutDesc;
  @Size(max = 1)
  @Column(name = "MSG_TYPE_ID")
  private String msgTypeId;

  public CasSysctrlServicesEntity() {
  }

  public CasSysctrlServicesEntity(BigDecimal recordId) {
    this.recordId = recordId;
  }

  public BigDecimal getRecordId() {
    return recordId;
  }

  public void setRecordId(BigDecimal recordId) {
    this.recordId = recordId;
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

  public String getServiceIdIn() {
    return serviceIdIn;
  }

  public void setServiceIdIn(String serviceIdIn) {
    this.serviceIdIn = serviceIdIn;
  }

  public String getServiceIdInDesc() {
    return serviceIdInDesc;
  }

  public void setServiceIdInDesc(String serviceIdInDesc) {
    this.serviceIdInDesc = serviceIdInDesc;
  }

  public String getServiceIdOut() {
    return serviceIdOut;
  }

  public void setServiceIdOut(String serviceIdOut) {
    this.serviceIdOut = serviceIdOut;
  }

  public String getServiceIdOutDesc() {
    return serviceIdOutDesc;
  }

  public void setServiceIdOutDesc(String serviceIdOutDesc) {
    this.serviceIdOutDesc = serviceIdOutDesc;
  }

  public String getMsgTypeId() {
    return msgTypeId;
  }

  public void setMsgTypeId(String msgTypeId) {
    this.msgTypeId = msgTypeId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CasSysctrlServicesEntity that = (CasSysctrlServicesEntity) o;
    return Objects.equals(recordId, that.recordId) &&
        Objects.equals(activeInd, that.activeInd) &&
        Objects.equals(createdBy, that.createdBy) &&
        Objects.equals(createdDate, that.createdDate) &&
        Objects.equals(modifiedBy, that.modifiedBy) &&
        Objects.equals(modifiedDate, that.modifiedDate) &&
        Objects.equals(serviceIdIn, that.serviceIdIn) &&
        Objects.equals(serviceIdInDesc, that.serviceIdInDesc) &&
        Objects.equals(serviceIdOut, that.serviceIdOut) &&
        Objects.equals(serviceIdOutDesc, that.serviceIdOutDesc) &&
        Objects.equals(msgTypeId, that.msgTypeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recordId, activeInd, createdBy, createdDate, modifiedBy, modifiedDate,
        serviceIdIn, serviceIdInDesc, serviceIdOut, serviceIdOutDesc, msgTypeId);
  }

  @Override
  public String toString() {
    return "CasSysctrlServicesEntity{" +
        "recordId=" + recordId +
        ", activeInd='" + activeInd + '\'' +
        ", createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", modifiedBy='" + modifiedBy + '\'' +
        ", modifiedDate=" + modifiedDate +
        ", serviceIdIn='" + serviceIdIn + '\'' +
        ", serviceIdInDesc='" + serviceIdInDesc + '\'' +
        ", serviceIdOut='" + serviceIdOut + '\'' +
        ", serviceIdOutDesc='" + serviceIdOutDesc + '\'' +
        ", msgTypeId='" + msgTypeId + '\'' +
        '}';
  }
}
