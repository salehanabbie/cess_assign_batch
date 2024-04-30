package com.bsva.entities;

import java.io.Serializable;
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
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_OPS_CUST_PARAM",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsCustParamEntity.findAll", query = "SELECT m FROM CasOpsCustParamEntity m"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByInstId", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaAmdXsdNs", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaAmdXsdNs = :casaAmdXsdNs"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaAmdLstSeq", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaAmdLstSeq = :casaAmdLstSeq"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaAmdLastFileNr", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaAmdLastFileNr = :casaAmdLastFileNr"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaAccpXsdNs", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaAccpXsdNs = :casaAccpXsdNs"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaAccpLstSeq", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaAccpLstSeq = :casaAccpLstSeq"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaAccpLastFileNr", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaAccpLastFileNr = :casaAccpLastFileNr"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByActiveInd", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCreatedBy", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCreatedDate", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaStatusRepXsdNs", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaStatusRepXsdNs = :casaStatusRepXsdNs"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaStatusRepLstSeq", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaStatusRepLstSeq = :casaStatusRepLstSeq"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaStatusRepLastFileNr", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaStatusRepLastFileNr = :casaStatusRepLastFileNr"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaConfirmXsdNs", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaConfirmXsdNs = :casaConfirmXsdNs"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaConfirmLstSeq", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaConfirmLstSeq = :casaConfirmLstSeq"),
    @NamedQuery(name = "CasOpsCustParamEntity.findByCasaConfirmLstFileNr", query = "SELECT m FROM CasOpsCustParamEntity m WHERE m.casaConfirmLstFileNr = :casaConfirmLstFileNr")})
