package com.bsva.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class WebOpsCustomerParameters implements Serializable {

  /**
   * @author ElelwaniR
   */

  private String instId;
  private String casaAmdXsdNs;
  private String casaAmdLstSeq;
  private String casaAmdLastFileNr;
  private String casaAccpXsdNs;
  private String casaAccpLstSeq;
  private String casaAccpLastFileNr;
  private String activeInd;
  private String createdBy;
  private Date createdDate;
  private String casaStatusRepXsdNs;
  private String casaStatusRepLstSeq;
  private String casaStatusRepLastFileNr;
  private String casaConfirmXsdNs;
  private String casaConfirmLstSeq;
  private String casaConfirmLstFileNr;


  public WebOpsCustomerParameters() {
    super();
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
    WebOpsCustomerParameters that = (WebOpsCustomerParameters) o;
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
        casaAccpLstSeq, casaAccpLastFileNr, activeInd, createdBy, createdDate,
        casaStatusRepXsdNs,
        casaStatusRepLstSeq, casaStatusRepLastFileNr, casaConfirmXsdNs, casaConfirmLstSeq,
        casaConfirmLstFileNr);
  }

  @Override
  public String toString() {
    return "WebOpsCustomerParameters{" +
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
