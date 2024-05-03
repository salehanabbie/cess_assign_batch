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
@Table(name = "CAS_ARC_STATUS_HDRS", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasArcStatusHdrsEntity.findAll", query = "SELECT m FROM CasArcStatusHdrsEntity m"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findBySystemSeqNo", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByHdrMsgId", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.hdrMsgId = :hdrMsgId"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByCreateDateTime", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.createDateTime = :createDateTime"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByInstgAgt", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.instgAgt = :instgAgt"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByInstdAgt", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.instdAgt = :instdAgt"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByOrgnlMsgId", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.orgnlMsgId = :orgnlMsgId"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByOrgnlMsgName", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.orgnlMsgName = :orgnlMsgName"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByOrgnlCreateDateTime", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.orgnlCreateDateTime = :orgnlCreateDateTime"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByOrgnlNoOfTxns", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.orgnlNoOfTxns = :orgnlNoOfTxns"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByOrgnlCntlSum", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.orgnlCntlSum = :orgnlCntlSum"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByProcessStatus", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByIN", query = "SELECT o FROM CasArcStatusHdrsEntity o WHERE o.processStatus IN (:processStatus1, :processStatus2)"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByServiceIdIN", query = "SELECT o FROM CasArcStatusHdrsEntity o WHERE o.service IN (:service1, :service2)"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByGroupStatus", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.groupStatus = :groupStatus"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByService", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.service = :service"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByVetRunNumber", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.vetRunNumber = :vetRunNumber"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByWorkunitRefNo", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.workunitRefNo = :workunitRefNo"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByProStatusAndService", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.service = :service and " +
        "m.processStatus = :processStatus and TRUNC(m.createDateTime) = TO_DATE(:createDateTime,'YYYY-MM-DD') and m.instgAgt = :memberId"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByOrgnlFileName", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.orgnlFileName = :orgnlFileName"),
    @NamedQuery(name = "CasArcStatusHdrsEntity.findByExtractFileName", query = "SELECT m FROM CasArcStatusHdrsEntity m WHERE m.extractFileName = :extractFileName")})