public class CasOpsCustParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 11)
  @Column(name = "INST_ID")
  private String instId;
  @Size(max = 50)
  @Column(name = "CASA_AMD_XSD_NS")
  private String casaAmdXsdNs;
  @Size(max = 6)
  @Column(name = "CASA_AMD_LST_SEQ")
  private String casaAmdLstSeq;
  @Size(max = 6)
  @Column(name = "CASA_AMD_LAST_FILE_NR")
  private String casaAmdLastFileNr;
  @Size(max = 50)
  @Column(name = "CASA_ACCP_XSD_NS")
  private String casaAccpXsdNs;
  @Size(max = 6)
  @Column(name = "CASA_ACCP_LST_SEQ")
  private String casaAccpLstSeq;
  @Size(max = 6)
  @Column(name = "CASA_ACCP_LAST_FILE_NR")
  private String casaAccpLastFileNr;
  @Size(max = 1)
  @Column(name = "ACTIVE_IND")
  private String activeInd;
  @Size(max = 25)
  @Column(name = "CREATED_BY")
  private String createdBy;
  @Column(name = "CREATED_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Size(max = 50)
  @Column(name = "CASA_STATUS_REP_XSD_NS")
  private String casaStatusRepXsdNs;
  @Size(max = 6)
  @Column(name = "CASA_STATUS_REP_LST_SEQ")
  private String casaStatusRepLstSeq;
  @Size(max = 6)
  @Column(name = "CASA_STATUS_REP_LAST_FILE_NR")
  private String casaStatusRepLastFileNr;
  @Size(max = 50)
  @Column(name = "CASA_CONFIRM_XSD_NS")
  private String casaConfirmXsdNs;
  @Size(max = 6)
  @Column(name = "CASA_CONFIRM_LST_SEQ")
  private String casaConfirmLstSeq;
  @Size(max = 6)
  @Column(name = "CASA_CONFIRM_LST_FILE_NR")
  private String casaConfirmLstFileNr;

  public CasOpsCustParamEntity() {
  }

  public CasOpsCustParamEntity(String instId) {
    this.instId = instId;
  }

  public String getInstId() {
    return instId;
  }

  public void setInstId(String instId) {
    this.instId = instId;
  }

  public String getCasaAmdXsdNs() {
    return casaAmdXsdNs;
  }

  public void setCasaAmdXsdNs(String casaAmdXsdNs) {
    this.casaAmdXsdNs = casaAmdXsdNs;
  }

  public String getCasaAmdLstSeq() {
    return casaAmdLstSeq;
  }

  public void setCasaAmdLstSeq(String casaAmdLstSeq) {
    this.casaAmdLstSeq = casaAmdLstSeq;
  }

  public String getCasaAmdLastFileNr() {
    return casaAmdLastFileNr;
  }

  public void setCasaAmdLastFileNr(String casaAmdLastFileNr) {
    this.casaAmdLastFileNr = casaAmdLastFileNr;
  }

  public String getCasaAccpXsdNs() {
    return casaAccpXsdNs;
  }

  public void setCasaAccpXsdNs(String casaAccpXsdNs) {
    this.casaAccpXsdNs = casaAccpXsdNs;
  }

  public String getCasaAccpLstSeq() {
    return casaAccpLstSeq;
  }

  public void setCasaAccpLstSeq(String casaAccpLstSeq) {
    this.casaAccpLstSeq = casaAccpLstSeq;
  }

  public String getCasaAccpLastFileNr() {
    return casaAccpLastFileNr;
  }

  public void setCasaAccpLastFileNr(String casaAccpLastFileNr) {
    this.casaAccpLastFileNr = casaAccpLastFileNr;
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

  public String getCasaStatusRepXsdNs() {
    return casaStatusRepXsdNs;
  }

  public void setCasaStatusRepXsdNs(String casaStatusRepXsdNs) {
    this.casaStatusRepXsdNs = casaStatusRepXsdNs;
  }

  public String getCasaStatusRepLstSeq() {
    return casaStatusRepLstSeq;
  }

  public void setCasaStatusRepLstSeq(String casaStatusRepLstSeq) {
    this.casaStatusRepLstSeq = casaStatusRepLstSeq;
  }

  public String getCasaStatusRepLastFileNr() {
    return casaStatusRepLastFileNr;
  }

  public void setCasaStatusRepLastFileNr(String casaStatusRepLastFileNr) {
    this.casaStatusRepLastFileNr = casaStatusRepLastFileNr;
  }


  public String getCasaConfirmXsdNs() {
    return casaConfirmXsdNs;
  }

  public void setCasaConfirmXsdNs(String casaConfirmXsdNs) {
    this.casaConfirmXsdNs = casaConfirmXsdNs;
  }

  public String getCasaConfirmLstSeq() {
    return casaConfirmLstSeq;
  }

  public void setCasaConfirmLstSeq(String casaConfirmLstSeq) {
    this.casaConfirmLstSeq = casaConfirmLstSeq;
  }

  public String getCasaConfirmLstFileNr() {
    return casaConfirmLstFileNr;
  }

  public void setCasaConfirmLstFileNr(String casaConfirmLstFileNr) {
    this.casaConfirmLstFileNr = casaConfirmLstFileNr;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CasOpsCustParamEntity that = (CasOpsCustParamEntity) o;
    return Objects.equals(instId, that.instId) &&
        Objects.equals(casaAmdXsdNs, that.casaAmdXsdNs) &&
        Objects.equals(casaAmdLstSeq, that.casaAmdLstSeq) &&
        Objects.equals(casaAmdLastFileNr, that.casaAmdLastFileNr) &&
        Objects.equals(casaAccpXsdNs, that.casaAccpXsdNs) &&
        Objects.equals(casaAccpLstSeq, that.casaAccpLstSeq) &&
        Objects.equals(casaAccpLastFileNr, that.casaAccpLastFileNr) &&
        Objects.equals(activeInd, that.activeInd) &&
        Objects.equals(createdBy, that.createdBy) &&
        Objects.equals(createdDate, that.createdDate) &&
        Objects.equals(casaStatusRepXsdNs, that.casaStatusRepXsdNs) &&
        Objects.equals(casaStatusRepLstSeq, that.casaStatusRepLstSeq) &&
        Objects.equals(casaStatusRepLastFileNr, that.casaStatusRepLastFileNr) &&
        Objects.equals(casaConfirmXsdNs, that.casaConfirmXsdNs) &&
        Objects.equals(casaConfirmLstSeq, that.casaConfirmLstSeq) &&
        Objects.equals(casaConfirmLstFileNr, that.casaConfirmLstFileNr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instId, casaAmdXsdNs, casaAmdLstSeq, casaAmdLastFileNr, casaAccpXsdNs,
        casaAccpLstSeq, casaAccpLastFileNr, activeInd, createdBy, createdDate, casaStatusRepXsdNs,
        casaStatusRepLstSeq, casaStatusRepLastFileNr, casaConfirmXsdNs, casaConfirmLstSeq,
        casaConfirmLstFileNr);
  }

  @Override
  public String toString() {
    return "CasOpsCustParamEntity{" +
        "instId='" + instId + '\'' +
        ", casaAmdXsdNs='" + casaAmdXsdNs + '\'' +
        ", casaAmdLstSeq='" + casaAmdLstSeq + '\'' +
        ", casaAmdLastFileNr='" + casaAmdLastFileNr + '\'' +
        ", casaAccpXsdNs='" + casaAccpXsdNs + '\'' +
        ", casaAccpLstSeq='" + casaAccpLstSeq + '\'' +
        ", casaAccpLastFileNr='" + casaAccpLastFileNr + '\'' +
        ", activeInd='" + activeInd + '\'' +
        ", createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", casaStatusRepXsdNs='" + casaStatusRepXsdNs + '\'' +
        ", casaStatusRepLstSeq='" + casaStatusRepLstSeq + '\'' +
        ", casaStatusRepLastFileNr='" + casaStatusRepLastFileNr + '\'' +
        ", casaConfirmXsdNs='" + casaConfirmXsdNs + '\'' +
        ", casaConfirmLstSeq='" + casaConfirmLstSeq + '\'' +
        ", casaConfirmLstFileNr='" + casaConfirmLstFileNr + '\'' +
        '}';
  }
}