public class CasArcStatusHdrsEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "SYSTEM_SEQ_NO")
  private BigDecimal systemSeqNo;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 35)
  @Column(name = "HDR_MSG_ID")
  private String hdrMsgId;
  @Column(name = "CREATE_DATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createDateTime;
  @Size(max = 11)
  @Column(name = "INSTG_AGT")
  private String instgAgt;
  @Size(max = 11)
  @Column(name = "INSTD_AGT")
  private String instdAgt;
  @Size(max = 35)
  @Column(name = "ORGNL_MSG_ID")
  private String orgnlMsgId;
  @Size(max = 35)
  @Column(name = "ORGNL_MSG_NAME")
  private String orgnlMsgName;
  @Column(name = "ORGNL_CREATE_DATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date orgnlCreateDateTime;
  @Column(name = "ORGNL_NO_OF_TXNS")
  private Long orgnlNoOfTxns;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these 
  // annotations to enforce field validation
  @Column(name = "ORGNL_CNTL_SUM")
  private BigDecimal orgnlCntlSum;
  @Size(max = 1)
  @Column(name = "PROCESS_STATUS")
  private String processStatus;
  @Size(max = 4)
  @Column(name = "GROUP_STATUS")
  private String groupStatus;
  @Size(max = 5)
  @Column(name = "SERVICE")
  private String service;
  @Column(name = "VET_RUN_NUMBER")
  private Long vetRunNumber;
  @Size(max = 16)
  @Column(name = "WORKUNIT_REF_NO")
  private String workunitRefNo;
  @Size(max = 40)
  @Column(name = "ORGNL_FILE_NAME")
  private String orgnlFileName;
  @Size(max = 40)
  @Column(name = "EXTRACT_FILE_NAME")
  private String extractFileName;
  @Column(name = "ARCHIVE_DATE")
  @Temporal(TemporalType.DATE)
  private Date archiveDate;

  public CasArcStatusHdrsEntity() {
  }

  public CasArcStatusHdrsEntity(BigDecimal systemSeqNo) {
    this.systemSeqNo = systemSeqNo;
  }

  public CasArcStatusHdrsEntity(BigDecimal systemSeqNo, String hdrMsgId) {
    this.systemSeqNo = systemSeqNo;
    this.hdrMsgId = hdrMsgId;
  }

  public BigDecimal getSystemSeqNo() {
    return systemSeqNo;
  }

  public void setSystemSeqNo(BigDecimal systemSeqNo) {
    this.systemSeqNo = systemSeqNo;
  }

  public String getHdrMsgId() {
    return hdrMsgId;
  }

  public void setHdrMsgId(String hdrMsgId) {
    this.hdrMsgId = hdrMsgId;
  }

  public Date getCreateDateTime() {
    return createDateTime;
  }

  public void setCreateDateTime(Date createDateTime) {
    this.createDateTime = createDateTime;
  }

  public String getInstgAgt() {
    return instgAgt;
  }

  public void setInstgAgt(String instgAgt) {
    this.instgAgt = instgAgt;
  }

  public String getInstdAgt() {
    return instdAgt;
  }

  public void setInstdAgt(String instdAgt) {
    this.instdAgt = instdAgt;
  }

  public String getOrgnlMsgId() {
    return orgnlMsgId;
  }

  public void setOrgnlMsgId(String orgnlMsgId) {
    this.orgnlMsgId = orgnlMsgId;
  }

  public String getOrgnlMsgName() {
    return orgnlMsgName;
  }

  public void setOrgnlMsgName(String orgnlMsgName) {
    this.orgnlMsgName = orgnlMsgName;
  }

  public Date getOrgnlCreateDateTime() {
    return orgnlCreateDateTime;
  }

  public void setOrgnlCreateDateTime(Date orgnlCreateDateTime) {
    this.orgnlCreateDateTime = orgnlCreateDateTime;
  }

  public Long getOrgnlNoOfTxns() {
    return orgnlNoOfTxns;
  }

  public void setOrgnlNoOfTxns(Long orgnlNoOfTxns) {
    this.orgnlNoOfTxns = orgnlNoOfTxns;
  }

  public BigDecimal getOrgnlCntlSum() {
    return orgnlCntlSum;
  }

  public void setOrgnlCntlSum(BigDecimal orgnlCntlSum) {
    this.orgnlCntlSum = orgnlCntlSum;
  }

  public String getProcessStatus() {
    return processStatus;
  }

  public void setProcessStatus(String processStatus) {
    this.processStatus = processStatus;
  }

  public String getGroupStatus() {
    return groupStatus;
  }

  public void setGroupStatus(String groupStatus) {
    this.groupStatus = groupStatus;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public Long getVetRunNumber() {
    return vetRunNumber;
  }

  public void setVetRunNumber(Long vetRunNumber) {
    this.vetRunNumber = vetRunNumber;
  }

  public String getWorkunitRefNo() {
    return workunitRefNo;
  }

  public void setWorkunitRefNo(String workunitRefNo) {
    this.workunitRefNo = workunitRefNo;
  }

  public String getOrgnlFileName() {
    return orgnlFileName;
  }

  public void setOrgnlFileName(String orgnlFileName) {
    this.orgnlFileName = orgnlFileName;
  }

  public String getExtractFileName() {
    return extractFileName;
  }

  public void setExtractFileName(String extractFileName) {
    this.extractFileName = extractFileName;
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
    CasArcStatusHdrsEntity that = (CasArcStatusHdrsEntity) o;
    return Objects.equals(systemSeqNo, that.systemSeqNo) &&
        Objects.equals(hdrMsgId, that.hdrMsgId) &&
        Objects.equals(createDateTime, that.createDateTime) &&
        Objects.equals(instgAgt, that.instgAgt) &&
        Objects.equals(instdAgt, that.instdAgt) &&
        Objects.equals(orgnlMsgId, that.orgnlMsgId) &&
        Objects.equals(orgnlMsgName, that.orgnlMsgName) &&
        Objects.equals(orgnlCreateDateTime, that.orgnlCreateDateTime) &&
        Objects.equals(orgnlNoOfTxns, that.orgnlNoOfTxns) &&
        Objects.equals(orgnlCntlSum, that.orgnlCntlSum) &&
        Objects.equals(processStatus, that.processStatus) &&
        Objects.equals(groupStatus, that.groupStatus) &&
        Objects.equals(service, that.service) &&
        Objects.equals(vetRunNumber, that.vetRunNumber) &&
        Objects.equals(workunitRefNo, that.workunitRefNo) &&
        Objects.equals(orgnlFileName, that.orgnlFileName) &&
        Objects.equals(extractFileName, that.extractFileName) &&
        Objects.equals(archiveDate, that.archiveDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(systemSeqNo, hdrMsgId, createDateTime, instgAgt, instdAgt, orgnlMsgId,
        orgnlMsgName, orgnlCreateDateTime, orgnlNoOfTxns, orgnlCntlSum, processStatus, groupStatus,
        service, vetRunNumber, workunitRefNo, orgnlFileName, extractFileName, archiveDate);
  }

  @Override
  public String toString() {
    return "CasArcStatusHdrsEntity{" +
        "systemSeqNo=" + systemSeqNo +
        ", hdrMsgId='" + hdrMsgId + '\'' +
        ", createDateTime=" + createDateTime +
        ", instgAgt='" + instgAgt + '\'' +
        ", instdAgt='" + instdAgt + '\'' +
        ", orgnlMsgId='" + orgnlMsgId + '\'' +
        ", orgnlMsgName='" + orgnlMsgName + '\'' +
        ", orgnlCreateDateTime=" + orgnlCreateDateTime +
        ", orgnlNoOfTxns=" + orgnlNoOfTxns +
        ", orgnlCntlSum=" + orgnlCntlSum +
        ", processStatus='" + processStatus + '\'' +
        ", groupStatus='" + groupStatus + '\'' +
        ", service='" + service + '\'' +
        ", vetRunNumber=" + vetRunNumber +
        ", workunitRefNo='" + workunitRefNo + '\'' +
        ", orgnlFileName='" + orgnlFileName + '\'' +
        ", extractFileName='" + extractFileName + '\'' +
        ", archiveDate=" + archiveDate +
        '}';
  }
}
